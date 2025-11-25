package com.arqui.chatservice.controller;

import com.arqui.chatservice.dto.request.ChatRequestDTO;
import com.arqui.chatservice.dto.response.ChatResponseDTO;
import com.arqui.chatservice.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @PostMapping("")
    public ResponseEntity<ChatResponseDTO> process(
            @RequestBody ChatRequestDTO request,
            @RequestHeader("X-ACCOUNT-ID") Long accountId) {
        return ResponseEntity.ok(chatService.handleRequest(request, accountId));
    }
}
