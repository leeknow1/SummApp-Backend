package com.leeknow.summapp.integration.jokeapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JokeApiMultipleDTO {
    private Boolean error;
    private Integer amount;
    private List<JokeApiDTO> jokes;
}
