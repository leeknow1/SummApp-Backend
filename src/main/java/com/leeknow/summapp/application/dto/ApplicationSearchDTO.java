package com.leeknow.summapp.application.dto;

import com.leeknow.summapp.common.dto.DataSearchDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ApplicationSearchDTO extends DataSearchDTO {

    private String number;
    private Integer statusId;
    private Integer typeId;
    private Integer userId;
}
