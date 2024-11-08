package com.leeknow.summapp.common.enums;

import lombok.Getter;

import java.util.Objects;

@Getter
public enum Language {

    RUSSIAN(1),
    KAZAKH(2)
    ;


    Language(Integer id) {
        this.id = id;
    }

    private final Integer id;

    public static String getLanguageString(String nameRu, String nameKz, Language language) {
        return Objects.equals(language.getId(), RUSSIAN.getId()) ? nameRu : nameKz;
    }

    public static Language getLanguageById(Integer id) {
        for (Language value : Language.values()) {
            if (value.getId().equals(id))
                return value;
        }
        throw new IllegalArgumentException();
    }

    public static Language getLanguageById(String id) {
        return getLanguageById(Integer.parseInt(id));
    }
}
