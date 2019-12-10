package com.overstar.search.export.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/9 16:07
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductDocument {
    private int productId;
    private String productNameZh;
    private String productNameEn;
    private String viewSpotName;
    private String tagName;
}
