package com.overstar.search.service.config;

import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/11/1 17:53
 */
@Configuration
@Slf4j
@RefreshScope
public class NacosConfigCenter {
    //监听nacos配置文件的变化
    @NacosConfigListener(dataId = "overstar-search.properties", groupId = "BASE",timeout = 500)
    public void onChange(String newContent) {
        log.info("config has refresh ,content ={}",newContent);
    }
}
