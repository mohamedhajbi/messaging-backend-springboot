package com.test.api.service.impl;

import com.test.api.model.ChatRoom;
import com.test.api.repository.ChatRoomRepository;
import com.test.api.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public ChatRoomServiceImpl(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    @Override
    public ChatRoom createChatRoom(ChatRoom chatRoom) {
        return chatRoomRepository.save(chatRoom);
    }

    @Override
    public ChatRoom getChatRoomById(String chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("Chat room not found"));
    }

    @Override
    public List<ChatRoom> getAllChatRooms() {
        return chatRoomRepository.findAll();
    }

    @Override
    public ChatRoom updateChatRoom(String id, ChatRoom chatRoom) {
        chatRoom.setId(id);
        return chatRoomRepository.save(chatRoom);
    }

    @Override
    public void deleteChatRoom(String id) {
        chatRoomRepository.deleteById(id);
    }
}
