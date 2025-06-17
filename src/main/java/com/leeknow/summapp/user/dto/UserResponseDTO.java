package com.leeknow.summapp.user.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserResponseDTO implements Serializable {

    private String firstName;
    private String lastName;
    private String middleName;
}
