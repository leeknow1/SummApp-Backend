package com.leeknow.summapp.schedule.dto;

import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class ExchangeRateAPIScheduleDTO {

    private boolean success;
    private String base;
    private Date date;
    private Map<String, Double> rates;
}
