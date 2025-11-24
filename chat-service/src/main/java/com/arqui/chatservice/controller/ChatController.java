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

    @PostMapping("/{accountId}")
    public ResponseEntity<ChatResponseDTO> process(
            @RequestBody ChatRequestDTO request,
            @PathVariable Long accountId /*@RequestHeader("X-USER-ID") String userId*/) {
        return ResponseEntity.ok(chatService.handleRequest(request, accountId));
    }
}
