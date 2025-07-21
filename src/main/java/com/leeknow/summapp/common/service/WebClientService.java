package com.leeknow.summapp.common.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WebClientService {

    public <T> T get(WebClient webClient, String endpoint, Class<T> responseType) {
        return webClient.get()
                .uri(endpoint)
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }

    public <T> T post(WebClient webClient, String endpoint, Object body, Class<T> responseType) {
        return webClient.post()
                .uri(endpoint)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }
}
