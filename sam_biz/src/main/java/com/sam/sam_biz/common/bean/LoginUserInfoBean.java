package com.sam.sam_biz.common.bean;

import lombok.Data;

import java.util.List;

/**
 * 登陆用户基本信息及权限信息
 *
 */
@Data
public class LoginUserInfoBean {

	// 登陆用户ID
	private Integer accountId;
	
	// 登陆用户名
	private String loginName;
	
	private Integer clientId;
	
	private Integer clientDbsn;
	
	// 登陆用户权限列表
	private List<Integer> functionIdList;
	private String sapId;
	private String userType;
	private String email;
}
