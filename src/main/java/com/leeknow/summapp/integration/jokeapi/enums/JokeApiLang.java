package com.leeknow.summapp.integration.jokeapi.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum JokeApiLang {
    CZECH("cs"),
    GERMAN("de"),
    ENGLISH("en"),
    SPANISH("es"),
    FRENCH("fr"),
    PORTUGUESE("pt");

    @JsonValue
    private final String lang;

    JokeApiLang(String lang) {
        this.lang = lang;
    }

    @Override
    public String toString() {
        return lang;
    }
}
