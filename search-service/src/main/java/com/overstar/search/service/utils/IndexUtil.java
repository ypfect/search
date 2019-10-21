package com.overstar.search.service.utils;

import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/17 15:30
 */
@Component
public class IndexUtil {
    @Autowired
    private RestHighLevelClient client;

    public List<String> getIndicesByAlias(String alias) throws IOException {
        IndicesClient indices = client.indices();
        GetAliasesResponse alias1 = indices.getAlias(new GetAliasesRequest(alias), RequestOptions.DEFAULT);
        Map<String, Set<AliasMetaData>> aliases1 = alias1.getAliases();
        return new ArrayList<>(aliases1.keySet());
    }

}
