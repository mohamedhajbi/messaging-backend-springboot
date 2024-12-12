package com.test.api.events;

import com.test.api.model.ChatRoom;
import com.test.api.model.Message;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class MessageSentEvent extends ApplicationEvent {
    private final ChatRoom chatRoom;
    public MessageSentEvent(Message source, ChatRoom chatRoom) {
        super(source);
        this.chatRoom = chatRoom;
    }

    public MessageSentEvent(Message source, Clock clock, ChatRoom chatRoom) {
        super(source, clock);
        this.chatRoom = chatRoom;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }
}
