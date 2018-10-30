package com.cn.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by MOZi on 2018/10/30.
 */
public class PropertiesUtil {

    public static final String MQTT_HOST;
    public static final String MQTT_CLIENT_ID;
    public static final String MQTT_USER_NAME;
    public static final String MQTT_PASSWORD;
    public static final String MQTT_Client_TOPIC;
    public static final int MQTT_TIMEOUT;
    public static final int MQTT_KEEP_ALIVE;


    //public static final String ELASTIC_SEARCH_HOST;

    //public static final int ELASTIC_SEARCH_PORT;

    //public static final String ELASTIC_SEARCH_CLUSTER_NAME;

    static {
//        MQTT_HOST = loadMqttProperties().getProperty("com.mqtt.host");
//        MQTT_CLIENTID = loadMqttProperties().getProperty("com.mqtt.clientid");
//        MQTT_USER_NAME = loadMqttProperties().getProperty("com.mqtt.username");
//        MQTT_PASSWORD = loadMqttProperties().getProperty("com.mqtt.password");
//        MQTT_TIMEOUT = Integer.valueOf(loadMqttProperties().getProperty("com.mqtt.timeout"));
//        MQTT_KEEP_ALIVE = Integer.valueOf(loadMqttProperties().getProperty("com.mqtt.keepalive"));

        MQTT_HOST = "tcp://127.0.0.1:1883";
        MQTT_CLIENT_ID = "DemoMQTT";
        MQTT_USER_NAME = "admin";
        MQTT_PASSWORD = "public";
        MQTT_Client_TOPIC = "firstMqttTest";
        MQTT_TIMEOUT = Integer.valueOf(10);
        MQTT_KEEP_ALIVE = Integer.valueOf(90);

    }

    static {
        //ELASTIC_SEARCH_HOST = loadEsProperties().getProperty("ES_HOST");
        //ELASTIC_SEARCH_PORT = Integer.valueOf(loadEsProperties().getProperty("ES_PORT"));
        //ELASTIC_SEARCH_CLUSTER_NAME = loadEsProperties().getProperty("ES_CLUSTER_NAME");
    }

    private static Properties loadMqttProperties() {
        InputStream inputstream = PropertiesUtil.class.getResourceAsStream("/bootstrap.yml");
        Properties properties = new Properties();
        try {
            properties.load(inputstream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (inputstream != null) {
                    inputstream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static Properties loadEsProperties() {
        InputStream inputstream = PropertiesUtil.class.getResourceAsStream("/elasticsearch.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputstream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (inputstream != null) {
                    inputstream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
