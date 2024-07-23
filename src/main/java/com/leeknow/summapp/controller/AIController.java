package com.leeknow.summapp.controller;

import com.leeknow.summapp.dto.SimpleDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/openai")
@RequiredArgsConstructor
public class AIController {

    private final ChatClient chatClient;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody @Valid SimpleDTO message) {
        Map<String, Object> result = new HashMap<>();

        String content;
        try {
            content = chatClient.prompt().user(message.getMessage()).call().content();
        } catch (Exception e) {
            result.put("message", "Произошла ошибка!");
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(result);
        }
        result.put("message", content);
        return ResponseEntity.ok().body(result);
    }
}
