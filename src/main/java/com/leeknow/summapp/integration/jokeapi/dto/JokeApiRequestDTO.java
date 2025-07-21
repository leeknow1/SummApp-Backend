package com.leeknow.summapp.integration.jokeapi.dto;

import com.leeknow.summapp.integration.jokeapi.enums.*;
import lombok.Data;

import java.util.List;

@Data
public class JokeApiRequestDTO {

    private List<JokeApiCategory> category;
    private JokeApiLang lang;
    private List<JokeApiBlacklist> blacklist;
    private JokeApiResponseFormat responseFormat;
    private JokeApiType type;
    private String search;
    private Integer amount;
}
