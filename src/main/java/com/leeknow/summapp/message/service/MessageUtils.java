package com.leeknow.summapp.message.service;

import com.leeknow.summapp.common.enums.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MessageUtils {

    private final MessageSource messageSource;

    public String getMessage(Language language, String messageCode) {
        return getMessage(language, messageCode, (Object[]) null);
    }

    public String getMessage(Language language, String messageCode, Object... args) {
        return messageSource.getMessage(getCodeLanguage(messageCode, language), args, new Locale(language.getCode()));
    }

    private String getCodeLanguage(String code, Language language) {
        if (Language.RUSSIAN == language) {
            code += ".ru";
        } else {
            code += ".kz";
        }
        return code;
    }
}
