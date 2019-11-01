package com.overstar.search.service.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.overstar.core.vo.Result;
import com.overstar.order.export.api.IOrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/10/29 22:46
 */
@RestController
public class DubboController {

    @Reference
    private IOrderService orderService;

    @RequestMapping("/dubbo")
    public Result test(){
        return orderService.create(null,null,null);
    }
}
