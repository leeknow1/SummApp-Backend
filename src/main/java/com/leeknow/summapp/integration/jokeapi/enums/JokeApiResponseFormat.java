package com.leeknow.summapp.integration.jokeapi.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum JokeApiResponseFormat {

    XML("xml"),
    YAML("yaml"),
    PLAIN_TEXT("txt");

    @JsonValue
    private final String name;

    JokeApiResponseFormat(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
