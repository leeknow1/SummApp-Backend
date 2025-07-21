package com.leeknow.summapp.integration.jokeapi.dto;

import lombok.Data;

@Data
public class JokeApiFlagsDTO {
    private Boolean nsfw;
    private Boolean religious;
    private Boolean political;
    private Boolean racist;
    private Boolean sexist;
    private Boolean explicit;
}
