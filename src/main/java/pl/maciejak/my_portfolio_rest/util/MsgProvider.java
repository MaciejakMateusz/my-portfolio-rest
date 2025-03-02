package pl.maciejak.my_portfolio_rest.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MsgProvider {

    private final MessageSource messageSource;

    @Autowired
    public MsgProvider(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getLocalizedMsg(String code, Object[] args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, args, locale);
    }
}
