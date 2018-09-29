package com.cn.enums;

import lombok.Getter;

/**
 * rabbitmq交换配置枚举
 */
@Getter
public enum ExchangeEnum
{
    /**
     * 用户注册交换配置枚举
     */
    USER_REGISTER_TOPIC_EXCHANGE("register.topic.exchange")
    ;
    private String name;

    ExchangeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
