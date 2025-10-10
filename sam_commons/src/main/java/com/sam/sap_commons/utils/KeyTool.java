package com.sam.sap_commons.utils;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;


@Slf4j
public class KeyTool {

    // 计数器
    private static final Map<String, Integer> KEY_MAP = Maps.newConcurrentMap();
    // 补位字符
    private static final String PAD_CHAR = "0";


    // 计数器补位长度
    private static final Integer COUNT_LENGTH = 4;

    /**
     * 生成主键 <hr>
     * 格式为 feature / yyyyMMddHHmmss + 0001
     *
     * @return String
     */
    public static synchronized String newKey(String id) {

        StringBuilder result = new StringBuilder();
        // 时间戳
        result.append(id);
        result.append(SysDefaults.now());
        // 获取时间戳的使用次数
        Integer counter = KEY_MAP.getOrDefault(result.toString(), 0);
        if (counter == 0) {
            // 重置MAP,避免溢出
            KEY_MAP.clear();
        }
        counter++;
        // 使用次数添加到map
        KEY_MAP.put(result.toString(), counter);
        // 拼接上序号
        result.append(StringUtils.leftPad(String.valueOf(counter), COUNT_LENGTH, PAD_CHAR));
        return result.toString();
    }
}
