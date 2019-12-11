package com.overstar.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.overstar.order.export.domain.OrderBase;
import com.overstar.order.export.domain.OrderStarDetail;
import com.overstar.order.export.vo.OrderModel;
import com.overstar.search.export.api.IOrderIndexService;
import com.overstar.search.export.dto.OrderTileDocument;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.NotifyOnceListener;
import org.elasticsearch.action.bulk.BulkAction;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * https://blog.csdn.net/u011781521/article/details/77848489  各种聚合操作
 * https://blog.csdn.net/u014646662/article/details/100098851  各种查找嵌套文档操作
 * @Description
 * 订单和订单明细具有强耦合性
 * 考虑nested模型构建
 * 产品跟
 * 也可以采用平铺，但是数据比较大。选择nested
 * @Author stanley.yu
 * @Date 2019/9/19 22:25
 */
@Service
@org.apache.dubbo.config.annotation.Service
@Slf4j
public class OrderIndexService implements IOrderIndexService {


    @Autowired
    private RestHighLevelClient client;

    @Override
    public boolean indexOrderInfo(OrderBase orderBase, List<OrderStarDetail> details) {
        BulkRequest bulkRequest = new BulkRequest();
        details.forEach(orderStarDetail -> {
            OrderTileDocument document = new OrderTileDocument();
            BeanUtils.copyProperties(orderBase,document);
            BeanUtils.copyProperties(orderStarDetail,document);
            document.setItemId(orderStarDetail.getId());
            document.setOrderType((byte)1);
            String doc = JSON.toJSONString(document);
            document.setDocCreateTime(LocalDateTime.now());
            IndexRequest indexRequest = buildIndexRequest(doc, "order", String.valueOf(orderStarDetail.getId()));
            bulkRequest.add(indexRequest);
        });

        /**
         * 主订单 type=0
         */
        OrderTileDocument document = new OrderTileDocument();
        BeanUtils.copyProperties(orderBase,document);
        document.setOrderType((byte)0);
        document.setDocCreateTime(LocalDateTime.now());
        IndexRequest order = buildIndexRequest(JSON.toJSONString(document), "order", String.valueOf(document.getOrderNo()));
        bulkRequest.add(order);

        client.bulkAsync(bulkRequest, RequestOptions.DEFAULT, new NotifyOnceListener<BulkResponse>() {
            @Override
            protected void innerOnResponse(BulkResponse bulkItemResponses) {
                log.info("bulk success ！");
            }

            @Override
            protected void innerOnFailure(Exception e) {
                log.error("bulk failure ！");
            }
        });

        return true;
    }

    private IndexRequest buildIndexRequest(String source,String index,String id){
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.source(source, XContentType.JSON);
        indexRequest.index(index) ;
        //取子订单id作为id
        indexRequest.id(id);
        return indexRequest;
    }
}
