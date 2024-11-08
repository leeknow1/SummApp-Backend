package com.leeknow.summapp.application.enums;

import com.leeknow.summapp.common.enums.Language;
import lombok.Getter;

@Getter
public enum ApplicationType {

    SHORT(1, "Короткий", "Қысқа"),
    LONG(2, "Длинный", "Ұзақ");

    private final int id;
    private final String nameRu;
    private final String nameKz;

    ApplicationType(int id, String nameRu, String nameKz) {
        this.id = id;
        this.nameRu = nameRu;
        this.nameKz = nameKz;
    }

    public static String getNameById(Integer id, Language language) {
        for (ApplicationType value : ApplicationType.values()) {
            if (value.getId() == id) {
                return Language.getLanguageString(value.getNameRu(), value.getNameKz(), language);
            }
        }
        return "";
    }
}
