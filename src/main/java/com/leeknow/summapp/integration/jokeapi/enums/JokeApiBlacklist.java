package com.leeknow.summapp.integration.jokeapi.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum JokeApiBlacklist {

    NSFW("nsfw"),
    RELIGIOUS("religious"),
    POLITICAL("political"),
    RACIST("racist"),
    SEXIST("sexist"),
    EXPLICIT("explicit");

    @JsonValue
    private final String name;

    JokeApiBlacklist(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
