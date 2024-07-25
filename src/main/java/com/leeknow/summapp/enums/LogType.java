package com.leeknow.summapp.enums;

import lombok.Getter;

@Getter
public enum LogType {
    NORMAL(1),
    CRITICAL(2);

    private final int id;

    LogType(int id) {
        this.id = id;
    }
}
