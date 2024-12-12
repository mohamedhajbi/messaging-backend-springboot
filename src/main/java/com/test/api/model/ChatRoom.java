package com.test.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.HashSet;
import java.util.Set;

@Document
public class ChatRoom {

    @Id
    private String id;
    private String name;

    @DBRef
    private Set<ChatRoomSubscription> subscriptions = new HashSet<>();

    @DBRef
    private Message lastMessage;

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ChatRoomSubscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<ChatRoomSubscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }
}
