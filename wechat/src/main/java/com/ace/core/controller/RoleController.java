package com.ace.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ace.core.bean.user.SysPermission;
import com.ace.core.bean.user.SysRole;
import com.ace.core.service.user.RolePermissionService;
import com.ace.core.service.user.SysService;
import com.ace.core.service.user.UserRoleService;
import com.ace.core.util.ResponseUtils;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private RolePermissionService rolePermissionService;
	@Autowired
	private SysService sysService;
	
	/**
	 * 权限列表
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list.do")
	@RequiresPermissions("role:query")
	public String list(ModelMap model) throws Exception{
		List<SysRole> roles = userRoleService.getRoles();
		model.addAttribute("roles", roles);
		return "admin_role";
	}
	
	@RequestMapping("/toAdd.do")
	@RequiresPermissions("role:add")
	public String toAdd(ModelMap model) throws Exception{
		List<SysPermission> sysPermissionsMenu=rolePermissionService.getPermissionsMenu();
		List<SysPermission> sysPermissionsPermission=rolePermissionService.getPermissionPermission();
		model.addAttribute("sysPermissionsMenu", sysPermissionsMenu);
		model.addAttribute("sysPermissionsPermission", sysPermissionsPermission);
		return "admin_role_add";
	}
	
	@RequestMapping("/add.do")
	@RequiresPermissions("role:add")
	public String add(SysRole sysRole,Long[] pids,ModelMap model) throws Exception{
		int count = rolePermissionService.addRoleGivePermission(sysRole, pids);
		if(count>0){
			model.addAttribute("msg", 1);
		}else{
			model.addAttribute("msg", 0);
		}
		return "redirect:/role/list.do";
	}
	
	@RequestMapping("/toUpdate.do")
	public String toUpdate(String roleId,ModelMap model) throws Exception{
		//根据角色Id查询角色信息
		SysRole sysRole=userRoleService.getRoleById(roleId);
		//取出系统可以分配的权限和菜单
		List<SysPermission> sysPermissionsMenu=rolePermissionService.getPermissionsMenu();
		List<SysPermission> sysPermissionsPermission=rolePermissionService.getPermissionPermission();
		//取出用户拥有的权限和惨淡
		List<SysPermission> menus = rolePermissionService.getRolemenus(roleId);
		List<SysPermission> permissions =rolePermissionService.getRolePermission(roleId);
		model.addAttribute("sysRole", sysRole);
		model.addAttribute("sysPermissionsMenu", sysPermissionsMenu);
		model.addAttribute("sysPermissionsPermission", sysPermissionsPermission);
		model.addAttribute("menus", menus);
		model.addAttribute("permissions", permissions);
		return "admin_role_update";
	}
	
	@RequestMapping("/update.do")
	public void update(SysRole sysRole,Long[] pids,HttpServletResponse response,ModelMap model) throws Exception{
		int flag = rolePermissionService.updateRoleUpdatePermission(sysRole, pids);
		JSONObject jo=new JSONObject();
		if(flag>0){
	    	jo.put("status", "y");
	    	jo.put("info", "角色信息修改成功");
		}else{
	    	jo.put("status", "n");
	    	jo.put("info", "角色信息修改失败");
		}
    	ResponseUtils.renderJson(response, jo.toString());
	}
}
