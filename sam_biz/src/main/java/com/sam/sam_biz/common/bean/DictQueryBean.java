package com.sam.sam_biz.common.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 字典查询 *
 *
 * @author kunpeng.dang
 * @since 2021/11/30 15:24 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DictQueryBean {
    private String typeKey;
    private String language;
    private String deleteFlag;
}
