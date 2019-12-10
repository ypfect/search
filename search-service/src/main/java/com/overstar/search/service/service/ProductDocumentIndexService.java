package com.overstar.search.service.service;

import com.google.common.collect.Lists;
import com.overstar.search.export.constants.EnumErrorCode;
import com.overstar.search.export.constants.EnumIndexType;
import com.overstar.search.export.dto.ProductDocument;
import com.overstar.search.export.exception.IndexException;
import com.overstar.search.service.mapper.ProductDocumentMapper;
import com.overstar.search.service.pop.EsPop;
import com.overstar.search.service.utils.BeanUtils;
import com.overstar.search.service.utils.IndexUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/11 14:20
 */
@Service
@Slf4j
public class ProductDocumentIndexService extends AbstractIndexService {

    @Autowired
    private RestHighLevelClient client;
    @Autowired
    private ProductDocumentMapper mapper;
    @Autowired
    private EsPop esPop;
    @Autowired
    private IndexUtil indexUtil;
    private List<String> oldIndies;
    private String indexNameNow;


    @Override
    String taskType() {
        return EnumIndexType.PRODUCT_DOCUMENT.getDesc();
    }

    @Override
    Object getData() {
        List<ProductDocument> documents = new ArrayList<>();
        int star = 0;
        int size = 500;
        boolean executeFlag = true;
        while (executeFlag) {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("start", star);
            map.put("size", size);
            List<ProductDocument> productDocSource = mapper.getProductDocSource(map);
            star += size;
            if (productDocSource.size() < 1) {
                executeFlag = false;
            }

            if (!CollectionUtils.isEmpty(productDocSource)) {
                documents.addAll(productDocSource);
            }
        }

        return documents;
    }


    @Override
    List<IndexRequest> dataPrepare(Object data) {
        List<ProductDocument> documents = (List<ProductDocument>) data;
        ArrayList<IndexRequest> requests = new ArrayList<>();
        for (ProductDocument document : documents) {
            Map<String, Object> bean2Map = BeanUtils.bean2Map(document);
            IndexRequest request = new IndexRequest();
            request.id(String.valueOf(document.getProductId()));
            if (StringUtils.isEmpty(indexNameNow)){
                throw new IndexException("生成新的索引名称为空....", EnumErrorCode.INDEX_ERROR.getCode());
            }
            request.index(indexNameNow);
            request.source(bean2Map);
            request.type("_doc");
            requests.add(request);
        }
        return requests;
    }

    @Override
    public boolean createIndex() {
        String indexName = generatorIndexName();
        indexNameNow = indexName;
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        // 2、设置索引的settings
        request.settings(esPop.getIndex().getSettings(), XContentType.JSON);
        request.mapping(esPop.getIndex().getMappings(), XContentType.JSON);

        Alias alias = new Alias(esPop.getAliasProperties().getProductAlias());
        request.alias(alias);

        try {
            //别名下的老索引名称
            oldIndies = indexUtil.getIndicesByAlias(esPop.getAliasProperties().getProductAlias());
            log.info("老索引名称={}",oldIndies);
            CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
            boolean acknowledged = createIndexResponse.isAcknowledged();
            if (acknowledged) {
                return true;
            }
        } catch (IOException e) {
            log.error("别名={}，indexName={},创建失败了...", alias.name(), indexName);
            e.printStackTrace();
        }

        return false;
    }


    @Override
    boolean indexing(Object tem) {
        List<IndexRequest> requests = (List<IndexRequest>) tem;
        //bulk插入
        Lists.partition(requests, 1000).forEach(indexRequests -> {
            BulkRequest bulkRequest = new BulkRequest();
            //受限于整个esClient的配置
            bulkRequest.timeout(new TimeValue(5000, TimeUnit.MILLISECONDS));
            indexRequests.forEach(bulkRequest::add);
            //bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
            //异步bulk
            client.bulkAsync(bulkRequest, RequestOptions.DEFAULT, new ActionListener<BulkResponse>() {
                @Override
                public void onResponse(BulkResponse bulkItemResponses) {
                    log.info("bulking 【{}】 success a piece", taskType());
                }

                @Override
                public void onFailure(Exception e) {
                    log.info("bulking 【{}】 Failure a piece", taskType());
                }
            });
        });

        return true;
    }

    @Override
    boolean deleteIndex() {
        if (CollectionUtils.isEmpty(oldIndies)) {
            return true;
        }

        DeleteIndexRequest delIndex = new DeleteIndexRequest();
        delIndex.indices(String.join(",", oldIndies));
        try {
            client.indices().deleteAsync(delIndex, RequestOptions.DEFAULT, new ActionListener<AcknowledgedResponse>() {
                @Override
                public void onResponse(AcknowledgedResponse acknowledgedResponse) {
                    log.info("删除成功！别名=【{}】,删除索引【{}】", "product", oldIndies);
                }

                @Override
                public void onFailure(Exception e) {
                    log.error("删除失败！别名=【{}】,删除索引【{}】", "product", oldIndies);
                }
            });
        } catch (Exception e) {
            log.error("异步删除索引异常！别名=【{}】,删除索引【{}】", "product", oldIndies);
        }

        return true;

    }

    public String generatorIndexName() {
        SimpleDateFormat format = new SimpleDateFormat("[yyyy-MM-dd]-HH-ss-mm");
        String format1 = format.format(new Date());
        return "product_" + format1;
    }

}
