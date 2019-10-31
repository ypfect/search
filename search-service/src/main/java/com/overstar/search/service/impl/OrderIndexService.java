package com.overstar.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.overstar.order.export.domain.OrderBase;
import com.overstar.order.export.domain.OrderStarDetail;
import com.overstar.order.export.vo.OrderModel;
import com.overstar.search.export.api.IOrderIndexService;
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
@com.alibaba.dubbo.config.annotation.Service
public class OrderIndexService implements IOrderIndexService {


    @Autowired
    private RestHighLevelClient client;

    @Override
    public boolean indexOrderInfo(OrderBase orderBase, List<OrderStarDetail> details) {
        OrderModel model = new OrderModel();
        model.setDetailList(details);
        BeanUtils.copyProperties(orderBase,model);
        String s = JSON.toJSONString(model);
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.source(s, XContentType.JSON);
        indexRequest.index("order") ;
        indexRequest.id(orderBase.getOrderNo());

        try {
            IndexResponse index = client.index(indexRequest, RequestOptions.DEFAULT);
            System.out.println(index.status());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
