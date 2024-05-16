package com.leeknow.summapp.dto;

import lombok.Data;

@Data
public class DataSearchDTO {
    private int page;
    private int size;
    private String sort;
}
