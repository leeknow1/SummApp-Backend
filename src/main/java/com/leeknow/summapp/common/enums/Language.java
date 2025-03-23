package com.leeknow.summapp.common.enums;

import lombok.Getter;

import java.util.Objects;

@Getter
public enum Language {

    RUSSIAN("ru"),
    KAZAKH("kk");


    private final String code;

    Language(String code) {
        this.code = code;
    }

    public static String getLanguageString(String nameRu, String nameKz, Language language) {
        return Objects.equals(language.getCode(), RUSSIAN.getCode()) ? nameRu : nameKz;
    }

    public static Language getLanguageByCode(String code) {
        for (Language value : Language.values()) {
            if (value.getCode().equals(code))
                return value;
        }
        throw new IllegalArgumentException();
    }
}
