package com.leeknow.summapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class SimpleDTO {

    @Length(max = 1000, message = "Слишком длинное сообщение.")
    @NotBlank(message = "Введите сообщение.")
    private String message;
}
