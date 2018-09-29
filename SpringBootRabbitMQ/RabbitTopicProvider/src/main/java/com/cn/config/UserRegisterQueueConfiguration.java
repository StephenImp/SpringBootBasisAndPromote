package com.cn.config;

import com.cn.enums.ExchangeEnum;
import com.cn.enums.QueueEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 用户注册消息队列配置
 */

/**
 * 第一步： 首先我们创建了TopicExchange消息队列对象，使用ExchangeEnum枚举内的USER_REGISTER_TOPIC_EXCHANGE类型作为交换名称。

   第二步：我们创建了发送注册邮件的队列sendRegisterMailQueue，使用QueueEnum枚举内的类型USER_REGISTER_SEND_MAIL作为队列名称。

   第三步：与发送邮件队列一致，用户创建完成后需要初始化账户信息，而createAccountQueue消息队列后续逻辑就是来完成该工作，
   使用QueueEnum枚举内的USER_REGISTER_CREATE_ACCOUNT枚举作为创建账户队列名称。

   第四步：在上面步骤中已经将交换、队列创建完成，下面就开始将队列绑定到用户注册交换，从而实现注册用户消息队列消息消费，
   sendMailBinding绑定了QueueEnum.USER_REGISTER_SEND_MAIL的RoutingKey配置信息。
   createAccountBinding绑定了QueueEnum.USER_REGISTER_CREATE_ACCOUNT的RoutingKey配置信息。
 */
@Configuration
public class UserRegisterQueueConfiguration {

    private Logger logger = LoggerFactory.getLogger(UserRegisterQueueConfiguration.class);
    /**
     * 配置用户注册主题交换
     * @return
     */
    @Bean
    public TopicExchange userTopicExchange()
    {
        TopicExchange topicExchange = new TopicExchange(ExchangeEnum.USER_REGISTER_TOPIC_EXCHANGE.getName());
        logger.info("用户注册交换实例化成功。");
        return topicExchange;
    }

    /**
     * 配置用户注册
     * 发送激活邮件消息队列
     * 并设置持久化队列
     * @return
     */
    @Bean
    public Queue sendRegisterMailQueue()
    {
        Queue queue = new Queue(QueueEnum.USER_REGISTER_SEND_MAIL.getName());
        logger.info("创建用户注册消息队列成功");
        return queue;
    }

    /**
     * 配置用户注册
     * 创建账户消息队列
     * 并设置持久化队列
     * @return
     */
    @Bean
    public Queue createAccountQueue()
    {
        Queue queue = new Queue(QueueEnum.USER_REGISTER_CREATE_ACCOUNT.getName());
        logger.info("创建用户注册账号队列成功.");
        return queue;
    }

    /**
     * 绑定用户发送注册激活邮件队列到用户注册主题交换配置
     * @return
     */
    @Bean
    public Binding sendMailBinding(TopicExchange userTopicExchange,Queue sendRegisterMailQueue)
    {
        Binding binding = BindingBuilder.bind(sendRegisterMailQueue).to(userTopicExchange).with(QueueEnum.USER_REGISTER_SEND_MAIL.getRoutingKey());
        logger.info("绑定发送邮件到注册交换成功");
        return binding;
    }

    /**
     * 绑定用户创建账户到用户注册主题交换配置
     * @return
     */
    @Bean
    public Binding createAccountBinding(TopicExchange userTopicExchange,Queue createAccountQueue)
    {
        Binding binding = BindingBuilder.bind(createAccountQueue).to(userTopicExchange).with(QueueEnum.USER_REGISTER_CREATE_ACCOUNT.getRoutingKey());
        logger.info("绑定创建账号到注册交换成功。");
        return binding;
    }
}
