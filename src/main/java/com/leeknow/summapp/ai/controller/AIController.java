package com.leeknow.summapp.ai.controller;

import com.leeknow.summapp.common.dto.SimpleDTO;
import com.leeknow.summapp.ai.service.AiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AIController {

    private final AiService aiService;

    @PostMapping("/send/openai")
    public ResponseEntity<?> sendOpenAI(@RequestBody @Valid SimpleDTO message) {
        Map<String, Object> result = aiService.sendOpenAI(message);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/send/gemini")
    public ResponseEntity<?> generateContent(@RequestBody @Valid SimpleDTO message) throws IOException, InterruptedException {
        Map<String, Object> result = aiService.sendGeminiAI(message);
        return ResponseEntity.ok().body(result);
    }
}
