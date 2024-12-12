package com.test.api.service;

import com.test.api.model.ChatRoom;
import java.util.List;

public interface ChatRoomService {
    ChatRoom createChatRoom(ChatRoom chatRoom);
    ChatRoom getChatRoomById(String chatRoomId);
    List<ChatRoom> getAllChatRooms();
    ChatRoom updateChatRoom(String id, ChatRoom chatRoom);
    void deleteChatRoom(String id);
}
