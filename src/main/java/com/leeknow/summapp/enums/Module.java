package com.leeknow.summapp.enums;

import lombok.Getter;

@Getter
public enum Module {

    APPLICATIONS(1, "Заявки");

    private final Integer id;
    private final String name;

    Module(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
