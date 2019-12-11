package com.overstar.search.service.template.job.simple;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.overstar.search.service.template.fixture.repository.FooRepository;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class SpringSimpleJob implements SimpleJob {
    
    @Resource
    private FooRepository fooRepository;
    
    @Override
    public void execute(final ShardingContext shardingContext) {
//        log.info(String.format("Item: %s | Time: %s | Thread: %s | %s",
//                shardingContext.getShardingItem(), new SimpleDateFormat("HH:mm:ss").format(new Date()), Thread.currentThread().getId(), "SIMPLE"));
//        List<Foo> data = fooRepository.findTodoData(shardingContext.getShardingParameter(), 10);
//        for (Foo each : data) {
//            fooRepository.setCompleted(each.getId());
//        }
        does();
    }


    public void does(){
        //TODO
    }
}
