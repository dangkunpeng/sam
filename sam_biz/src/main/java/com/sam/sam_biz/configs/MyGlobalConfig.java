package com.sam.sam_biz.configs;

import com.sam.sam_biz.common.DictApi;
import com.sam.sam_biz.common.SysMenuApi;
import com.sam.sam_biz.common.UserUtil;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;


/**
 * 全局变量 *
 *
 * @author kunpeng.dang
 * @since 2021/12/24 13:52 * @version 1.0
 */
@ControllerAdvice
public class MyGlobalConfig {
    @Value("${app.version}")
    private String appVersion;
    @Value("${spring.application.name}")
    private String appName;
    @Value("${spring.profiles.active}")
    private String env;
    @Resource
    private DictApi dictApi;
    @Resource
    private SysMenuApi sysMenuApi;

    @ModelAttribute
    public void init(Model model) {
        if (StringUtils.isBlank(UserUtil.getUserName())) {
            return;
        }
        model.addAttribute("env", env);
        // 应用名
        model.addAttribute("appName", appName);
        // 版本信息
        model.addAttribute("appVersion", appVersion);
        model.addAttribute("currentUser", UserUtil.getUser());
        // 获取菜单
        model.addAttribute("sysMenus", this.sysMenuApi.getAllMenus(UserUtil.getUserId()));
        // 分页信息
//        model.addAttribute("pageList", this.dictApi.getMstDictValue(PAGE_LIST.name(), appName));
    }
}
