package com.overstar.search.service.impl;

import com.alibaba.fastjson.JSON;
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
            document.setItem_id(orderStarDetail.getId());
            String doc = JSON.toJSONString(document);

            IndexRequest indexRequest = new IndexRequest();
            indexRequest.source(doc, XContentType.JSON);
            indexRequest.index("order") ;
            indexRequest.id(String.valueOf(orderBase.getOrderNo()));
            bulkRequest.add(indexRequest);

        });

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
}
