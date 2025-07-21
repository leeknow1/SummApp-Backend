package com.leeknow.summapp.integration.jokeapi.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum JokeApiCategory {
    ANY("Any"),
    PROGRAMMING("Programming"),
    MISCELLANEOUS("Miscellaneous"),
    DARK("Dark"),
    PUN("Pun"),
    SPOOKY("Spooky"),
    CHRISTMAS("Christma");

    @JsonValue
    private final String name;

    JokeApiCategory(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
