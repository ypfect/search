package com.overstar.search.service.pop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/11 13:58
 */
@ConfigurationProperties(prefix = "over.es")
@Data
public class EsPop {

    /**
     * es
     */
    private IndexProperties index = new IndexProperties();
    private XPackSecurityProperties xPackSecurityProperties = new XPackSecurityProperties();
    private AliasProperties aliasProperties = new AliasProperties();

}
