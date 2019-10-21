package com.overstar.search.service.web;

import com.overstar.search.service.service.ProductDocumentIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/12 17:00
 */
@RestController
public class ProductDocumentController {

    @Autowired
    private ProductDocumentIndexService productDocumentIndexService;

    @RequestMapping("index")
    public void test() throws IllegalAccessException {
        productDocumentIndexService.index();
    }
}
