package com.sam.sam_biz.common.dao;


import com.sam.sam_biz.common.bean.SysMenu;

import java.util.List;

//@Mapper
public interface SysMenuMapper {

	/**
	 * 查询数据来自IAP
	 * dbo.MST_DictType
	 * @param param
	 * @return
	 */
	List<SysMenu> getMenus(SysMenu param);
}
