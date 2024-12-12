//package com.test.api.controller;
//
//import com.test.api.model.ChatRoomSubscription;
//import com.test.api.repository.ChatRoomSubscriptionRepository;
//import org.eclipse.paho.client.mqttv3.MqttClient;
//import org.eclipse.paho.client.mqttv3.MqttCallback;
//import org.eclipse.paho.client.mqttv3.MqttMessage;
//import org.eclipse.paho.client.mqttv3.MqttException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//public class ChatRoomSubscriptionController {
//
//    @Autowired
//    private ChatRoomSubscriptionRepository chatRoomSubscriptionRepository;
//
//    @Autowired
//    private MqttClient mqttClient;
//
//    @PostMapping("/chatrooms/{chatRoomId}/subscribe")
//    public ChatRoomSubscription subscribeToChatRoom(@PathVariable String chatRoomId, @RequestBody ChatRoomSubscription subscription) throws MqttException {
//        // Save the subscription
//        ChatRoomSubscription savedSubscription = chatRoomSubscriptionRepository.save(subscription);
//
//        // Subscribe to the MQTT topic
//        String topic = "chatroom/" + chatRoomId;
//        mqttClient.subscribe(topic, (String t, MqttMessage message) -> {
//            System.out.println("Received message from topic " + t + ": " + new String(message.getPayload()));
//            // Handle the received message (e.g., update UI, notify users, etc.)
//        });
//
//        return savedSubscription;
//    }
//}
package com.test.api.controller;

import com.test.api.config.MqttConfig;
import com.test.api.model.ChatRoom;
import com.test.api.repository.ChatRoomRepository;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChatRoomSubscriptionController {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private MqttConfig mqttConfig;

    @PostMapping("/chatrooms/{chatRoomId}/subscribe")
    public String subscribeToChatRoom(@PathVariable String chatRoomId, Authentication authentication) throws Exception {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String userId = (String) jwt.getClaims().get("name");
        MqttClient mqttClient = mqttConfig.mqttClient();
        String topic = "chatroom/" + chatRoomId;
        mqttClient.subscribe(topic);

        return "Subscribed to chat room: " + chatRoomId;
    }
}

