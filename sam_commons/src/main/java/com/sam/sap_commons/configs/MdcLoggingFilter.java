package com.sam.sap_commons.configs;

import com.sam.sap_commons.utils.AjaxUtils;
import com.sam.sap_commons.utils.ExceptionUtils;
import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class MdcLoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        AjaxUtils.putTraceId();
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.error("apiId set error {}", ExceptionUtils.getExceptionStackTrace(e));
        } finally {
            // 清理MDC信息以避免内存泄漏或错误传递
            AjaxUtils.removeTraceId();
        }
    }
}
