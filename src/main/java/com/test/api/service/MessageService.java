package com.test.api.service;

import com.test.api.model.Message;

import java.util.List;

public interface MessageService {
    Message createMessage(Message message);
    List<Message> getAllMessages();
    void deleteMessage(String id);
    Message sendMessage(String chatRoomId, String userId, String email, Message message);
    Message getMessageById(String messageId);
}
