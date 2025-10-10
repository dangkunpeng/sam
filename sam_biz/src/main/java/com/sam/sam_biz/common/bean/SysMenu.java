package com.sam.sam_biz.common.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 菜单 *
 *
 * @author kunpeng.dang
 * @since 2021/12/24 15:04 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysMenu {
    private String menuId;
    private String parentId;
    private String menuName;
    private String menuDesc;
    private String menuAuth;
    private String menuUrl;
    private String menuCss;
    private String appName;
}
