//package com.test.api.config;
//
//import org.eclipse.paho.client.mqttv3.MqttClient;
//import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
//import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class MqttConfig {
//
//    private static final String BROKER_URL = "tcp://broker.emqx.io:1883";
//    private static final String CLIENT_ID = "chat-app-client";
//
//    @Bean
//    public MqttClient mqttClient() throws Exception {
//        MqttClient client = new MqttClient(BROKER_URL, CLIENT_ID, new MemoryPersistence());
//        MqttConnectOptions options = new MqttConnectOptions();
//        options.setCleanSession(true);
//        client.connect(options);
//        return client;
//    }
//}
package com.test.api.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class MqttConfig {

    private static final String BROKER_URL = "tcp://broker.emqx.io:1883";

    @Bean
    public MqttClient mqttClient() throws Exception {
        String clientId = "chat-app-client-" + UUID.randomUUID();
        MqttClient client = new MqttClient(BROKER_URL, clientId, new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        client.connect(options);
        return client;
    }
}
