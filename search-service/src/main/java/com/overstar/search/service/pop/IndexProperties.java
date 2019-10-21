package com.overstar.search.service.pop;

import lombok.Data;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/11 14:02
 */
@Data
public class IndexProperties {
    private String indexName;
    private String settings;
    private String mappings;
}
