package com.ace.core.service.user;

import java.util.List;

import com.ace.core.bean.user.SysPermission;
import com.ace.core.bean.user.SysRole;

public interface RolePermissionService {

	//遍历所有权限菜单
	public List<SysPermission> getPermissionsMenu() throws Exception;
	
	//遍历所有权限信息
	public List<SysPermission> getPermissionPermission() throws Exception;
	
	//添加用户角色并分配权限
	public int addRoleGivePermission(SysRole sysRole,Long[] ids) throws Exception;
	
	//添加权限节点
	public int addPermisson(SysPermission sysPermission) throws Exception;
	
	//根据角色获取角色拥有的权限菜单
	public List<SysPermission> getRolemenus(String roleId) throws Exception;
	
	//根据角色获取角色拥有的权限菜单
	public List<SysPermission> getRolePermission(String roleId) throws Exception;
	
	//修改用户信息和权限信息
	public int updateRoleUpdatePermission(SysRole sysRole,Long[] ids) throws Exception;
 }
