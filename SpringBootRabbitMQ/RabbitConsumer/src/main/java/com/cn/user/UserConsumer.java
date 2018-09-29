package com.cn.user;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 用户注册消息消费者
 * ========================
 @RabbitListener
 RabbitMQ队列消息监听注解，该注解配置监听queues内的队列名称列表，可以配置多个。
 队列名称对应rabbitmq-common模块内QueueEnum枚举name属性。

 @RabbitHandler
 RabbitMQ消息处理方法，该方法的参数要与rabbitmq-provider发送消息时的类型保持一致，
 否则无法自动调用消费方法，也就无法完成消息的消费。
 * ========================
 */
@Component
@RabbitListener(queues = "user.register.queue")
public class UserConsumer {

    @RabbitHandler
    public void execute(Long userId)
    {
        System.out.println("用户：" + userId+"，完成了注册");
        //...//自行业务逻辑处理
    }
}
