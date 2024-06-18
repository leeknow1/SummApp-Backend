package com.leeknow.summapp.dto;

import lombok.Data;

@Data
public class ApplicationResponseDTO {
    private Integer applicationId;
    private String number;
    private String creationDate;
    private String finishDate;
    private Integer statusId;
    private Integer typeId;
    private UserResponseDTO userResponseDTO;
}
