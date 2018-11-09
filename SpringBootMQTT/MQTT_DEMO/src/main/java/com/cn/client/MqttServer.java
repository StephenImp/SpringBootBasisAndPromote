package com.cn.client;

import com.cn.callback.PushCallback;
import com.cn.config.MqttConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by MOZi on 2018/10/30.
 *
 * 创建mqtt消息推送或订阅客户端
 */
@Slf4j
@Component
public class MqttServer {

    @Autowired
    private MqttConfiguration mqttConfiguration;

    private MqttClient client;


    public void connect(){
        try {
            client = new MqttClient(mqttConfiguration.getHost(), mqttConfiguration.getClientId(), new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(false);
            options.setUserName(mqttConfiguration.getUserName());
            options.setPassword(mqttConfiguration.getPassWord().toCharArray());
            // 设置超时时间
            options.setConnectionTimeout(mqttConfiguration.getTimeout());
            // 设置会话心跳时间
            options.setKeepAliveInterval(mqttConfiguration.getKeepAlive());
            options.setMaxInflight(mqttConfiguration.getMaxInfLight());
            try {
                client.connect(options);

                /**
                 * mqtt推送回调类
                 */
                client.setCallback(new PushCallback());

                /**
                 * 发布消息
                 */
                //client.publish(mqttConfiguration.getPublishTopic(),pushMessage.toBytes(),0,true);

                /**
                 * 订阅消息
                 */
                client.subscribe(mqttConfiguration.getSubscribeTopic(),2);

                log.info("订阅消息成功{}"+mqttConfiguration.getSubscribeTopic());

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
