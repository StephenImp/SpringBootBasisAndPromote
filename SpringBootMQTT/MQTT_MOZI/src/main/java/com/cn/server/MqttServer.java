package com.cn.server;

import com.cn.config.MqttConfiguration;
import com.cn.handler.MqttConsume;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MqttServer {
    @Autowired
    private MqttConfiguration mqttConfiguration;
    @Autowired
    private MqttConsume mqttConsume;

    /**
     * 轻量级客户端，使用非阻塞方法与MQTT服务器通信，允许操作在后台运行。
     */
    public static MqttAsyncClient mqttAsyncClient;

    /**
     * 连接服务器
     */
    public boolean connect(){
        try {
            mqttAsyncClient = new MqttAsyncClient(mqttConfiguration.getHost(), mqttConfiguration.getClientid(), new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setUserName(mqttConfiguration.getUsername());
            options.setPassword(mqttConfiguration.getPassword().toCharArray());
            options.setConnectionTimeout(mqttConfiguration.getTimeout());
            options.setKeepAliveInterval(mqttConfiguration.getKeepalive());
            options.setMaxInflight(mqttConfiguration.getMaxInflight());

            /**
             * mqtt推送回调类
             */
            mqttAsyncClient.setCallback(new MqttCallback() {

                public void connectionLost(Throwable cause) {
                    mqttConsume.connectionLost(cause);
                }

                public void deliveryComplete(IMqttDeliveryToken token) {
                    mqttConsume.deliveryComplete(token);
                }

                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    mqttConsume.messageArrived(topic,message);
                }
            });

            /**
             * 建立连接
             */
            mqttAsyncClient.connect(options,"订阅消息",new IMqttActionListener() {

                /**
                 * 成功
                 */
                @Override
                public void onSuccess(IMqttToken iMqttToken) {
                    log.info("=======================================连接mqtt服务器成功!=========================================");
                    //订阅所有客户端消息
                    IMqttToken mqttToken=null;
                    try {
                        /**
                         * 这里根据topic区订阅
                         */
                        mqttToken=mqttAsyncClient.subscribe(mqttConfiguration.getSubscribeTopic(), 2);
                        if(mqttToken!=null){
                            log.info("=======================================订阅[{}]成功!=========================================",mqttConfiguration.getSubscribeTopic());
                        }else{
                            log.warn("=======================================订阅[{}]失败!=========================================",mqttConfiguration.getSubscribeTopic());
                        }
                    } catch (MqttException e) {
                        log.error("=======================================订阅[{}]异常[{}]=========================================",mqttConfiguration.getSubscribeTopic(),e);
                    }
                }

                /**
                 * 失败
                 */
                @Override
                public void onFailure(IMqttToken iMqttToken, Throwable throwable) {
                    log.info("连接mqtt失败[{}]",throwable);
                }
            });
            return mqttAsyncClient.isConnected();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
