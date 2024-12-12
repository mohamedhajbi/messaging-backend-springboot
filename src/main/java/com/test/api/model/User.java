package com.test.api.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String email;

    @DBRef
    private Set<Message> messages = new HashSet<>();

    @DBRef
    private Set<ChatRoomSubscription> subscriptions = new HashSet<>();

    // Getters and setters

    public User(String id, String email) {
        this.id = id;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public Set<ChatRoomSubscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<ChatRoomSubscription> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
