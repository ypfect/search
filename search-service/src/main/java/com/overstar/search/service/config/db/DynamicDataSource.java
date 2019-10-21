package com.overstar.search.service.config.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Stack;

public class DynamicDataSource extends AbstractRoutingDataSource {
    private static final ThreadLocal<Stack<String>> DATA_SOURCE_KEY = new InheritableThreadLocal<>();

    public static void setDataSourceKey(String dataSource) {
        Stack<String> stack = DATA_SOURCE_KEY.get();
        if (stack == null) {
            stack = new Stack<>();
            DATA_SOURCE_KEY.set(stack);
        }
        stack.push(dataSource);
    }

    public static void cleanDataSourceKey() {
        Stack<String> stack = DATA_SOURCE_KEY.get();
        if (stack != null) {
            stack.pop();
            if (stack.isEmpty()) {
                DATA_SOURCE_KEY.remove();
            }
        }
    }

    /**
     * 构造
     *
     * @param targetDataSources
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public DynamicDataSource(Map<String, DataSource> targetDataSources, DataSource defaultDataSource) {
        super.setTargetDataSources((Map) targetDataSources);
        super.setDefaultTargetDataSource(defaultDataSource);
    }

    /**
     * determineCurrentLookupKey
     *
     * @return
     * @see AbstractRoutingDataSource#determineCurrentLookupKey()
     */
    @Override
    protected Object determineCurrentLookupKey() {
        Stack<String> stack = DATA_SOURCE_KEY.get();
        if (stack != null) {
            return stack.peek();
        }
        return null;
    }
}