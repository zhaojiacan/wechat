package com.ace.core.dao.user;

import java.util.List;

import com.ace.core.bean.user.SysUser;

public interface SysUserMapperCustom {
	//获取所有的用户信息
	public List<SysUser> getUserRoleInfoList();
	//根据用户Id获取用户信息
	public SysUser getUserRoleInfoById(String id);
}
