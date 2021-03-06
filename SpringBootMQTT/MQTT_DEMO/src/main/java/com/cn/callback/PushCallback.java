package com.cn.callback;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by MOZi on 2018/10/30.
 *
 * mqtt推送回调类
 */
public class PushCallback implements MqttCallback {

    // 连接丢失后，一般在这里面进行重连
    public void connectionLost(Throwable cause) {
        System.out.println("连接断开，可以做重连");
    }

    //消息发送成功回调。
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete---------" + token.isComplete());
    }

    // subscribe后得到的消息会执行到这里面
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("接收消息主题 : " + topic);
        System.out.println("接收消息Qos : " + message.getQos());
        System.out.println("接收消息内容 : " + new String(message.getPayload()));
    }

}
