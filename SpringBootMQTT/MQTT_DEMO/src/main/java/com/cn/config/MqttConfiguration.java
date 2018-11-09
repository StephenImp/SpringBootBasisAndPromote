package com.cn.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * mqtt消息属性配置类
 */
@Component
@ConfigurationProperties(prefix = "com.mqtt")
//@Data
public class MqttConfiguration {

    private String host = "tcp://127.0.0.1:1883";

    private String clientId ="myself_mqtt_demo";

    private String userName ="admin";

    private String passWord ="public";

    /**
     * 订阅的topic
     */
    private String subscribeTopic ="s2c/100120201100001C";
    /**
     * 发送的topic
     */
    private String publishTopic ="c2s/100120201100001C";

    private int timeout = 10;

    private int keepAlive = 90 ;

    private int maxInfLight = 1000000;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getSubscribeTopic() {
        return subscribeTopic;
    }

    public void setSubscribeTopic(String subscribeTopic) {
        this.subscribeTopic = subscribeTopic;
    }

    public String getPublishTopic() {
        return publishTopic;
    }

    public void setPublishTopic(String publishTopic) {
        this.publishTopic = publishTopic;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(int keepAlive) {
        this.keepAlive = keepAlive;
    }

    public int getMaxInfLight() {
        return maxInfLight;
    }

    public void setMaxInfLight(int maxInfLight) {
        this.maxInfLight = maxInfLight;
    }
}
