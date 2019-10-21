package com.overstar.search.export.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/18 10:15
 */
@AllArgsConstructor
@Getter
public enum EnumErrorCode {
    INDEX_ERROR("索引异常", 10001),
    ;
    private String desc;
    private Integer code;
}
