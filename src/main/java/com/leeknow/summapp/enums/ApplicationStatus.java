package com.leeknow.summapp.enums;

import lombok.Getter;

@Getter
public enum ApplicationStatus {

    CREATED(1, "Создана"),
    IN_PROGRESS(2, "В работе"),
    APPROVED(3, "Принята"),
    REJECTED(4, "Отклонена"),
    ;

    private final Integer id;
    private final String name;

    ApplicationStatus(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public static String getNameById(int id) {
        for (ApplicationStatus value : ApplicationStatus.values()) {
            if (value.getId() == id) {
                return value.getName();
            }
        }
        return "";
    }
}
