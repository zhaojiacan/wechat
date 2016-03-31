package com.ace.core.dao.user;

import java.util.List;

import com.ace.core.bean.user.SysRole;

public interface SysRoleMapperCustom {
	//根据用户id获取角色信息
	public SysRole  getRoleByUserId(String userid);
	//关联查询所用角色及拥有该角色的用户信息
	public List<SysRole> getRoleUsersInfo();
}