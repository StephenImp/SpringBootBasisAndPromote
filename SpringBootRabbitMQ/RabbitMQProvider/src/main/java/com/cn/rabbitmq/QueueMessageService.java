package com.cn.rabbitmq;

import com.cn.enums.ExchangeEnum;
import com.cn.enums.QueueEnum;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * QueueMessageService的接口并且继承了RabbitTemplate.ConfirmCallback接口，
 * 而RabbitTemplate.ConfirmCallback接口是用来回调消息发送成功后的方法，
 * 当一个消息被成功写入到RabbitMQ服务端时，就会自动的回调RabbitTemplate.ConfirmCallback接口内的confirm方法完成通知
 */
public interface QueueMessageService
    extends RabbitTemplate.ConfirmCallback
{
    /**
     * 发送消息到rabbitmq消息队列
     * @param message 消息内容
     * @param exchangeEnum 交换配置枚举
     * @param queueEnum 队列配置枚举
     * @throws Exception
     */
    public void send(Object message, ExchangeEnum exchangeEnum, QueueEnum queueEnum) throws Exception;
}
