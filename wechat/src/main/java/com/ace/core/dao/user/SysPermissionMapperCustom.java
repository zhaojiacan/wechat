package com.ace.core.dao.user;

import java.util.List;

import com.ace.core.bean.user.SysPermission;

/**
 * 自定义权限mapper
 * @author Administrator
 *
 */
public interface SysPermissionMapperCustom {
	
	//根据用户id查询菜单
	public List<SysPermission> getMenuListByUserId(String userid)throws Exception;
	//根据用户id查询权限url
	public List<SysPermission> getPermissionListByUserId(String userid)throws Exception;
	//根据角色Id查询角色权限
	public List<SysPermission> getPermissionListByRoleId(String roleId) throws Exception;
	//根据角色Id查询角色菜单
	public List<SysPermission> getMenuListByRoleId(String roleId) throws Exception;
}
