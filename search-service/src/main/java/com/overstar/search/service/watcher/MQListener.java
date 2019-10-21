package com.overstar.search.service.watcher;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @Description
 * tag 只能是一个，如果直接推送消息，是按照消费组进行推送的！
 * 可以不指定tag 在代码判断也没毛病
 *
 *
 * 延时发送消息
 * @Author stanley.yu
 * @Date 2019/10/16 18:18
 */
@Service
@Slf4j
@RocketMQMessageListener(topic = "testMQ",selectorExpression = "order_create",consumerGroup = "es-consumer")
public class MQListener implements RocketMQListener<MessageExt> {


    /**
     * for (MessageExt messageExt : msgs) {
     *                 log.debug("received msg: {}", messageExt);
     *                 try {
     *                     long now = System.currentTimeMillis();
     *                     rocketMQListener.onMessage(doConvertMessage(messageExt));
     *                     long costTime = System.currentTimeMillis() - now;
     *                     log.debug("consume {} cost: {} ms", messageExt.getMsgId(), costTime);
     *                 } catch (Exception e) {
     *                     log.warn("consume message failed. messageExt:{}", messageExt, e);
     *                     context.setDelayLevelWhenNextConsume(delayLevelWhenNextConsume);
     *                     return ConsumeConcurrentlyStatus.RECONSUME_LATER;
     *                 }
     *             }
     *
     *
     * 外层做了处理，如果抛异常就直接重试
     * @param genericMessage
     */
    @Override
    public void onMessage(MessageExt genericMessage) {
        log.info("messageId={}",genericMessage.getMsgId());
        log.info(JSON.toJSONString(genericMessage));
    }
}
