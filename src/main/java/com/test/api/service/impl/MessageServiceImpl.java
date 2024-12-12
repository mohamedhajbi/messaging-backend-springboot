package com.test.api.service.impl;

import com.test.api.events.MessageSentEvent;
import com.test.api.model.Message;
import com.test.api.model.User;
import com.test.api.model.ChatRoom;
import com.test.api.service.UserService;
import com.test.api.service.ChatRoomService;
import com.test.api.service.ChatRoomSubscriptionService;
import com.test.api.service.MessageService;
import com.test.api.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;
    private final ChatRoomService chatRoomService;
    private final ApplicationEventPublisher publisher;
    private final ChatRoomSubscriptionService chatRoomSubscriptionService;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, UserService userService,
                              ChatRoomService chatRoomService, ChatRoomSubscriptionService chatRoomSubscriptionService,
                              ApplicationEventPublisher publisher) {
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.chatRoomService = chatRoomService;
        this.chatRoomSubscriptionService = chatRoomSubscriptionService;
        this.publisher = publisher;
    }

    @Override
    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public void deleteMessage(String id) {
        messageRepository.deleteById(id);
    }

    @Override
    public Message sendMessage(String chatRoomId, String userId, String email, Message message) {
        // Get or create user
        User user = userService.findOrCreateUser(userId, email);

        // Find chat room
        ChatRoom chatRoom = chatRoomService.getChatRoomById(chatRoomId);

        // Subscribe user to chat room if not already subscribed
        chatRoomSubscriptionService.subscribeUserToChatRoom(user, chatRoom);

        // Set additional message details
        message.setSender(user);
        message.setDate(LocalDateTime.now());
        message.setChatRoom(chatRoom);

        // Save and return the message
        Message savedMessage = messageRepository.save(message);

        // Publish message sent event
        publisher.publishEvent(new MessageSentEvent(savedMessage, chatRoom));

        return savedMessage;
    }

    @Override
    public Message getMessageById(String messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));
    }
}
