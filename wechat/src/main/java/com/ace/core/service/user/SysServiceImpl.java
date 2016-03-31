package com.ace.core.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ace.core.bean.user.ActiveUser;
import com.ace.core.bean.user.SysPermission;
import com.ace.core.bean.user.SysRole;
import com.ace.core.bean.user.SysUser;
import com.ace.core.bean.user.SysUserExample;
import com.ace.core.dao.user.SysPermissionMapperCustom;
import com.ace.core.dao.user.SysRoleMapperCustom;
import com.ace.core.dao.user.SysUserMapper;
import com.ace.core.dao.user.SysUserMapperCustom;
import com.ace.core.exception.CustomException;
import com.ace.core.util.MD5;

@Service
@Transactional
public class SysServiceImpl implements SysService {
	
	
	@Autowired
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysPermissionMapperCustom sysPermissionMapperCustom;
	@Autowired
	private SysRoleMapperCustom sysRoleMapperCustom;
	@Autowired
	private SysUserMapperCustom sysUserMapperCustom;
	
	public ActiveUser authorization(String usercode, String password)
			throws Exception {
		//根据用户帐号查询数据库
		SysUser sysUser=this.getSysUserByUserCode(usercode);
		if(sysUser==null){
			throw new CustomException("用户账户不存在");
		}
		//数据库加密后的密码
		String password_db=sysUser.getPassword();
		//用户输入的密码加密后和数据库的比对;
		String password_input=new MD5().getMD5ofStr(password);
		if(!password_input.equalsIgnoreCase(password_db)){
			throw new CustomException("用户名或密码不正确");
		}
		//得到用户ID
		String userid=sysUser.getId();
		//根据用户id获取菜单列表12
		List<SysPermission> menuList=this.getMenuListByUserId(userid);
		//根据用户ID获取权限列表
		List<SysPermission> permissionList=this.getPermissionListByUserId(userid);
		//根据ID获取角色信息
		SysRole sysRole=this.getRoleByUserId(userid);
		//认证通过
		ActiveUser activeUser=new ActiveUser();
		activeUser.setUserid(sysUser.getId());
		activeUser.setUsercode(sysUser.getUsercode());
		activeUser.setUsername(sysUser.getUsername());
		
		// 放入权限范围的url和菜单
		activeUser.setMenus(menuList);
		activeUser.setPermissions(permissionList);
		activeUser.setSysRole(sysRole);
		return activeUser;
	}
	
	@Transactional(readOnly=true)
	public SysUser getSysUserByUserCode(String userCode) throws Exception{
		
		SysUserExample sysUserExample=new SysUserExample();
		SysUserExample.Criteria criteria=sysUserExample.createCriteria();
		criteria.andUsercodeEqualTo(userCode);
		
		List<SysUser> list=sysUserMapper.selectByExample(sysUserExample);
		if(list!=null && list.size()==1){
			return list.get(0);
		}
		return null;
	}

	public List<SysPermission> getMenuListByUserId(String userid)
			throws Exception {
		// TODO Auto-generated method stub
		return sysPermissionMapperCustom.getMenuListByUserId(userid);
	}

	public List<SysPermission> getPermissionListByUserId(String userid)
			throws Exception {
		// TODO Auto-generated method stub
		return sysPermissionMapperCustom.getPermissionListByUserId(userid);
	}

	@Override
	public SysRole getRoleByUserId(String userid) throws Exception {
		return sysRoleMapperCustom.getRoleByUserId(userid);
	}






}
