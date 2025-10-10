package com.sam.sam_parents.configs;

import com.sam.sap_commons.utils.ExceptionUtils;
import com.sam.sap_commons.utils.KeyTool;
import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.io.IOException;
@Slf4j
public class MdcLoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        MDC.put("apiId", KeyTool.newKey("apiId"));
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.error("apiId set error {}", ExceptionUtils.getExceptionStackTrace(e));
        } finally {
            MDC.remove("apiId"); // 清理MDC信息以避免内存泄漏或错误传递
        }
    }
}
