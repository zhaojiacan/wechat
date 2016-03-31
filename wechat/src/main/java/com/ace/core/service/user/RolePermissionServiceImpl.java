package com.ace.core.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ace.core.bean.user.SysPermission;
import com.ace.core.bean.user.SysPermissionExample;
import com.ace.core.bean.user.SysRole;
import com.ace.core.bean.user.SysRolePermission;
import com.ace.core.bean.user.SysUser;
import com.ace.core.dao.user.SysPermissionMapper;
import com.ace.core.dao.user.SysPermissionMapperCustom;
import com.ace.core.dao.user.SysRoleMapper;
import com.ace.core.dao.user.SysRolePermissionMapper;
import com.ace.core.dao.user.SysRolePermissionMapperCustom;
import com.ace.core.util.StringUtil;

@Service
@Transactional
public class RolePermissionServiceImpl implements RolePermissionService {
	@Autowired
	private SysPermissionMapper sysPermissionMapper;
	@Autowired
	private SysRoleMapper sysRoleMapper;
	@Autowired
	private SysRolePermissionMapper sysRolePermissionMapper;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private  SysPermissionMapperCustom sysPermissionMapperCustom;
	@Autowired
	private SysRolePermissionMapperCustom sysRolePermissionMapperCustom;
	
	@Transactional(readOnly=true)
	@Override
	public List<SysPermission> getPermissionsMenu() throws Exception {
		SysPermissionExample sysPermissionExample=new SysPermissionExample();
		sysPermissionExample.setOrderByClause("id");
		SysPermissionExample.Criteria criteria=sysPermissionExample.createCriteria();
		criteria.andTypeEqualTo("menu");
		List<SysPermission> selectByExample = sysPermissionMapper.selectByExample(sysPermissionExample);
		return selectByExample;
	}
	
	@Transactional(readOnly=true)
	@Override
	public List<SysPermission> getPermissionPermission() throws Exception {
		SysPermissionExample sysPermissionExample=new SysPermissionExample();
		SysPermissionExample.Criteria criteria=sysPermissionExample.createCriteria();
		criteria.andTypeEqualTo("permission");
		criteria.andAvailableEqualTo("1");
		List<SysPermission> selectByExample = sysPermissionMapper.selectByExample(sysPermissionExample);
		return selectByExample;
	}

	@Override
	public int addRoleGivePermission(SysRole sysRole,Long[] ids) throws Exception {
		//保存角色信息
		sysRole.setId(StringUtil.getUUID(32));
		sysRole.setAvailable("1");
		int count = sysRoleMapper.insert(sysRole);
		//角色权限分配
		if(count>0){
			SysRolePermission sysRolePermission=new SysRolePermission();
			sysRolePermission.setSysRoleId(sysRole.getId());
			for (Long pid : ids) {
				sysRolePermission.setId(StringUtil.getUUID());
				sysRolePermission.setSysPermissionId(pid+"");
				count = sysRolePermissionMapper.insert(sysRolePermission);
			}
		}
		//初始化一个默认用户
		if(count>0){
			SysUser sysUser=new SysUser();
			String userstring=StringUtil.getUUID(8);
			sysUser.setId(userstring);
			sysUser.setUsercode(userstring);
			sysUser.setUsername(sysRole.getName()+userstring);
			count = userRoleService.addUser(sysUser, sysRole.getId());
		}
		return count;
	}

	@Override
	public int addPermisson(SysPermission sysPermission) throws Exception {
		
		int insertSelective = sysPermissionMapper.insertSelective(sysPermission);
		return insertSelective;
	}

	@Transactional(readOnly=true)
	@Override
	public List<SysPermission> getRolemenus(String roleId) throws Exception {
		
		return sysPermissionMapperCustom.getMenuListByRoleId(roleId);
	}

	@Transactional(readOnly=true)
	@Override
	public List<SysPermission> getRolePermission(String roleId) throws Exception {
		return sysPermissionMapperCustom.getPermissionListByRoleId(roleId);
	}

	@Override
	public int updateRoleUpdatePermission(SysRole sysRole, Long[] ids)
			throws Exception {
		//修改用户信息
		int count = sysRoleMapper.updateByPrimaryKeySelective(sysRole);
		//更新权限信息
		if(count>0){
			//删除角色对应的所用权限重新分配
			sysRolePermissionMapperCustom.deleteByRoleId(sysRole.getId());
			//插入新的权限信息
			SysRolePermission sysRolePermission=new SysRolePermission();
			sysRolePermission.setSysRoleId(sysRole.getId());
			for (Long pid : ids) {
				sysRolePermission.setId(StringUtil.getUUID());
				sysRolePermission.setSysPermissionId(pid+"");
				count = sysRolePermissionMapper.insert(sysRolePermission);
			}
		}
		
		return count;
	}
}
