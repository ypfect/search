package com.overstar.search.service.config.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/9/10 14:54
 */
@Configuration
@Slf4j
public class MybatisSourceConfig {

    /**
     * 主数据源
     * @return
     */
    @Bean(initMethod = "init", destroyMethod = "close")
    @ConfigurationProperties("spring.datasource.druid.master")
    public DruidDataSource master() {
        log.debug("master druid data-source init...");
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 从数据源
     * @return
     */
    @ConditionalOnProperty(value = "slave.enable",havingValue = "true")
    @Bean(initMethod = "init", destroyMethod = "close")
    @ConfigurationProperties("spring.datasource.druid.slave")
    public DruidDataSource slave() {
        log.debug("slave druid data-source init...");
        return DruidDataSourceBuilder.create().build();
    }


    @Bean
    @Primary
    public DynamicDataSource dataSource(@Qualifier("master") DataSource master, @Qualifier("slave") DataSource slave) {
        Map<String, DataSource> pool = new HashMap<>();
        pool.put(DataSourceTypeConstant.MASTER.getDesc(), master);
        pool.put(DataSourceTypeConstant.SLAVE.getDesc(), slave);
        return new DynamicDataSource(pool, master);
    }
}
