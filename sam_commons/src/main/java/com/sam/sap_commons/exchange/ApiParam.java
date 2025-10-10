package com.sam.sap_commons.exchange;

import lombok.Data;

@Data
public class ApiParam<T> {
    // 由系统统一分配，每个接口为固定值
    private String appId;
    // 验证token
    private String token;

    private String ApiId;

    private String traceId;

    private T data;
}
