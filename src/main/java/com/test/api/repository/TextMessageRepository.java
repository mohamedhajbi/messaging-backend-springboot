package com.test.api.repository;

import com.test.api.model.TextMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TextMessageRepository extends MongoRepository<TextMessage, String> {
}
