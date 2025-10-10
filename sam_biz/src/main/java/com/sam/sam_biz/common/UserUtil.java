package com.sam.sam_biz.common;

import com.sam.sam_biz.common.bean.LoginUserInfoBean;
import com.sam.sam_biz.utils.Constants;
import com.sam.sap_commons.utils.SessionManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserUtil {

	/**
	 * 获取登录用户的id
	 * @return
	 */
	public static Integer getUserId() {
		try {
			return getUser().getAccountId();
		} catch (Exception e) {
		}
		return -1;
	}
	/**
	 * 获取登录用户的 loginName
	 * @return
	 */
	public static String getUserName() {
		try {
			return getUser().getLoginName();
		} catch (Exception e) {
		}
		return "";
	}
	/**
	 * 获取登录用户信息
	 * @return
	 */
	public static LoginUserInfoBean getUser() {
		try {
			//当前登录用户
			return SessionManager.getValue(Constants.SESSION_LOGIN_USERINFO);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取sapId
	 * @return
	 */
	public static String getSapId() {
		try {
			//当前登录用户
			return getUser().getSapId();
		} catch (Exception e) {
			return "";
		}
	}
}
