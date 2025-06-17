package com.leeknow.summapp.application.dto;

import com.leeknow.summapp.user.dto.UserResponseDTO;
import lombok.Data;

import java.io.Serializable;

@Data
public class ApplicationResponseDTO implements Serializable {
    private Integer applicationId;
    private String number;
    private String creationDate;
    private String finishDate;
    private String status;
    private String type;
    private UserResponseDTO userResponseDTO;
}
