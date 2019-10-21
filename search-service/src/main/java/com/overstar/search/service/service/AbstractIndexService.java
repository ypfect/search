package com.overstar.search.service.service;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;

import java.util.List;

/**
 * @Description
 * 抽象任务流程
 * 产品每日任务
 * 1.创建新的索引
 * 2.索引数据{ a.查询数据 b.处理数据 c.插入}
 * 3.索引执行 别名
 * 4.删除老索引
 *
 * @Author stanley.yu
 * @Date 2019/9/11 14:20
 */
@Slf4j
public  abstract class AbstractIndexService  implements IndexService{

    @Override
    public void index() throws IllegalAccessException {
        //先准备数据
        Object datas = getData();
        if (!createIndex()) {
            log.error("创建索引失败了！类型={}",taskType());
            return;
        }

        List<IndexRequest> objects = dataPrepare(datas);
        boolean indexing = indexing(objects);
        deleteIndex();
        if (indexing) log.info("类型={}，indexing successfully",taskType());

    }

    abstract String taskType();
    abstract Object getData();
    abstract List<IndexRequest> dataPrepare(Object data) throws IllegalAccessException;
    abstract boolean createIndex();
    abstract boolean indexing(Object tem);
    abstract boolean deleteIndex();

}
