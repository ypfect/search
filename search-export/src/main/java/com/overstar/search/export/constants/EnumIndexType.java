package com.overstar.search.export.constants;

import lombok.Getter;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/11 14:58
 */
@Getter
public enum  EnumIndexType {
    PRODUCT_DOCUMENT("产品文档",1001);


    private String desc;
    private int code;

    EnumIndexType(String desc, int code) {
        this.desc = desc;
        this.code = code;
    }

}
