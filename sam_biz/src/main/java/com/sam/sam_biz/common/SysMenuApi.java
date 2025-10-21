package com.sam.sam_biz.common;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sam.sam_biz.common.bean.SysMenu;
import com.sam.sam_biz.common.bean.SysMenuBean;
import com.sam.sam_biz.common.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 菜单接口 *
 *
 * @author kunpeng.dang
 * @since 2021/12/24 15:21 * @version 1.0
 */
@Slf4j
@Service
public class SysMenuApi {
    private static final String ROOT = "9999";
//    @Resource
    private SysMenuService sysMenuService;

//    @Cacheable(value = Constants.CACHE_NAME, key = "'userMenu-' + #userId", unless = "#result==null")
    public List<SysMenuBean> getAllMenus(Integer userId) {
        if (null == userId || userId == -1) {
            // 用户未登录
            return Lists.newArrayList();
        }

        // 获取当前用户的权限/功能列表
        List<Integer> functionList = UserUtil.getUser().getFunctionIdList();
        if (CollectionUtils.isEmpty(functionList)) {
            // 用户无权限
            return Lists.newArrayList();
        }
        Map<String, String> funMap = Maps.newHashMap();
        for (Integer auth : functionList) {
            funMap.put(String.valueOf(auth), String.valueOf(auth));
        }

        List<SysMenuBean> result = Lists.newArrayList();
        // 获取所有菜单
        List<SysMenu> menuAll = this.sysMenuService.getMenus();
        // 分组
        Map<String, List<SysMenu>> menuMap = menuAll.stream().collect(Collectors.groupingBy(SysMenu::getParentId));
        // 获取第一层菜单
        List<SysMenu> menuHeaders = menuMap.get(ROOT);
        for (SysMenu header : menuHeaders) {
            // 获取次级菜单
            List<SysMenu> menus = menuMap.getOrDefault(header.getMenuId(),Lists.newArrayList());
            // 过滤:当前用户有权限的功能
            menus = menus.stream().filter(item -> funMap.containsKey(item.getMenuAuth())).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(menus)) {
                continue;
            }
            result.add(SysMenuBean.builder().menuHeader(header).menuList(menus).build());
        }
        return result;
    }
}
