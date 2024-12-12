package com.test.api;

import com.test.api.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class ApplicationStartupRunner implements CommandLineRunner {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void run(String...args) throws Exception {
        if (!mongoTemplate.collectionExists(User.class)) {
            mongoTemplate.createCollection(User.class);
        }
        if (!mongoTemplate.collectionExists(Message.class)) {
            mongoTemplate.createCollection(Message.class);
        }
        if (!mongoTemplate.collectionExists(ChatRoom.class)) {
            mongoTemplate.createCollection(ChatRoom.class);
        }
        if (!mongoTemplate.collectionExists(ChatRoomSubscription.class)) {
            mongoTemplate.createCollection(ChatRoomSubscription.class);
        }
        if (!mongoTemplate.collectionExists(TextMessage.class)) {
            mongoTemplate.createCollection(TextMessage.class);
        }
    }
}
