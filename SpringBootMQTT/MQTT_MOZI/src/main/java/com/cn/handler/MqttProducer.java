package com.cn.handler;

import com.cn.server.MqttServer;
import com.cn.config.MqttConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MqttProducer {
    @Autowired
    private MqttConfiguration mqttConfiguration;

    /**
     *
     * @param topic
     * @param payload
     * @param qos
     * @param retained
     * @return
     * @throws MqttException
     * @throws MqttPersistenceException
     */
    public boolean publish(String topic,byte[] payload, int qos, boolean retained) throws Exception {
        if(MqttServer.mqttAsyncClient==null||!MqttServer.mqttAsyncClient.isConnected()){
            throw new Exception();
        }
        MqttMessage message = new MqttMessage(payload);
        message.setQos(qos);
        message.setRetained(retained);
        return publish(topic,message);
    }

    /**
     *
     * @param topic
     * @param mqttMessage
     * @return
     * @throws MqttException
     * @throws MqttPersistenceException
     */
    public boolean publish(String topic,MqttMessage mqttMessage) throws Exception {
        if(MqttServer.mqttAsyncClient==null||!MqttServer.mqttAsyncClient.isConnected()){
            throw new Exception();
        }
        MqttServer.mqttAsyncClient.publish(mqttConfiguration.getPublishTopic().replace("+",topic), mqttMessage);
        return true;
    }
}
