package com.overstar.search.export.api;

import com.overstar.order.export.domain.OrderBase;
import com.overstar.order.export.domain.OrderStarDetail;

import java.util.List;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/9 15:17
 */
public interface IOrderIndexService {
    boolean indexOrderInfo(OrderBase orderBase, List<OrderStarDetail> details);
}
