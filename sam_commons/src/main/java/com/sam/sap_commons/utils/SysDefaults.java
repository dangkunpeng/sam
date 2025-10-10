package com.sam.sap_commons.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SysDefaults {
    public static final String CHAR_SEPARATOR = ";";
    public static final String MQ_MAIN = "mqMain";
    public static final String MQ_MAIL = "mqMail";
    public static final String SYS_DEFAULT_TIME_PATTERN = "yyyyMMddHHmmssSSS";

    public static String now() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(SysDefaults.SYS_DEFAULT_TIME_PATTERN));
    }
}
