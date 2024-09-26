package com.leeknow.summapp.enums;

import lombok.Getter;

@Getter
public enum ModuleRight {
    READ(1),
    WRITE(2)
    ;

    private final int id;

    ModuleRight(int id) {
        this.id = id;
    }
}
