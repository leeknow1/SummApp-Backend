package com.leeknow.summapp.integration.jokeapi.client;

import com.leeknow.summapp.integration.jokeapi.dto.JokeApiDTO;
import com.leeknow.summapp.integration.jokeapi.dto.JokeApiMultipleDTO;
import com.leeknow.summapp.integration.jokeapi.dto.JokeApiRequestDTO;
import com.leeknow.summapp.integration.jokeapi.enums.JokeApiLang;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.leeknow.summapp.integration.jokeapi.constatnt.JokeApiConstant.JOKE_API_BASE_URL;

@Service
public class JokeApiClient {

    private final WebClient webClient;

    public JokeApiClient(@Qualifier("webClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public JokeApiDTO getJokes(JokeApiRequestDTO requestDTO) {
        return webClient.get()
                .uri(JOKE_API_BASE_URL + getUrlParameters(requestDTO))
                .exchangeToMono(clientResponse -> {
                    HttpStatusCode code = clientResponse.statusCode();
                    if (code.is2xxSuccessful() || code.is4xxClientError()) {
                        return clientResponse.bodyToMono(JokeApiDTO.class);
                    } else {
                        return clientResponse.createException().flatMap(Mono::error);
                    }
                }).block();
    }

    public JokeApiMultipleDTO getMultipleJokes(JokeApiRequestDTO requestDTO) {
        return webClient.get()
                .uri(JOKE_API_BASE_URL + getUrlParameters(requestDTO))
                .exchangeToMono(clientResponse -> {
                    HttpStatusCode code = clientResponse.statusCode();
                    if (code.is2xxSuccessful() || code.is4xxClientError()) {
                        return clientResponse.bodyToMono(JokeApiMultipleDTO.class);
                    } else {
                        return clientResponse.createException().flatMap(Mono::error);
                    }
                }).block();
    }

    private String getUrlParameters(JokeApiRequestDTO requestDTO) {
        StringBuilder url = new StringBuilder();
        boolean hasParams = false;

        url.append(StringUtils.join(requestDTO.getCategory(), ",")).append("?");

        if (requestDTO.getLang() != null && requestDTO.getLang() != JokeApiLang.ENGLISH) {
            hasParams = true;
            url.append("lang=").append(requestDTO.getLang());
        }

        if (requestDTO.getBlacklist() != null) {
            if (hasParams) url.append("&");
            url.append("blacklistFlags=").append(StringUtils.join(requestDTO.getBlacklist(), ","));
            hasParams = true;
        }

        if (requestDTO.getResponseFormat() != null) {
            if (hasParams) url.append("&");
            url.append("format=").append(requestDTO.getResponseFormat());
            hasParams = true;
        }

        if (requestDTO.getType() != null) {
            if (hasParams) url.append("&");
            url.append("type=").append(requestDTO.getType());
            hasParams = true;
        }

        if (requestDTO.getSearch() != null && !requestDTO.getSearch().isBlank() && !requestDTO.getSearch().isEmpty()) {
            if (hasParams) url.append("&");
            url.append("contains=").append(requestDTO.getSearch());
            hasParams = true;
        }

        if (requestDTO.getAmount() != null && requestDTO.getAmount() > 1) {
            if (hasParams) url.append("&");
            url.append("amount=").append(requestDTO.getAmount());
        }

        if (url.charAt(url.length() - 1) == '?') {
            url.deleteCharAt(url.length() - 1);
        }

        return url.toString();
    }

}
