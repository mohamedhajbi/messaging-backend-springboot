package com.test.api.listeners;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Notification;
import com.test.api.events.MessageSentEvent;
import com.test.api.model.Message;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class FirebaseEventListener  {

    private final MqttClient mqttClient;

    public FirebaseEventListener(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    @EventListener(MessageSentEvent.class)
    @Async
    public void sendWithFirebase(MessageSentEvent event) {


        Message message = (Message) event.getSource();
        // Construire la notification Firebase
        Notification notification = Notification.builder()
                .setTitle("New Message in " + event.getChatRoom().getName().replaceAll("[^a-zA-Z0-9 ]", ""))
                .setBody(message.getContent().replaceAll("[^a-zA-Z0-9 ]", ""))
                .build();

        // Nettoyer et valider le nom du sujet Firebase
        String firebaseTopic = "chatroom_" + event.getChatRoom().getId().replaceAll("[^a-zA-Z0-9_-]", "");

        com.google.firebase.messaging.Message firebaseMessage = com.google.firebase.messaging.Message.builder()
                .setNotification(notification)
                .setTopic(firebaseTopic)
                .build();

        try {
            FirebaseMessaging.getInstance().send(firebaseMessage);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send Firebase notification: " + e.getMessage(), e);
        }

    }
}