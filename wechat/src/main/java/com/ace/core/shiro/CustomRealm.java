package com.ace.core.shiro;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.ace.core.bean.user.ActiveUser;
import com.ace.core.bean.user.SysPermission;
import com.ace.core.bean.user.SysRole;
import com.ace.core.bean.user.SysUser;
import com.ace.core.service.user.SysService;

public class CustomRealm extends AuthorizingRealm {

	@Autowired
	private SysService sysService;

	// 用于认证
	@Override
	public void setName(String name) {
		super.setName("customRealm");
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 取出身份信息
		String userCode = (String) token.getPrincipal();
		// 根据身份信息去数据库查询
		SysUser sysUser = null;
		try {
			sysUser = sysService.getSysUserByUserCode(userCode);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// 查询不到返回null;
		if (sysUser == null) {
			return null;
		}
		if(sysUser.getLocked().equals("1")){
			throw new LockedAccountException();
		}
		// 查询到了返回认证信息
		String password = sysUser.getPassword();
		// 加盐
		String salt = sysUser.getSalt();
		// 根据用户ID出去用户的菜单
		List<SysPermission> menus = null;
		// 根据用户ID获取用户权限
		List<SysPermission> permissions = null;
		// 根据ID获取角色信息
		SysRole sysRole = null;
		try {
			menus = sysService.getMenuListByUserId(sysUser.getId());
			permissions = sysService.getPermissionListByUserId(sysUser.getId());
			sysRole = sysService.getRoleByUserId(sysUser.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 将用户信息放到activeUser
		ActiveUser activeUser = new ActiveUser();
		activeUser.setUserid(sysUser.getId());
		activeUser.setUsercode(sysUser.getUsercode());
		activeUser.setUsername(sysUser.getUsername());
		activeUser.setMenus(menus);
		activeUser.setPermissions(permissions);
		activeUser.setSysRole(sysRole);
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(activeUser, password, ByteSource.Util.bytes(salt), this.getName());

		return simpleAuthenticationInfo;

	}

	// 用于授权

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		// 从 principals获取主身份信息
		// 将getPrimaryPrincipal方法返回值转为真实身份类型（在上边的doGetAuthenticationInfo认证通过填充到SimpleAuthenticationInfo中身份类型），
		ActiveUser activeUser = (ActiveUser) principals.getPrimaryPrincipal();

		// 根据身份信息获取权限信息
		// 从数据库获取到权限数据
		List<SysPermission> permissionList = null;
		try {
			permissionList = sysService.getPermissionListByUserId(activeUser.getUserid());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 单独定一个集合对象
		List<String> permissions = new ArrayList<String>();
		if (permissionList != null) {
			for (SysPermission sysPermission : permissionList) {
				// 将数据库中的权限标签 符放入集合
				permissions.add(sysPermission.getPercode());
			}
		}

		/*
		 * List<String> permissions = new ArrayList<String>();
		 * permissions.add("user:create");//用户的创建
		 * permissions.add("item:query");//商品查询权限
		 * permissions.add("item:add");//商品添加权限
		 * permissions.add("item:edit");//商品修改权限
		 */// ....

		// 查到权限数据，返回授权信息(要包括 上边的permissions)
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		// 将上边查询到授权信息填充到simpleAuthorizationInfo对象中
		simpleAuthorizationInfo.addStringPermissions(permissions);

		return simpleAuthorizationInfo;
	}

	// 清除缓存
	public void clearCached() {
		PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
		super.clearCache(principals);
	}
}
