package com.sam.sam_biz.common.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 菜单对象 *
 *
 * @author kunpeng.dang
 * @since 2021/12/24 15:07 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysMenuBean {

    private SysMenu menuHeader;
    private List<SysMenu> menuList;
}
