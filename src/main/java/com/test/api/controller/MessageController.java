//package com.test.api.controller;
//
//import com.test.api.model.Message;
//import com.test.api.model.ChatRoom;
//import com.test.api.repository.MessageRepository;
//import com.test.api.repository.ChatRoomRepository;
//import org.eclipse.paho.client.mqttv3.MqttClient;
//import org.eclipse.paho.client.mqttv3.MqttMessage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//public class MessageController {
//
//    @Autowired
//    private MessageRepository messageRepository;
//
//    @Autowired
//    private ChatRoomRepository chatRoomRepository;
//
//    @Autowired
//    private MqttClient mqttClient;
//
//    @PostMapping("/chatrooms/{chatRoomId}/messages")
//    public Message sendMessage(@PathVariable String chatRoomId, @RequestBody Message message) throws Exception {
//        // Set chat room and save message
//        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new RuntimeException("Chat room not found"));
//        message.setChatRoom(chatRoom);
//        message = messageRepository.save(message);
//
//        // Publish message to MQTT topic
//        String topic = "chatroom/" + chatRoomId;
//        MqttMessage mqttMessage = new MqttMessage(message.getContent().getBytes());
//        mqttClient.publish(topic, mqttMessage);
//
//        return message;
//    }
//}
//2
//2
//2
//package com.test.api.controller;
//
//import com.test.api.config.MqttConfig;
//import com.test.api.model.Message;
//import com.test.api.model.ChatRoom;
//import com.test.api.repository.MessageRepository;
//import com.test.api.repository.ChatRoomRepository;
//import org.eclipse.paho.client.mqttv3.MqttClient;
//import org.eclipse.paho.client.mqttv3.MqttMessage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.jwt.Jwt;
//
//import java.time.LocalDateTime;
//
//@RestController
//public class MessageController {
//
//    @Autowired
//    private MessageRepository messageRepository;
//
//    @Autowired
//    private ChatRoomRepository chatRoomRepository;
//
//    @Autowired
//    private MqttConfig mqttConfig;
//
//    @PostMapping("/chatrooms/{chatRoomId}/messages")
//    public Message sendMessage(@PathVariable String chatRoomId, @RequestBody Message message, Authentication authentication) throws Exception {
//        // Retrieve user ID from Firebase authentication
//        Jwt jwt = (Jwt) authentication.getPrincipal();
//        String userId = (String) jwt.getClaims().get("name");
//
//        message.setDate(LocalDateTime.now());
//        // Initialize MQTT client with user's Firebase ID
//        MqttClient mqttClient = mqttConfig.mqttClient(userId);
//
//        // Set chat room and save message
//        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new RuntimeException("Chat room not found"));
//        message.setChatRoom(chatRoom);
//        message = messageRepository.save(message);
//
//        // Publish message to MQTT topic
//        String topic = "chatroom/" + chatRoomId;
//        MqttMessage mqttMessage = new MqttMessage(message.getContent().getBytes());
//        mqttClient.publish(topic, mqttMessage);
//
//        return message;
//    }
//}
//3
//package com.test.api.controller;
//
//import com.test.api.config.MqttConfig;
//import com.test.api.model.ChatRoom;
//import com.test.api.model.ChatRoomSubscription;
//import com.test.api.model.Message;
//import com.test.api.model.User;
//import com.test.api.repository.ChatRoomRepository;
//import com.test.api.repository.MessageRepository;
//import com.test.api.repository.ChatRoomSubscriptionRepository;
//import com.test.api.repository.UserRepository;
//import org.eclipse.paho.client.mqttv3.MqttClient;
//import org.eclipse.paho.client.mqttv3.MqttMessage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/chatrooms/{chatRoomId}/messages")
//public class MessageController {
//
//    @Autowired
//    private MessageRepository messageRepository;
//
//    @Autowired
//    private ChatRoomRepository chatRoomRepository;
//
//    @Autowired
//    private MqttConfig mqttConfig;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private ChatRoomSubscriptionRepository chatRoomSubscriptionRepository;
//
//    @PostMapping
//    public Message sendMessage(@PathVariable String chatRoomId, @RequestBody Message message, Authentication authentication) throws Exception {
//        // Récupération des informations utilisateur à partir du jeton JWT
//        Jwt jwt = (Jwt) authentication.getPrincipal();
//        String userId = (String) jwt.getClaims().get("sub"); // Utilize "sub" for Firebase UID
//        String email = (String) jwt.getClaims().get("email");
//
//        // Vérification de l'existence de l'utilisateur dans la base de données
//        Optional<User> optionalUser = userRepository.findById(userId);
//        User user = optionalUser.orElseGet(() -> {
//            User newUser = new User(userId, email);
//            return userRepository.save(newUser);
//        });
//
//        // Récupération de la salle de discussion (ChatRoom)
//        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
//                .orElseThrow(() -> new RuntimeException("Chat room not found"));
//
//        // Vérification de l'abonnement de l'utilisateur à la salle de discussion
//        Optional<ChatRoomSubscription> optionalSubscription = chatRoomSubscriptionRepository.findByMemberAndChatRoom(user, chatRoom);
//        if (optionalSubscription.isEmpty()) {
//            // Créer un nouvel abonnement si l'utilisateur n'est pas encore abonné
//            ChatRoomSubscription newSubscription = new ChatRoomSubscription(user, chatRoom);
//            chatRoomSubscriptionRepository.save(newSubscription);
//        }
//
//        // Association du message à l'utilisateur et définition de la date d'envoi
//        message.setSender(user);
//        message.setDate(LocalDateTime.now());
//        message.setChatRoom(chatRoom);
//
//        // Sauvegarde du message dans la base de données
//        message = messageRepository.save(message);
//
//        // Initialisation du client MQTT et publication du message
//        MqttClient mqttClient = mqttConfig.mqttClient(userId);
//        String topic = "chatroom/" + chatRoomId;
//        MqttMessage mqttMessage = new MqttMessage(message.getContent().getBytes());
//        mqttClient.publish(topic, mqttMessage);
//
//        return message;
//    }
//}
//4
//4
//4
//4
//package com.test.api.controller;
//
//import com.test.api.config.MqttConfig;
//import com.test.api.model.ChatRoom;
//import com.test.api.model.ChatRoomSubscription;
//import com.test.api.model.Message;
//import com.test.api.model.User;
//import com.test.api.repository.ChatRoomRepository;
//import com.test.api.repository.MessageRepository;
//import com.test.api.repository.ChatRoomSubscriptionRepository;
//import com.test.api.repository.UserRepository;
//import org.eclipse.paho.client.mqttv3.MqttClient;
//import org.eclipse.paho.client.mqttv3.MqttMessage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/chatrooms/{chatRoomId}/messages")
//public class MessageController {
//
//    @Autowired
//    private MessageRepository messageRepository;
//
//    @Autowired
//    private ChatRoomRepository chatRoomRepository;
//
//    @Autowired
//    private MqttConfig mqttConfig;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private ChatRoomSubscriptionRepository chatRoomSubscriptionRepository;
//
//    @PostMapping
//    public Message sendMessage(@PathVariable String chatRoomId, @RequestBody Message message, Authentication authentication) throws Exception {
//        // Récupération des informations utilisateur à partir du jeton JWT
//        Jwt jwt = (Jwt) authentication.getPrincipal();
//        String userId = (String) jwt.getClaims().get("sub"); // Utilisation de "sub" pour l'UID Firebase
//        String email = (String) jwt.getClaims().get("email");
//
//        // Vérification de l'existence de l'utilisateur dans la base de données
//        Optional<User> optionalUser = userRepository.findById(userId);
//        User user = optionalUser.orElseGet(() -> {
//            User newUser = new User(userId, email);
//            return userRepository.save(newUser);
//        });
//
//        // Récupération de la salle de discussion (ChatRoom)
//        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
//                .orElseThrow(() -> new RuntimeException("Chat room not found"));
//
//        // Vérification de l'abonnement de l'utilisateur à la salle de discussion
//        Optional<ChatRoomSubscription> optionalSubscription = chatRoomSubscriptionRepository.findByMemberAndChatRoom(user, chatRoom);
//        if (optionalSubscription.isEmpty()) {
//            // Créer un nouvel abonnement si l'utilisateur n'est pas encore abonné
//            ChatRoomSubscription newSubscription = new ChatRoomSubscription(user, chatRoom);
//            chatRoomSubscriptionRepository.save(newSubscription);
//        }
//
//        // Association du message à l'utilisateur et définition de la date d'envoi
//        message.setSender(user);
//        message.setDate(LocalDateTime.now());
//        message.setChatRoom(chatRoom);
//
//        // Sauvegarde du message dans la base de données
//        message = messageRepository.save(message);
//
//        // Initialisation du client MQTT et publication du message
//        MqttClient mqttClient = mqttConfig.mqttClient(userId);
//        String topic = "chatroom/" + chatRoomId;
//        MqttMessage mqttMessage = new MqttMessage(message.getContent().getBytes());
//        mqttClient.publish(topic, mqttMessage);
//
//        return message;
//    }
//
//    @PostMapping("/readMessage/{messageId}")
//    public ResponseEntity<Void> markMessageAsSeen(@PathVariable String messageId, Authentication authentication) {
//        Jwt jwt = (Jwt) authentication.getPrincipal();
//        String userId = (String) jwt.getClaims().get("sub");
//
//        // Récupération de l'utilisateur et du message
//        Optional<User> optionalUser = userRepository.findById(userId);
//        User user = optionalUser.orElseThrow(() -> new RuntimeException("User not found"));
//
//        Optional<Message> optionalMessage = messageRepository.findById(messageId);
//        Message message = optionalMessage.orElseThrow(() -> new RuntimeException("Message not found"));
//
//        // Vérifier l'abonnement de l'utilisateur à la salle de discussion
//        ChatRoom chatRoom = message.getChatRoom();
//        Optional<ChatRoomSubscription> optionalSubscription = chatRoomSubscriptionRepository.findByMemberAndChatRoom(user, chatRoom);
//
//        ChatRoomSubscription subscription = optionalSubscription.orElseThrow(() -> new RuntimeException("Subscription not found"));
//
//        // Mettre à jour le dernier message vu
//        subscription.setLastSeenMessage(message);
//        chatRoomSubscriptionRepository.save(subscription);
//
//        return ResponseEntity.ok().build();
//    }
//}
//55
//package com.test.api.controller;
//
//import com.test.api.events.MessageSentEvent;
//import com.test.api.model.ChatRoom;
//import com.test.api.model.ChatRoomSubscription;
//import com.test.api.model.Message;
//import com.test.api.model.User;
//import com.test.api.repository.ChatRoomRepository;
//import com.test.api.repository.MessageRepository;
//import com.test.api.repository.ChatRoomSubscriptionRepository;
//import com.test.api.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/chatrooms/{chatRoomId}/messages")
//public class MessageController {
//
//    @Autowired
//    private MessageRepository messageRepository;
//
//    @Autowired
//    private ChatRoomRepository chatRoomRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private ChatRoomSubscriptionRepository chatRoomSubscriptionRepository;
//
//    private ApplicationEventPublisher publisher;
//
//    @PostMapping
//    public Message sendMessage(@PathVariable String chatRoomId, @RequestBody Message message, Authentication authentication) throws Exception {
//        Jwt jwt = (Jwt) authentication.getPrincipal();
//        String userId = (String) jwt.getClaims().get("sub");
//        String email = (String) jwt.getClaims().get("email");
//
//        //UserService
//        User user = userRepository.findById(userId).orElseGet(() -> {
//            User newUser = new User(userId, email);
//            return userRepository.save(newUser);
//        });
//
//
//        //ChatroomService
//        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
//                .orElseThrow(() -> new RuntimeException("Chat room not found"));
//
//
//        Optional<ChatRoomSubscription> optionalSubscription = chatRoomSubscriptionRepository.findByMemberAndChatRoom(user, chatRoom);
//        if (optionalSubscription.isEmpty()) {
//            ChatRoomSubscription newSubscription = new ChatRoomSubscription(user, chatRoom);
//            chatRoomSubscriptionRepository.save(newSubscription);
//        }
//        ///////
//
//        message.setSender(user);
//        message.setDate(LocalDateTime.now());
//        message.setChatRoom(chatRoom);
//
//
//        // MessageService
//        message = messageRepository.save(message);
//
//        // Réponse
//
//        // Event Listener
//        publisher.publishEvent(new MessageSentEvent(message, chatRoom));
//
//        return message;
//    }
//
//    @PostMapping("/readMessage/{messageId}")
//    public ResponseEntity<Void> markMessageAsSeen(@PathVariable String messageId, Authentication authentication) {
//        Jwt jwt = (Jwt) authentication.getPrincipal();
//        String userId = (String) jwt.getClaims().get("sub");
//
//        Optional<User> optionalUser = userRepository.findById(userId);
//        User user = optionalUser.orElseThrow(() -> new RuntimeException("User not found"));
//
//        Optional<Message> optionalMessage = messageRepository.findById(messageId);
//        Message message = optionalMessage.orElseThrow(() -> new RuntimeException("Message not found"));
//
//        ChatRoom chatRoom = message.getChatRoom();
//        Optional<ChatRoomSubscription> optionalSubscription = chatRoomSubscriptionRepository.findByMemberAndChatRoom(user, chatRoom);
//
//        ChatRoomSubscription subscription = optionalSubscription.orElseThrow(() -> new RuntimeException("Subscription not found"));
//
//        subscription.setLastSeenMessage(message);
//        chatRoomSubscriptionRepository.save(subscription);
//
//        return ResponseEntity.ok().build();
//    }
//}


