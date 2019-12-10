package com.overstar.search.service.mapper;

import com.overstar.search.export.dto.ProductDocument;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/10 11:04
 */
public interface ProductDocumentMapper extends BaseMapper<ProductDocument> {

    List<ProductDocument> getProductDocSource(Map<String, Integer> queryMap);
}
