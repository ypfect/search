package com.overstar.search.service.config.db;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/1/25 14:27
 */
@Aspect
@Configuration
@Slf4j
public class DBConnectionAop {

    /**
     * 调用查询订单库信息的时候动态切换数据源
     * @param point
     * @return
     */
    @Around("@annotation(com.overstar.search.service.config.db.TargetDataSource) && @annotation(targetDataSource)")
    public Object dynamicChangeDataSource(ProceedingJoinPoint point,TargetDataSource targetDataSource) throws Throwable {
        try {
            String dataSourceName = targetDataSource.name();

            if (StringUtils.isNotBlank(dataSourceName)) {
                log.info(" ~~ datasource changed from [{}] to [{}] ~~","master",dataSourceName);
                DynamicDataSource.setDataSourceKey(dataSourceName);
            }

            return point.proceed();
        }finally {
            DynamicDataSource.setDataSourceKey(DataSourceTypeConstant.MASTER.getDesc());
            log.info("数据源切换回主数据源。。。");
        }
    }
}
