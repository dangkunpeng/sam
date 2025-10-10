package com.sam.sap_commons.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Locale;

/**
 * 国际化文言取得的工具类
 *
 */
@Component
public class MsgUtils {

    private static MessageSource messageSource;

    /**
     * 取得指定Key对应的文言
     *
     * @param key
     * @return
     */
    public static String getMsg(String key) {
        Locale locale = LocaleContextHolder.getLocale();
        return getMsg(key, locale);
    }

    /**
     * 取得指定Key对应的文言
     *
     * @param key
     * @param request
     * @return
     */
    public static String getMsg(String key, HttpServletRequest request) {
        Locale locale = RequestContextUtils.getLocale(request);
        return getMsg(key, locale);
    }

    /**
     * 取得指定Key对应的,参数替换后的文言(支持多参数替换)
     *
     * @param key
     * @param arg
     * @return
     */
    public static String getMsg(String key, String... arg) {
        Locale locale = LocaleContextHolder.getLocale();
        return getMsg(key, locale, arg);
    }

    /**
     * 取得指定Key对应的,参数替换后的文言(支持多参数替换)
     *
     * @param key
     * @param arg
     * @return
     */
    public static String getMsg(String key, Object... arg) {
        Locale locale = LocaleContextHolder.getLocale();
        return getMsg(key, locale, arg);
    }

    /**
     * 取得指定Key对应的,参数替换后的文言(支持多参数替换)
     *
     * @param key
     * @param request
     * @param arg
     * @return
     */
    public static String getMsg(String key, HttpServletRequest request, String... arg) {
        Locale locale = RequestContextUtils.getLocale(request);
        return getMsg(key, locale, arg);
    }

    private static String getMsg(String key, Locale locale) {
        return messageSource.getMessage(key, null, locale);
    }

    private static String getMsg(String key, Locale locale, String... arg) {
        Object[] args = new Object[arg.length];
        for (int i = 0; i < arg.length; i++) {
            args[i] = arg[i];
        }
        return messageSource.getMessage(key, args, locale);
    }

    private static String getMsg(String key, Locale locale, Object... arg) {
        return messageSource.getMessage(key, arg, locale);
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        MsgUtils.messageSource = messageSource;
    }

}
