package com.sam.sam_biz.common;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sam.sam_biz.common.bean.DictBean;
import com.sam.sam_biz.common.service.DictService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class DictApi {

    @Resource
    DictService dictService;

    /**
     * 获取字典 MstData
     * 未删除的数据
     *
     * @param typeKey
     * @return
     */
    public List<DictBean> getMstDict(String typeKey) {
        return getMstDict(typeKey, "0");
    }

    /**
     * 获取字典 MstData
     * 未删除的数据
     *
     * @param itemType
     * @param itemName
     * @return
     */
    public String getMstDictValue(String itemType, String itemName) {
        List<DictBean> list = getMstDict(itemType);
        if (CollectionUtils.isEmpty(list)) {
            return "";
        }
        for (DictBean dictBean : list) {
            if (StringUtils.equalsIgnoreCase(dictBean.getItemName(), itemName)) {
                return dictBean.getItemValue();
            }
        }
        return "";
    }


    private List<DictBean> getMstDict(String typeKey, String deleteFlag) {
        // 获取当前语言
        String language = getLanguage();
        // 使用当前语言查询
        List<DictBean> list = this.dictService.getMstDict(typeKey, language, deleteFlag);
        if (CollectionUtils.isEmpty(list)) {
            // 查不到就查询全部
            list = this.dictService.getMstDict(typeKey, "", deleteFlag);
        }
        // 创建一个新对象,给外部使用,避免缓存里的字典被修改,影响其他代码使用
        return getNewList(list);
    }

    public Map<String, String> getDictMap(String typeKey) {
        return toMap(this.getMstDict(typeKey));
    }


    /**
     * 字典转map
     * key:itemValue
     * value:itemName
     *
     * @param list
     * @return
     */
    public static Map<String, String> toMap(List<DictBean> list) {
        Map<String, String> map = Maps.newHashMap();
        if (CollectionUtils.isEmpty(list)) {
            return map;
        }
        for (DictBean item : list) {
            map.put(item.getItemValue(), item.getItemName());
        }
        return map;
    }

    /**
     * 字典转map KV反转
     * key:itemName
     * value:itemValue
     *
     * @param list
     * @return
     */
    public static Map<String, String> toMapReversed(List<DictBean> list) {
        Map<String, String> map = Maps.newHashMap();
        if (CollectionUtils.isEmpty(list)) {
            return map;
        }
        for (DictBean item : list) {
            map.put(item.getItemName(), item.getItemValue());
        }
        return map;
    }


    /**
     * 描述： 获取系统语言
     *
     * @return
     * @auth yang.m.zhang
     */
    private static String getLanguage() {
        String language = LocaleContextHolder.getLocale().getLanguage().toUpperCase();
        if (StringUtils.isBlank(language)) {
            return "EN";
        }
        return language;
    }

    /**
     * 创建一个新对象,给外部使用,避免缓存里的字典被修改,影响其他代码使用
     *
     * @param list
     * @return
     */
    public static List<DictBean> getNewList(List<DictBean> list) {
        List<DictBean> extList = Lists.newArrayList();
        for (DictBean bean : list) {
            extList.add(DictBean.builder()
                    .itemName(bean.getItemName())
                    .itemValue(bean.getItemValue())
                    .itemType(bean.getItemType())
                    .build());
        }
        return extList;
    }


    /**
     * 合并两个字典表
     *
     * @param list01
     * @param list02
     * @return
     */
    public static List<DictBean> unionDict(List<DictBean> list01, List<DictBean> list02) {
        List<DictBean> list = Lists.newArrayList();
        Map<String, String> map = toMap(list01);
        list.addAll(list01);
        for (DictBean bean : list02) {
            if (map.containsKey(bean.getItemValue())) {
                continue;
            }
            list.add(bean);
        }
        return list;
    }

}
