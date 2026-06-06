package com.anarmitra.backend.repository;

import com.anarmitra.backend.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderBySentAtAsc(
            Long senderId,
            Long receiverId,
            Long receiverId2,
            Long senderId2
    );
}