package com.leeknow.summapp.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationKafkaDTO {

    private Integer applicationId;
    private Integer statusId;
    private String updatedDate;
}
