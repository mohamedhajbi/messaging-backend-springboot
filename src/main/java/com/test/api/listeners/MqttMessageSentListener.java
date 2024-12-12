package com.test.api.listeners;

import com.test.api.events.MessageSentEvent;
import com.test.api.model.Message;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MqttMessageSentListener {

    @Autowired
    private MqttClient mqttClient;

    @EventListener(MessageSentEvent.class)
    @Async
    public void sendWithMqtt(MessageSentEvent event) {
        Message message = (Message) event.getSource();

        // Nettoyer et valider le nom du sujet MQTT
        String mqttTopic = "chatroom/" + event.getChatRoom().getId().replaceAll("[^a-zA-Z0-9/_]", "");
        MqttMessage mqttMessage = new MqttMessage(message.getContent().getBytes());
        try {
            mqttClient.publish(mqttTopic, mqttMessage);
        } catch (Exception e) {
            throw new RuntimeException("Failed to publish MQTT message: " + e.getMessage(), e);
        }
    }
}
