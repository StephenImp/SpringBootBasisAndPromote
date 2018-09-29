package com.cn.config;

import com.cn.enums.ExchangeEnum;
import com.cn.enums.QueueEnum;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 用户注册消息队列配置
 * 在全局配置中按要求配置，会自动将连接注入到这里
 */
@Configuration
public class UserRegisterQueueConfiguration {

//    @Autowired
//    private ConnectionFactory connectionFactory;
//
//    // 管理
//    @Bean
//    public RabbitAdmin rabbitAdmin() {
//        return new RabbitAdmin(connectionFactory);
//    }

    /**
     * 配置路由交换对象实例
     * 配置DirectExchange实例对象，为交换设置一个名称，引用ExchangeEnum枚举配置的交换名称，
     * 消息提供者与消息消费者的交换名称必须一致才具备的第一步的通讯基础。
     * @return
     *
     * 系统启动时：创建一个Exchange的交换器到rabbitMQ
     */
    @Bean
    public DirectExchange userRegisterDirectExchange()
    {
        return new DirectExchange(ExchangeEnum.USER_REGISTER.getValue());
    }

    /**
     * 配置用户注册队列对象实例
     * 并设置持久化队列
     * 配置Queue实例对象，为消息队列设置一个名称，
     * 引用QueueEnum枚举配置的队列名称，当然队列的名称同样也是提供者与消费者之间的通讯基础。
     * @return
     *
     *
     * 系统启动时：创建一个message队列，注册到rabbitMQ中
     */
    @Bean
    public Queue userRegisterQueue()
    {
        return new Queue(QueueEnum.USER_REGISTER.getName(),true,false, false, null);
    }

    /**
     * 将用户注册队列绑定到路由交换配置上并设置指定路由键进行转发
     * 配置Binding实例对象，消息绑定的目的就是将Queue实例绑定到Exchange上，
     * 并且通过设置的路由Key进行消息转发，配置了路由Key后，只有符合该路由配置的消息才会被转发到绑定交换上的消息队列。
     * @return
     *
     *
     * 系统启动时：通过
     *      QueueEnum.USER_REGISTER.getRoutingKey()路由键
     *      将交换器（userRegisterDirectExchange）与队列（userRegisterQueue）进行绑定。
     */
    @Bean
    public Binding userRegisterBinding()
    {
        return BindingBuilder.bind(userRegisterQueue()).to(userRegisterDirectExchange()).with(QueueEnum.USER_REGISTER.getRoutingKey());
    }

}
