package com.ace.core.service.user;

import java.util.List;

import com.ace.core.bean.user.SysRole;
import com.ace.core.bean.user.SysUser;

public interface UserRoleService {
	
	//查询出所有用户的信息
	public List<SysUser> getSysUsers() throws Exception;
	
	//根据用户Id查询出用户的所有信息
	public SysUser getSysUserById(String id) throws Exception;
	
	//更Id修改用户状态
	public int updateLocked(SysUser sysUser)  throws Exception;
	
	//查询角色列表
	public List<SysRole> getRoles() throws Exception;
	
	//添加角色信息
	public int addUser(SysUser sysUser,String rid) throws Exception;
	
	//查询用户嘻嘻你
	public SysUser getSysUserByUserCode(String usercode) throws Exception;
	
	//原始密码比对
	public boolean isSuerPwd(String pwd,String usercoerd) throws Exception;
	
	//修改密码
	public int updatePwd(SysUser sysUser) throws Exception;

	//根据角色Id获取角色信息
	public SysRole getRoleById(String roleId) throws Exception;
	
	//重置用户密码
	public int resetPwd(String id) throws Exception;
	
	//修改用户信息
	public int updateSysUser(SysUser sysUser, String rid);
	
	//删除用户嘻嘻你
	public int deleteSysUser(String id) throws Exception;
	


}
