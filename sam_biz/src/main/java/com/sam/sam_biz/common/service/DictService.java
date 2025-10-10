package com.sam.sam_biz.common.service;

import com.google.common.collect.Lists;
import com.sam.sam_biz.common.bean.DictBean;
import com.sam.sam_biz.common.bean.DictQueryBean;
import com.sam.sam_parents.configs.CaffineConfig;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述： 字典表管理service
 * 不要直接使用当前类,请使用DictApi
 *
 * @author kunpeng.dang
 * @version 1.0
 * @since 20211201
 */
@Service
@Slf4j
public class DictService {


//    @Resource
//    DictMapper dictMapper;

    /**
     * 查询MstData里的字典,使用缓存加快速度
     *
     * @param typeKey
     * @param language
     * @return
     */
    @Cacheable(value = CaffineConfig.CACHE_NAME, key = "'mstDict-typeKey' + #typeKey + '-language' + #language+'-deleteFlag'+ #deleteFlag", unless = "#result==null")
    public List<DictBean> getMstDict(String typeKey, String language, String deleteFlag) {

        List<DictBean> result = Lists.newArrayList();
        try {
            // 根据默认条件查询
            DictQueryBean bean = DictQueryBean.builder().typeKey(typeKey).language(language).deleteFlag(deleteFlag).build();
//            result = this.dictMapper.getMstDict(bean);
        } catch (Exception e) {
            log.info("ERROR ={}", e);
        }
        log.debug("getMstDict {} records for typeKey ({}) ", result.size(), typeKey);
        return result;
    }


}
