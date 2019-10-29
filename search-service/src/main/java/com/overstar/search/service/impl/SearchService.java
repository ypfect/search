package com.overstar.search.service.impl;

import com.overstar.search.export.api.ISearchAsYouTypeService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/10/29 23:32
 */
@Service
public class SearchService implements ISearchAsYouTypeService {

    @Override
    public List<String> search() {
        return Arrays.asList("闷墩儿","火锅");
    }
}
