package com.leeknow.summapp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApplicationRequestDTO {

    @NotNull(message = "Выберите значение для типа заявки!")
    @Min(value = 1, message = "Выберите значение для типа заявки!")
    private Integer typeId;
}
