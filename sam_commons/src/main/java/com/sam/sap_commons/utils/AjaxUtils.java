package com.sam.sap_commons.utils;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.Objects;

@Log4j2
public class AjaxUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 验证是否是ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return Objects.equals("XMLHttpRequest", request.getHeader("X-Requested-With"));
    }

    public static boolean isAjaxUploadRequest(HttpServletRequest request) {
        return request.getParameter("ajaxUpload") != null;
    }

    public static void writeJson(Object value, HttpServletResponse response) {
        try {
            objectMapper.getFactory().createGenerator(response.getOutputStream(), JsonEncoding.UTF8).writeObject(value);
        } catch (IOException e) {
            log.error(ExceptionUtils.getExceptionStackTrace(e));
        }
    }
}
