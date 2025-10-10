package com.sam.sap_commons.exchange;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiMq {
    private String mqType;
    private String mqBody;
    private String bizKey;
    private String timestamp;
    private String traceId;
}
