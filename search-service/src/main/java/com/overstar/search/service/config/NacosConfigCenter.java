package com.overstar.search.service.config;

import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/11/1 17:53
 */
@Configuration
@Slf4j
@NacosPropertySource(dataId = "service_search", groupId = "BASE", autoRefreshed = true)
public class NacosConfigCenter {

    //监听nacos配置文件的变化
    @NacosConfigListener(dataId = "service_search", groupId = "BASE",timeout = 500)
    public void onChange(String newContent) {
        log.info("config has refresh ,content ={}",newContent);
    }
}
