package com.sam.sap_commons.exchange;

import lombok.Builder;
import lombok.Data;

/**
 * 通用返回结果
 *
 * @param <T>
 */
@Data
@Builder
public class ApiMsg<T> {

    private Integer code;
    private String msg;
    private T data;

    public ApiMsg(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ApiMsg<T> success(String msg, T data) {
        return new ApiMsg(ApiEnum.API_SUCCESS.code, msg, data);
    }

    public static <T> ApiMsg<T> success(T data) {
        return success(ApiEnum.API_SUCCESS.msg, data);
    }


    public static <T> ApiMsg<T> fail(String msg, T data) {
        return new ApiMsg(ApiEnum.API_FAIL.code, msg, data);
    }

    public static <T> ApiMsg<T> fail(T data) {
        return fail(ApiEnum.API_FAIL.msg, data);
    }

    public static <T> ApiMsg<T> error(String msg, T data) {
        return new ApiMsg(ApiEnum.API_ERROR.code, msg, data);
    }

    public static <T> ApiMsg<T> error(T data) {
        return error(ApiEnum.API_ERROR.msg, data);
    }

    public static <T> ApiMsg<T> others(String msg, T data) {
        return new ApiMsg(ApiEnum.API_OTHERS.code, msg, data);
    }

    public static <T> ApiMsg<T> others(T data) {
        return others(ApiEnum.API_OTHERS.msg, data);
    }

    public Boolean isSuccess() {
        return this.code.intValue() == ApiEnum.API_SUCCESS.code.intValue();
    }

    public Boolean isFailed() {
        return this.code.intValue() == ApiEnum.API_FAIL.code.intValue();
    }
}
