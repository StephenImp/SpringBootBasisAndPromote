package com.cn.enums;

/**
 * 队列配置枚举
 * ========================
 */

/**
 *
 路由特殊字符 #
 我们在QueueEnum内配置的路由键时有个特殊的符号：#，
 在RabbitMQ消息队列内路由配置#时表示可以匹配零个或多个字符，
 我们TopicEnum枚举内定义的register.user，则是可以匹配QueueEnum枚举定义register.#队列的路由规则。
 当然发送消息时如果路由传递：register.user.account也是可以同样匹配register.#的路由规则。


 路由特殊字符 *
 除此之外比较常用到的特殊字符还有一个*，在RabbitMQ消息队列内路由配置*时表示可以匹配一个字符，
 我们QueueEnum定义路由键如果修改成register.*时，发送消息时路由为register.user则是可以接受到消息的。
 但如果发送时的路由为register.user.account时，则是无法匹配该消息。

 */
public enum QueueEnum
{
    /**
     * 用户注册
     * 创建账户消息队列
     */
    USER_REGISTER_CREATE_ACCOUNT("register.account","register.#"),
    /**
     * 用户注册
     * 发送注册成功邮件消息队列
     */
    USER_REGISTER_SEND_MAIL("register.mail","register.#")
    ;
    /**
     * 队列名称
     */
    private String name;
    /**
     * 队列路由键
     */
    private String routingKey;

    QueueEnum(String name, String routingKey) {
        this.name = name;
        this.routingKey = routingKey;
    }

    public String getName() {
        return name;
    }

    public String getRoutingKey() {
        return routingKey;
    }
}
