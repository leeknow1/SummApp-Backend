package com.leeknow.summapp.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Date;

@Data
public class DataSearchDTO {
    private int page;
    private int size;
    private String sort;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date start;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date finish;
}
