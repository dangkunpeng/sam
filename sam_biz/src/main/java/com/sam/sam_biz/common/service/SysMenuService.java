package com.sam.sam_biz.common.service;

import com.sam.sam_biz.common.bean.SysMenu;
import com.sam.sam_biz.common.dao.SysMenuMapper;
import com.sam.sam_biz.utils.Constants;
import com.sam.sam_parents.configs.CaffineConfig;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单服务 *
 *
 * @author kunpeng.dang
 * @since 2021/12/24 15:16 * @version 1.0
 */
@Slf4j
@Service
public class SysMenuService {
//    @Resource
    SysMenuMapper sysMenuMapper;

    @Value("${spring.application.name}")
    private String appName;

    @Cacheable(value = CaffineConfig.CACHE_NAME, key = "'dbMenu-' + #parentId", unless = "#result==null")
    public List<SysMenu> getMenus() {
        return this.sysMenuMapper.getMenus(SysMenu.builder().appName(appName).build());
    }

}
