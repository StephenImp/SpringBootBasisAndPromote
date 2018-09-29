package com.cn.rabbitmq.support;

import com.cn.enums.ExchangeEnum;
import com.cn.rabbitmq.QueueMessageService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息队列业务逻辑实现
 */
@Component
public class QueueMessageServiceSupport
    implements QueueMessageService
{
    /**
     * 消息队列模板
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void send(Object message, ExchangeEnum exchangeEnum, String routingKey) throws Exception {
        //发送消息到消息队列
        rabbitTemplate.convertAndSend(exchangeEnum.getName(),routingKey,message);
    }
}
