package com.sam.sap_commons.exchange;

import com.sam.sap_commons.utils.AjaxUtils;
import org.apache.commons.lang3.StringUtils;


public class ApiBase {
    private String traceId;

    public String getTraceId() {
        return StringUtils.isBlank(this.traceId) ? AjaxUtils.getTraceId() : this.traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
}
