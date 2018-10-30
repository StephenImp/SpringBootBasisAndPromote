package com.cn;

import com.cn.callback.PushCallback;
import com.cn.entity.PushPayload;
import com.cn.utils.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Created by MOZi on 2018/10/30.
 *
 * 创建mqtt消息推送或订阅客户端
 */
@Slf4j
public class MqttPushClient {

    private MqttClient client;

    private static volatile MqttPushClient mqttPushClient = null;

    public static MqttPushClient getInstance(){

        if(null == mqttPushClient){
            synchronized (MqttPushClient.class){
                if(null == mqttPushClient){
                    mqttPushClient = new MqttPushClient();
                }
            }

        }
        return mqttPushClient;

    }

    private MqttPushClient() {
        connect();
    }

    private void connect(){
        try {
            client = new MqttClient(PropertiesUtil.MQTT_HOST, PropertiesUtil.MQTT_CLIENT_ID, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(false);
            options.setUserName(PropertiesUtil.MQTT_USER_NAME);
            options.setPassword(PropertiesUtil.MQTT_PASSWORD.toCharArray());
            // 设置超时时间
            options.setConnectionTimeout(PropertiesUtil.MQTT_TIMEOUT);
            // 设置会话心跳时间
            options.setKeepAliveInterval(PropertiesUtil.MQTT_KEEP_ALIVE);
            try {
                client.setCallback(new PushCallback());
                client.connect(options);

                MqttTopic topic = client.getTopic(PropertiesUtil.MQTT_Client_TOPIC);
                //这里设置遗言
                options.setWill(topic,new byte[0],2,false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发布，默认qos为0，非持久化
     * @param topic
     * @param pushMessage
     */
    public void publish(String topic,PushPayload pushMessage){
        publish(0, false, topic, pushMessage);
    }

    /**
     * 发布
     * @param qos
     * @param retained
     * @param topic
     * @param pushMessage
     */
    public void publish(int qos,boolean retained,String topic,PushPayload pushMessage){
        MqttMessage message = new MqttMessage();
        message.setQos(qos);
        message.setRetained(retained);
        message.setPayload(pushMessage.toString().getBytes());
        MqttTopic mTopic = client.getTopic(topic);
        if(null == mTopic){
            log.error("topic not exist");
        }
        MqttDeliveryToken token;
        try {
            token = mTopic.publish(message);
            token.waitForCompletion();
        } catch (MqttPersistenceException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * 订阅某个主题，qos默认为0
     * @param topic
     */
    public void subscribe(String topic){
        subscribe(topic,0);
    }

    /**
     * 订阅某个主题
     * @param topic
     * @param qos
     */
    public void subscribe(String topic,int qos){
        try {
            client.subscribe(topic, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
        String kdTopic = "firstMqttTest";

        /**
         * 封装消息体
         */
        PushPayload pushMessage = PushPayload.getPushPayloadBuider().setMobile("888")
                .setContent("testDemo")
                .bulid();

        MqttPushClient instance = MqttPushClient.getInstance();
        instance.publish(0, false, kdTopic, pushMessage);
    }

}
