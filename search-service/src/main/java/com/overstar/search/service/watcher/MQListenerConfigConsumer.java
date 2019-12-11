package com.overstar.search.service.watcher;

import com.alibaba.fastjson.JSON;
import com.overstar.core.constants.MessageConsumerGroup;
import com.overstar.core.constants.MessageOrderTags;
import com.overstar.core.constants.MessageTopics;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.UtilAll;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author stanley.yu
 * @Date 2019/10/16 18:18
 */
@Service
@Slf4j
@RocketMQMessageListener(topic = MessageTopics.OVER_STAR_ORDER, selectorExpression = MessageOrderTags.ORDER_CREATE
        , consumerGroup = MessageConsumerGroup.OVER_STAR_CONSUMER)
public class MQListenerConfigConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    @Override
    public void onMessage(MessageExt genericMessage) {
        log.info("收到order消息-messageId={}", genericMessage.getMsgId());
        log.info(JSON.toJSONString(genericMessage));
    }

    /**
     * 预先对消费者进行配置，包括：线程数，从哪里开始消费，超时时间等等
     * <p>
     * 从当前的开始消费。之前的不消费
     *
     * @param consumer
     */
    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_TIMESTAMP);
        consumer.setConsumeTimestamp(UtilAll.timeMillisToHumanString3(System.currentTimeMillis()));
    }
}
