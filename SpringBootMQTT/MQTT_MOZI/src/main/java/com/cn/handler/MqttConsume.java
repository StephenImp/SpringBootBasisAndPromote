package com.cn.handler;

import com.cn.server.MqttServer;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MqttConsume {


    @Autowired
    private MqttServer mqttServer;
    @Autowired
    private MqttMessageCreate mqttMessageCreate;

    public void connectionLost(Throwable cause) {
        // 连接丢失后，一般在这里面进行重连
        log.debug("MQTT服务断线重连中...");
        while (true){
            if(mqttServer.connect()){
                break;
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("MQTT服务断线重连中...");
        }
        log.debug("MQTT服务断线重连成功...");
    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        //发送消息是否成功
        log.debug("发送消息状态MessageId[{}],结果[{}]",token.getMessageId(), token.isComplete());
    }

    public void messageArrived(String topic, MqttMessage message) throws Exception {

        // subscribe后得到的消息会执行到这里面
        System.out.println("接收消息主题 : " + topic);
        System.out.println("接收消息Qos : " + message.getQos());
        System.out.println("接收消息内容 : " + new String(message.getPayload()));
    }
}
