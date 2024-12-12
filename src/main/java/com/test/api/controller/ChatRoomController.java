package com.test.api.controller;

import com.test.api.model.ChatRoom;
import com.test.api.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chatrooms")
public class ChatRoomController {

    @Autowired
    private ChatRoomService chatRoomService;

    @PostMapping
    public ChatRoom createChatRoom(@RequestBody ChatRoom chatRoom) {
        return chatRoomService.createChatRoom(chatRoom);
    }

    @GetMapping("/{id}")
    public ChatRoom getChatRoomById(@PathVariable String id) {
        return chatRoomService.getChatRoomById(id);
    }

    @GetMapping
    public List<ChatRoom> getAllChatRooms() {
        return chatRoomService.getAllChatRooms();
    }

    @PutMapping("/{id}")
    public ChatRoom updateChatRoom(@PathVariable String id, @RequestBody ChatRoom chatRoom) {
        return chatRoomService.updateChatRoom(id, chatRoom);
    }

    @DeleteMapping("/{id}")
    public void deleteChatRoom(@PathVariable String id) {
        chatRoomService.deleteChatRoom(id);
    }
}
