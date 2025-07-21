package com.leeknow.summapp.integration.jokeapi.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum JokeApiType {

    SINGLE("single"),
    TWO_PART("twopart");

    @JsonValue
    private final String name;

    JokeApiType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
