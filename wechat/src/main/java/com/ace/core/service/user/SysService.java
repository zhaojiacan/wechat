package com.ace.core.service.user;

import java.util.List;

import com.ace.core.bean.user.ActiveUser;
import com.ace.core.bean.user.SysPermission;
import com.ace.core.bean.user.SysRole;
import com.ace.core.bean.user.SysUser;

/**
 * 认证授权服务接口
 * @author Administrator
 *
 */
public interface SysService {

	public ActiveUser authorization(String userCode,String password) throws Exception;
	public SysUser getSysUserByUserCode(String userCode) throws Exception;
	
	//根据用户id查询权限范围的菜单
	public List<SysPermission> getMenuListByUserId(String userid) throws Exception;
	
	//根据用户id查询权限范围的url
	public List<SysPermission> getPermissionListByUserId(String userid) throws Exception;
	
	public SysRole  getRoleByUserId(String userid)  throws Exception;
	
}
