package com.leeknow.summapp.dto;

import lombok.Data;

@Data
public class ApplicationResponseDTO {
    private Integer applicationId;
    private String number;
    private String creationDate;
    private String finishDate;
    private String status;
    private String type;
    private UserResponseDTO userResponseDTO;
}
