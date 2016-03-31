package com.ace.core.service.user;

import java.util.List;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ace.core.bean.user.SysRole;
import com.ace.core.bean.user.SysUser;
import com.ace.core.bean.user.SysUserExample;
import com.ace.core.bean.user.SysUserRole;
import com.ace.core.bean.user.SysUserRoleExample;
import com.ace.core.dao.user.SysRoleMapper;
import com.ace.core.dao.user.SysRoleMapperCustom;
import com.ace.core.dao.user.SysUserMapper;
import com.ace.core.dao.user.SysUserMapperCustom;
import com.ace.core.dao.user.SysUserRoleMapper;
import com.ace.core.util.StringUtil;

@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService{

	@Autowired
	SysUserMapperCustom sysUserMapperCustom;
	@Autowired
	SysRoleMapperCustom sysRoleMapperCustom;
	@Autowired
	SysUserMapper sysUserMapper;
	@Autowired
	SysRoleMapper sysRoleMapper;
	@Autowired
	SysUserRoleMapper sysUserRoleMapper;
	
	@Transactional(readOnly=true)
	@Override
	public List<SysUser> getSysUsers() throws Exception {
		// TODO Auto-generated method stub
		return sysUserMapperCustom.getUserRoleInfoList();
	}

	@Override
	public int updateLocked(SysUser sysUser) {
		return sysUserMapper.updateByPrimaryKeySelective(sysUser);
	}

	@Transactional(readOnly=true)
	@Override
	public List<SysRole> getRoles() throws Exception {
		List<SysRole> lists=sysRoleMapperCustom.getRoleUsersInfo();
		return lists;
	}

	@Override
	public int addUser(SysUser sysUser,String rid) throws Exception {
		//插入用户基本信息
		if(sysUser.getPassword()==null){
			sysUser.setPassword("48e1a118a99e59fb09254e42a0335bc8");//初始化的用户默认密码都是：123456
			sysUser.setSalt("eteokues");//初始化的用户默认密码都是：123456
		}else{
			String salt=StringUtil.getStringRandom(8);
			Md5Hash md5Hash=new Md5Hash(sysUser.getPassword(), salt, 1);
			sysUser.setPassword(md5Hash.toString());
			sysUser.setSalt(salt);
			sysUser.setId(sysUser.getUsercode());
		}
		sysUser.setLocked("1");//初始化用户默认是锁定的需要管理员开启锁定
		int count = sysUserMapper.insertSelective(sysUser);
		
		if(count>0){
			//插入用户角色信息
			SysUserRole sysUserRole=new SysUserRole();
			sysUserRole.setId(StringUtil.getUUID());
			sysUserRole.setSysRoleId(rid);
			sysUserRole.setSysUserId(sysUser.getId());
			count= sysUserRoleMapper.insertSelective(sysUserRole);
		}
		
		return count;
		
		
		
	}

	@Transactional(readOnly=true)
	@Override
	public SysUser getSysUserByUserCode(String  usercode) throws Exception {
		SysUserExample sysUserExample=new SysUserExample();
		SysUserExample.Criteria criteria=sysUserExample.createCriteria();
		criteria.andUsercodeEqualTo(usercode);
		List<SysUser> selectByExample = sysUserMapper.selectByExample(sysUserExample);
		if(selectByExample!=null&&selectByExample.size()>0){
			return selectByExample.get(0);
		}
		return null;
	}

	@Transactional(readOnly=true)
	@Override
	public boolean isSuerPwd(String pwd,String usercoerd) throws Exception {
		//通过usercode获取用户信息
		SysUser sysUser = this.getSysUserByUserCode(usercoerd);
		Md5Hash md5Hash=new Md5Hash(pwd, sysUser.getSalt(), 1);
		if(sysUser.getPassword().equals(md5Hash.toString())){
			return true;
		}
		return false;
	}

	@Override
	public int updatePwd(SysUser sysUser) throws Exception {
		String salt=StringUtil.getStringRandom(8);
		Md5Hash md5Hash=new Md5Hash(sysUser.getPassword(), salt, 1);
		sysUser.setPassword(md5Hash.toString());
		sysUser.setSalt(salt);
		int count = sysUserMapper.updateByPrimaryKeySelective(sysUser);
		return count;
	}

	@Transactional(readOnly=true)
	@Override
	public SysRole getRoleById(String roleId) throws Exception {
		
		return sysRoleMapper.selectByPrimaryKey(roleId);
	}

	
	@Override
	public int resetPwd(String id) throws Exception {
		SysUser sysUser=new SysUser();
		sysUser.setId(id);
		sysUser.setPassword("48e1a118a99e59fb09254e42a0335bc8");//初始化的用户默认密码都是：123456
		sysUser.setSalt("eteokues");//初始化的用户默认密码都是：123456
		int count = sysUserMapper.updateByPrimaryKeySelective(sysUser);
		return count;
	}

	@Transactional(readOnly=true)
	@Override
	public SysUser getSysUserById(String id) throws Exception {
		return sysUserMapperCustom.getUserRoleInfoById(id);
	}

	@Override
	public int updateSysUser(SysUser sysUser, String rid) {
		//修改用户信息
		int count = sysUserMapper.updateByPrimaryKeySelective(sysUser);
		//更新用户角色信息
		SysUserRoleExample sysUserRoleExample=new SysUserRoleExample();
		SysUserRoleExample.Criteria criteria=sysUserRoleExample.createCriteria();
		criteria.andSysUserIdEqualTo(sysUser.getId());
		List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectByExample(sysUserRoleExample);
		SysUserRole sysUserRole = sysUserRoles.get(0);
		sysUserRole.setSysRoleId(rid);
		count = sysUserRoleMapper.updateByPrimaryKeySelective(sysUserRole);
		return count;
	}

	@Override
	public int deleteSysUser(String id) throws Exception {
		//删除用户基本信息
		int count = sysUserMapper.deleteByPrimaryKey(id);
		//删除用户角色信息
		if(count>0){
			SysUserRoleExample sysUserRoleExample=new SysUserRoleExample();
			SysUserRoleExample.Criteria criteria=sysUserRoleExample.createCriteria();
			criteria.andSysUserIdEqualTo(id);
			List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectByExample(sysUserRoleExample);
			SysUserRole sysUserRole = sysUserRoles.get(0);
			count = sysUserRoleMapper.deleteByPrimaryKey(sysUserRole.getId());
		}
		return count;
	}


}
