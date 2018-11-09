package com.cn;

import com.cn.callback.PushCallback;
import com.cn.contant.CommandType;
import com.cn.entity.LoginRequest;
import com.cn.entity.NettyMessage;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.BeanUtils;

@Slf4j
public class MqttClient {

    public static final String HOST = "tcp://127.0.0.1:1883";
    public static final String subscribeTOPIC = "s2c/100120201100001B";
    public static final String publishTOPIC = "s2c/100120201100001C";
    private static final String clientid = "myself_mqtt_mozi_test";

    public static org.eclipse.paho.client.mqttv3.MqttClient client;

    private String userName = "wpw";
    private String passWord = "123456";


    private void connect() throws MqttException {

        client = new org.eclipse.paho.client.mqttv3.MqttClient(HOST, clientid, new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName(userName);
        options.setPassword(passWord.toCharArray());
        // 设置超时时间
        options.setConnectionTimeout(100);
        // 设置会话心跳时间
        options.setKeepAliveInterval(90);
        options.setMaxInflight(10000);

        /**
         * mqtt推送回调类
         */
        client.setCallback(new PushCallback());

        //setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
        MqttTopic topic = client.getTopic(publishTOPIC);
        LoginRequest loginRequest=new LoginRequest();
        loginRequest.setVersion((short)1);
        loginRequest.setSn("100120201100001B");
        loginRequest.setTimestamp((int) (System.currentTimeMillis()/1000L));
        loginRequest.setCommandType(CommandType.SERVICE_LOGIN.value());
        loginRequest.setUserName("100120201100001B");
        loginRequest.setPassword("2DMxt799");
        loginRequest.setMacAddress("9C-69-B4-20-00-15");
        NettyMessage nettyMessage=new NettyMessage();

        /**
         * loginRequest 继承 nettyMessage（也就是说loginRequest包含nettyMessage）
         *
         * 将nettyMessage赋值给loginRequest
         */
        BeanUtils.copyProperties(loginRequest,nettyMessage);

        nettyMessage.setData(loginRequest.toBytes());

        options.setWill(topic, nettyMessage.toBytes(), 2, false);
        client.connect(options);
        //订阅
        client.subscribe(subscribeTOPIC,2);
    }

    public void publish(MqttTopic topic , MqttMessage message) throws MqttPersistenceException,
            MqttException {
        MqttDeliveryToken token = topic.publish(message);
        token.waitForCompletion();
    }

    public static void main(String[] args) throws MqttException {
        MqttClient mqttClient =new MqttClient();

        /**
         * 连接的时候订阅消息
         */
        mqttClient.connect();

        MqttMessage messageReport = new MqttMessage();
        messageReport.setQos(2);
        messageReport.setRetained(false);
        LoginRequest loginRequest=new LoginRequest();
        loginRequest.setVersion((short)1);
        loginRequest.setSn("100120201100001B");
        loginRequest.setTimestamp((int) (System.currentTimeMillis()/1000L));
        loginRequest.setCommandType(CommandType.SERVICE_LOGIN.value());
        loginRequest.setUserName("100120201100001B");
        loginRequest.setPassword("2DMxt799");
        loginRequest.setMacAddress("9C-69-B4-20-00-15");
        NettyMessage nettyMessage=new NettyMessage();
        BeanUtils.copyProperties(loginRequest,nettyMessage);
        nettyMessage.setData(loginRequest.toBytes());
        messageReport.setPayload(nettyMessage.toBytes());
        MqttTopic mqttTopic=client.getTopic(publishTOPIC);

        /**
         * 同时在这里发送消息
         */
        for(int i=0;i<1;i++){
            mqttTopic.publish(messageReport);
            log.debug("发送消息主题[{}],消息Qos[{}],消息内容[{}],状态[{}]",messageReport.getQos(),messageReport.getQos(),nettyMessage,messageReport.isRetained());
        }
    }
}
