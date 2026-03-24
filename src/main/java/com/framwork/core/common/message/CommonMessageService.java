package com.framwork.core.common.message;

import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class CommonMessageService {

    private final MessageSource messageSource;

    public String getMessage(String code) {
        return getMessage(code, code);
    }

    public String getMessage(String code, String fallback) {
        if (!StringUtils.hasText(code)) {
            return fallback;
        }
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, null, fallback, locale);
    }
}
