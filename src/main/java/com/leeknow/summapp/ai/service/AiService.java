package com.leeknow.summapp.ai.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leeknow.summapp.common.dto.SimpleDTO;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Service
public class AiService {

    private final ChatClient chatClient;

    @Value("${spring.ai.gemini.api-key}")
    private String geminiKey;

    public AiService(@Qualifier("ollamaChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public Map<String, Object> sendMessage(SimpleDTO message) {
        Map<String, Object> result = new HashMap<>();
        String content;
        content = chatClient.prompt().user(message.getMessage()).call().content();

        if (content != null) {
            if (content.contains("</think>"))
                content = content.split("</think>")[1];

            content = removeUnwanted(content);
        }
        result.put("message", content);
        return result;
    }

    public Map<String, Object> sendGeminiAI(SimpleDTO message) throws IOException, InterruptedException {
        Map<String, Object> result = new HashMap<>();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(GeminiAIUtil.getURL(geminiKey)))
                .headers("Content-Type", "application/json")
                .method(HttpMethod.POST.name(), HttpRequest.BodyPublishers.ofString(GeminiAIUtil.getJSON(message.getMessage())))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response.body());
        String text = jsonNode.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();
        text = removeUnwanted(text);

        result.put("message", text);
        return result;
    }

    private String removeUnwanted(String text) {
        return text.replaceAll("[\n*]", "").replaceAll("[^\\x20-\\x7Eа-яА-ЯёЁ0-9]", "");
    }
}
