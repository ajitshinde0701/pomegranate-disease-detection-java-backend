package com.anarmitra.backend.controller;

import com.anarmitra.backend.entity.ChatMessage;
import com.anarmitra.backend.repository.ChatMessageRepository;
import com.anarmitra.backend.service.UserPresenceService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatMessageRepository chatRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserPresenceService presenceService;

    public ChatController(
            ChatMessageRepository chatRepository,
            SimpMessagingTemplate messagingTemplate,
            UserPresenceService presenceService
    ) {
        this.chatRepository = chatRepository;
        this.messagingTemplate = messagingTemplate;
        this.presenceService = presenceService;
    }

    @PostMapping("/send")
    public ChatMessage sendMessage(@RequestBody ChatMessage message) {
        ChatMessage saved = chatRepository.save(message);

        messagingTemplate.convertAndSend("/topic/chat/" + saved.getReceiverId(), saved);
        messagingTemplate.convertAndSend("/topic/chat/" + saved.getSenderId(), saved);

        return saved;
    }

    @GetMapping("/conversation")
    public List<ChatMessage> getConversation(
            @RequestParam Long senderId,
            @RequestParam Long receiverId
    ) {
        return chatRepository.findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderBySentAtAsc(
                senderId,
                receiverId,
                senderId,
                receiverId
        );
    }

    @MessageMapping("/chat.send")
    public void websocketSend(ChatMessage message) {
        ChatMessage saved = chatRepository.save(message);

        messagingTemplate.convertAndSend("/topic/chat/" + saved.getReceiverId(), saved);
        messagingTemplate.convertAndSend("/topic/chat/" + saved.getSenderId(), saved);
    }

    @MessageMapping("/user.online")
    public void userOnline(Long userId) {
        presenceService.userOnline(userId);
        messagingTemplate.convertAndSend("/topic/online", presenceService.getOnlineUsers());
    }

    @MessageMapping("/user.offline")
    public void userOffline(Long userId) {
        presenceService.userOffline(userId);
        messagingTemplate.convertAndSend("/topic/online", presenceService.getOnlineUsers());
    }

    @MessageMapping("/user.typing")
    public void typing(ChatMessage message) {
        messagingTemplate.convertAndSend(
                "/topic/typing/" + message.getReceiverId(),
                message.getSenderId()
        );
    }
}