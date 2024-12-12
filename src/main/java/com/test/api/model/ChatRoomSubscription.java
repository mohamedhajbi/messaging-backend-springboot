package com.test.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Document
public class ChatRoomSubscription {

    @Id
    private String id;

    @DBRef
    private User member;

    @DBRef
    private ChatRoom chatRoom;

    @DBRef
    private Message lastSeenMessage;

    @DBRef
    private Message lastReceivedMessage;



    public ChatRoomSubscription(User member, ChatRoom chatRoom) {
        this.member = member;
        this.chatRoom = chatRoom;
    }
    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        this.member = member;
    }

    public Message getLastSeenMessage() {
        return lastSeenMessage;
    }

    public void setLastSeenMessage(Message lastSeenMessage) {
        this.lastSeenMessage = lastSeenMessage;
    }

    public Message getLastReceivedMessage() {
        return lastReceivedMessage;
    }

    public void setLastReceivedMessage(Message lastReceivedMessage) {
        this.lastReceivedMessage = lastReceivedMessage;
    }
    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }
}
