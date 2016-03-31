package com.ace.core.controller;


import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ace.core.bean.user.SysPermission;
import com.ace.core.service.user.RolePermissionService;

@Controller
@RequestMapping("/permission")
public class PermissonController {

	@Autowired
	private RolePermissionService rolePermissionService;
	
	/**
	 * 遍历可分配权限
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list.do")
	@RequiresPermissions("permission:query")
	public String list(ModelMap model) throws Exception{
		
		List<SysPermission> sysPermissionsMenu=rolePermissionService.getPermissionsMenu();
		List<SysPermission> sysPermissionsPermission=rolePermissionService.getPermissionPermission();
		model.addAttribute("sysPermissionsMenu", sysPermissionsMenu);
		model.addAttribute("sysPermissionsPermission", sysPermissionsPermission);
		return "admin_permission";
	}

	
	/**
	 * 添加权限节点
	 * @param sysPermission
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toAdd.do")
	@RequiresPermissions("permission:add")
	public String toAdd(String pid,String pname,ModelMap model) throws Exception{
		model.addAttribute("pid", pid);
		model.addAttribute("pname", pname);
		return "admin_permission_add";
	}
	
	/**
	 * 添加菜单权限节点
	 * @param sysPermission
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toAddMenu.do")
	@RequiresPermissions("permission:add")
	public String toAddMenu(ModelMap model) throws Exception{

		return "admin_menu_add";
	}
	/**
	 * 添加权限节点
	 * @param sysPermission
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/add.do")
	@RequiresPermissions("permission:add")
	public String add(SysPermission sysPermission) throws Exception{
		sysPermission.setParentids("0/1/"+sysPermission.getParentid());
		rolePermissionService.addPermisson(sysPermission);
		return "redirect:/permission/list.do";
	}
	
	
	/**
	 * 添加权限节点
	 * @param sysPermission
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addMenu.do")
	@RequiresPermissions("permission:add")
	public String addMenu(SysPermission sysPermission) throws Exception{
		sysPermission.setParentids("0/1/");
		rolePermissionService.addPermisson(sysPermission);
		return "redirect:/permission/list.do";
	}
	
}
