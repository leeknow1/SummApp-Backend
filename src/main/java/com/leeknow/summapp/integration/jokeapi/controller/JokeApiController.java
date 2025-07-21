package com.leeknow.summapp.integration.jokeapi.controller;

import com.leeknow.summapp.integration.jokeapi.client.JokeApiClient;
import com.leeknow.summapp.integration.jokeapi.dto.JokeApiDTO;
import com.leeknow.summapp.integration.jokeapi.dto.JokeApiRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/joke-api")
@RequiredArgsConstructor
public class JokeApiController {

    private final JokeApiClient jokeApiClient;

    @PostMapping
    public ResponseEntity<?> getJokes(@RequestBody JokeApiRequestDTO jokeApiDTO) {
        if (jokeApiDTO.getAmount() != null && jokeApiDTO.getAmount() > 1) {
            return ResponseEntity.ok(jokeApiClient.getMultipleJokes(jokeApiDTO));
        }
        JokeApiDTO apiClientJokes = jokeApiClient.getJokes(jokeApiDTO);
        return ResponseEntity.ok(apiClientJokes);
    }
}
