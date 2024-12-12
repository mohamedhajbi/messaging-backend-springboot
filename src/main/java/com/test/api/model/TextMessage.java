package com.test.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TextMessage implements MessageInterface {

    @Id
    private String id;

    // Define other fields and methods as needed
    private String content;

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void operation3() {
        // Implementation of the method
    }
}
