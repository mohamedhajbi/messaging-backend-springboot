package com.test.api.repository;
import com.test.api.model.ChatRoom;
import com.test.api.model.ChatRoomSubscription;
import com.test.api.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface ChatRoomSubscriptionRepository extends MongoRepository<ChatRoomSubscription, String> {
    Optional<ChatRoomSubscription> findByMemberAndChatRoom(User member, ChatRoom chatRoom);
    List<ChatRoomSubscription> findAllByChatRoom(ChatRoom chatRoom);


}
