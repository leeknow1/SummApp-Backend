package com.leeknow.summapp.enums;

import lombok.Getter;

@Getter
public enum ApplicationStatus {

    CREATED(1, "Создана", "Құрылды"),
    IN_PROGRESS(2, "В работе", "Орындалуда"),
    APPROVED(3, "Принята", "Қабылданды"),
    REJECTED(4, "Отклонена", "Қабылданбады"),
    ;

    private final Integer id;
    private final String nameRu;
    private final String nameKz;

    ApplicationStatus(Integer id, String nameRu, String nameKz) {
        this.id = id;
        this.nameRu = nameRu;
        this.nameKz = nameKz;
    }

    public static String getNameById(int id, Language language) {
        for (ApplicationStatus value : ApplicationStatus.values()) {
            if (value.getId() == id) {
                return Language.getLanguageString(value.getNameRu(), value.getNameKz(), language);
            }
        }
        return "";
    }
}
