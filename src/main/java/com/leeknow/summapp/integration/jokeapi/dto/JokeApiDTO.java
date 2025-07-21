package com.leeknow.summapp.integration.jokeapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JokeApiDTO {

    private Boolean error;
    private Boolean internalError;
    private Integer code;
    private String message;
    private List<String> causedBy;
    private String additionalInfo;
    private Timestamp timestamp;
    private String category;
    private String type;
    private String joke;
    private String setup;
    private String delivery;
    private JokeApiFlagsDTO flags;
    private Integer id;
    private Boolean safe;
    private String lang;
}