package com.test.api.controller;

import com.test.api.model.Message;
import com.test.api.service.MessageService;
import com.test.api.service.ChatRoomSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chatrooms/{chatRoomId}/messages")
public class MessageController {

    private final MessageService messageService;
    private final ChatRoomSubscriptionService chatRoomSubscriptionService;

    @Autowired
    public MessageController(MessageService messageService, ChatRoomSubscriptionService chatRoomSubscriptionService) {
        this.messageService = messageService;
        this.chatRoomSubscriptionService = chatRoomSubscriptionService;
    }

    @PostMapping
    public ResponseEntity<Message> sendMessage(@PathVariable String chatRoomId, @RequestBody Message message, Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String userId = (String) jwt.getClaims().get("sub");
        String email = (String) jwt.getClaims().get("email");

        Message savedMessage = messageService.sendMessage(chatRoomId, userId, email, message);
        return ResponseEntity.ok(savedMessage);
    }

    @PostMapping("/readMessage/{messageId}")
    public ResponseEntity<Void> markMessageAsSeen(@PathVariable String messageId, Authentication authentication) {
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String userId = (String) jwt.getClaims().get("sub");

        chatRoomSubscriptionService.markMessageAsSeen(userId, messageId);
        return ResponseEntity.ok().build();
    }
}








