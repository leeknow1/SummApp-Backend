package com.leeknow.summapp.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserLoginDTO {

    @Email(message = "Неверный формат.")
    @NotBlank(message = "Введите почту.")
    private String email;

    @NotBlank(message = "Введите пароль.")
    @Size(min = 8, message = "ВВедите как минимум 8 символов.")
    private String password;
}
