package com.sam.sap_commons.exchange;

public enum ApiEnum {
    API_SUCCESS(200, "success"),
    API_FAIL(400, "failed"),
    API_ERROR(500, "error"),
    API_OTHERS(9999, "others");

    Integer code;
    String msg;

    ApiEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
