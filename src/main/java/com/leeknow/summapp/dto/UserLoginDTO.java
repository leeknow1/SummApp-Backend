package com.leeknow.summapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginDTO {

    @NotBlank(message = "Введите почту.")
    private String email;

    @NotBlank(message = "Введите пароль.")
    private String password;
}
