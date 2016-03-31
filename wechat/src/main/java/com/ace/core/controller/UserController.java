package com.ace.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ace.core.bean.user.ActiveUser;
import com.ace.core.bean.user.SysRole;
import com.ace.core.bean.user.SysUser;
import com.ace.core.service.user.UserRoleService;
import com.ace.core.shiro.CustomRealm;
import com.ace.core.util.ResponseUtils;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRoleService userRoleService;


	/**
	 * 获取所有的用户信息
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list.do")
	@RequiresPermissions("user:query")
	public String list(ModelMap model) throws Exception {
		List<SysUser> sysUsers = userRoleService.getSysUsers();
		model.addAttribute("sysUsers", sysUsers);
		return "admin_list";
	}

	/**
	 * 锁定及解锁用户
	 * 
	 * @param sysUser
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/updateLocked.do")
	@RequiresPermissions("user:update")
	public void udpateLocked(SysUser sysUser, HttpServletResponse response)
			throws Exception {
		int flag = userRoleService.updateLocked(sysUser);
		JSONObject jo = new JSONObject();
		if (flag > 0) {
			jo.put("status", "y");
			jo.put("info", "用户状态修改成功");
		} else {
			jo.put("status", "n");
			jo.put("info", "用户状态修改失败");
		}
		ResponseUtils.renderJson(response, jo.toString());
	}

	// 注入realm
	@Autowired
	private CustomRealm customRealm;

	@RequestMapping(value="/clearShiroCache.do")
	@RequiresPermissions("user:clear")
	public void clearShiroCache(HttpServletResponse response) {

		// 清除缓存，将来正常开发要在service调用customRealm.clearCached()
		customRealm.clearCached();
		JSONObject jo = new JSONObject();
		jo.put("status", "y");
		jo.put("info", "用户状态修改成功");
		ResponseUtils.renderJson(response, jo.toString());
	}

	
	/**
	 * 进入添加用户页面
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toAdd.do")
	@RequiresPermissions("user:add")
	public String add(ModelMap model) throws Exception{
		List<SysRole> roles = userRoleService.getRoles();
		model.addAttribute("roles", roles);
		return "admin_add";
	}
	
	/**
	 * 用户名验证
	 * @param param
	 * @param name
	 * @throws Exception 
	 */
	@RequestMapping(value="/vilidataUserName.do",method = RequestMethod.POST)
	@RequiresPermissions("user:vlidata")
	public void vilidataUserName(String param,String name,HttpServletResponse response) throws Exception{
		SysUser sysUserByUserCode = userRoleService.getSysUserByUserCode(param);
		JSONObject jo=new JSONObject();
		if(sysUserByUserCode==null){	
	    	jo.put("status", "y");
	    	jo.put("info", "用户名可用");
		}else{
	    	jo.put("status", "n");
	    	jo.put("info", "用户名已被占用");
	    	
		}
		ResponseUtils.renderJson(response, jo.toString());
	}
	
	
	
	/**
	 * 添加用户
	 * @param sysUser
	 * @param rid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/add.do")
	@RequiresPermissions("user:add")
	public  String add(SysUser sysUser,String rid) throws Exception{
		userRoleService.addUser(sysUser, rid);
		return "redirect:/user/list.do";
	}
	
	/**
	 * 原始密码验证
	 * @param param
	 * @param name
	 * @throws Exception 
	 */
	@RequestMapping(value="/vilidataPwd.do",method = RequestMethod.POST)
	public void vilidataPwd(String param,String name,HttpServletResponse response) throws Exception{
		//取出主体信息
		Subject subject = SecurityUtils.getSubject();
		//从主体信息取出身份信息
		ActiveUser activeUser=(ActiveUser) subject.getPrincipal();
		boolean suer=userRoleService.isSuerPwd(param, activeUser.getUsercode());
		JSONObject jo=new JSONObject();
		if(suer==true){	
	    	jo.put("status", "y");
	    	jo.put("info", "原始密码正确");
		}else{
	    	jo.put("status", "n");
	    	jo.put("info", "原始密码错误");
	    	
		}
		ResponseUtils.renderJson(response, jo.toString());
	}
	
	
	@RequestMapping("/updatePwd.do")
	public String updatePwd(ModelMap model){
		//取出主体信息
		Subject subject = SecurityUtils.getSubject();
		//从主体信息取出身份信息
		ActiveUser activeUser=(ActiveUser) subject.getPrincipal();
		model.addAttribute("activeUser", activeUser);
		return "update_pwd";
	}
	
	@RequestMapping("/doUpdate.do")
	public String doUpdate(SysUser sysUser) throws Exception{
		//取出主体信息
		Subject subject = SecurityUtils.getSubject();
		//从主体信息取出身份信息
		ActiveUser activeUser=(ActiveUser) subject.getPrincipal();
		sysUser.setId(activeUser.getUserid());
		int count = userRoleService.updatePwd(sysUser);
		if(count>0){
			return "redirect:/logout.do";
		}else{
			return "redirect:/user/updatePwd.do";
		}
	}
	
	/**
	 * 重置管理员密码
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/resetPwd.do")
	public void resetPwd(String id,HttpServletResponse response) throws Exception{
		int count = userRoleService.resetPwd(id);
		JSONObject jo=new JSONObject();
		if(count>0){	
	    	jo.put("msg", "密码重置成功");
	    	
		}else{
	    	jo.put("msg", "密码重置失败");
	    	
		}
		ResponseUtils.renderJson(response, jo.toString());
	}
	
	/**
	 * 进入管理呀un更新页面
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toUpdate.do")
	public String toUpdate(String id,ModelMap model) throws Exception{
		//根据用户Id获取管理员信息
		SysUser sysUser = userRoleService.getSysUserById(id);
		model.addAttribute("sysUser", sysUser);
		List<SysRole> roles = userRoleService.getRoles();
		model.addAttribute("roles", roles);
		return "admin_update";
	}
	
	/**
	 * 跟新用户信息
	 * @param sysUser
	 * @param rid
	 * @param response
	 */
	@RequestMapping("/update.do")
	public void update(SysUser sysUser,String rid,HttpServletResponse response){
		int count = userRoleService.updateSysUser(sysUser, rid);
		JSONObject jo=new JSONObject();
		if(count>0){	
	    	jo.put("status", "y");
	    	jo.put("info", "用户信息修改成功");
		}else{
	    	jo.put("status", "n");
	    	jo.put("info", "用户信息修改失败");
	    	
		}
		ResponseUtils.renderJson(response, jo.toString());
	}
	
	/**
	 * 删除用户信息极其关联信息
	 * @param id
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/delete.do")
	public void delete(String id,HttpServletResponse response) throws Exception{
		int count = userRoleService.deleteSysUser(id);
		JSONObject jo=new JSONObject();
		if(count>0){	
	    	jo.put("msg", "用户信息彻底清除");
	    	
		}else{
	    	jo.put("msg", "用户信息彻底清除");
	    	
		}
		ResponseUtils.renderJson(response, jo.toString());
	}
}
