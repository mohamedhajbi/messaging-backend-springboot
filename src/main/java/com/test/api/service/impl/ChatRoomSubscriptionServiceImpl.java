package com.test.api.service.impl;

import com.test.api.model.ChatRoom;
import com.test.api.model.ChatRoomSubscription;
import com.test.api.model.Message;
import com.test.api.model.User;
import com.test.api.service.UserService;
import com.test.api.service.MessageService;
import com.test.api.repository.ChatRoomSubscriptionRepository;
import com.test.api.service.ChatRoomSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ChatRoomSubscriptionServiceImpl implements ChatRoomSubscriptionService {

    private final ChatRoomSubscriptionRepository chatRoomSubscriptionRepository;
    private final MessageService messageService;
    private final UserService userService;

    @Autowired
    public ChatRoomSubscriptionServiceImpl(ChatRoomSubscriptionRepository chatRoomSubscriptionRepository,
                                           @Lazy MessageService messageService, UserService userService) {
        this.chatRoomSubscriptionRepository = chatRoomSubscriptionRepository;
        this.messageService = messageService;
        this.userService = userService;
    }

    @Override
    public void subscribeUserToChatRoom(User user, ChatRoom chatRoom) {
        chatRoomSubscriptionRepository.findByMemberAndChatRoom(user, chatRoom)
                .orElseGet(() -> chatRoomSubscriptionRepository.save(new ChatRoomSubscription(user, chatRoom)));
    }

    @Override
    public void markMessageAsSeen(String userId, String messageId) {
        User user = userService.getUserById(userId);
        Message message = messageService.getMessageById(messageId);
        ChatRoom chatRoom = message.getChatRoom();

        ChatRoomSubscription subscription = chatRoomSubscriptionRepository
                .findByMemberAndChatRoom(user, chatRoom)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        subscription.setLastSeenMessage(message);
        chatRoomSubscriptionRepository.save(subscription);
    }
}
