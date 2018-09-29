package com.cn.enums;

import lombok.Getter;

/**
 * 消息队列topic交换路由key配置枚举
 */
public enum TopicEnum {
    /**
     * 用户注册topic路由key配置
     */
    USER_REGISTER("register.user")
    ;

    private String topicRouteKey;

    TopicEnum(String topicRouteKey) {
        this.topicRouteKey = topicRouteKey;
    }

    public String getTopicRouteKey() {
        return topicRouteKey;
    }
}
