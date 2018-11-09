package com.cn.handler;

import com.cn.entity.PushPayload;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MqttMessageCreate {
    @Autowired
    private MqttProducer mqttProducer;

    /**
     * 未登录应答
     * @return
     */
    public void createUnauthorizedMessage(PushPayload pushPayload) throws Exception{

        MqttMessage mqttMessage=new MqttMessage();
        mqttMessage.setQos(2);
        mqttMessage.setRetained(false);
        /**
         * 设置有效载体，需要二进制数据，
         */
        //mqttMessage.setPayload(pushPayload.toBytes());
        mqttProducer.publish("$client/".concat(pushPayload.getSn()),mqttMessage);
    }

}
