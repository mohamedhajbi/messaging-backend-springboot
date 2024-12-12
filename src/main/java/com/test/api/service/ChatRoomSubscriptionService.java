package com.test.api.service;

import com.test.api.model.ChatRoom;
import com.test.api.model.User;

public interface ChatRoomSubscriptionService {
    void subscribeUserToChatRoom(User user, ChatRoom chatRoom);
    void markMessageAsSeen(String userId, String messageId);
}
