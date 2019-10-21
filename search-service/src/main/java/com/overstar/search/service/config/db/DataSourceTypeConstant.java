package com.overstar.search.service.config.db;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/10 14:59
 */
enum DataSourceTypeConstant {
    MASTER("master"),
    SLAVE("slave")
    ;

    private String desc;

    DataSourceTypeConstant(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
