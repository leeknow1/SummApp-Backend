package com.leeknow.summapp.log.enums;

import lombok.Getter;

@Getter
public enum LogType {
    NORMAL(1),
    CRITICAL(2),
    SYSTEM(3)
    ;

    private final int id;

    LogType(int id) {
        this.id = id;
    }
}
