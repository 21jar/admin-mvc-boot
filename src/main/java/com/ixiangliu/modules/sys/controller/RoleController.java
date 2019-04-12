package com.ixiangliu.modules.sys.controller;

import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.common.utils.Result;
import com.ixiangliu.modules.sys.entity.Role;
import com.ixiangliu.modules.sys.service.IRoleDeptService;
import com.ixiangliu.modules.sys.service.IRoleMenuService;
import com.ixiangliu.modules.sys.service.IRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 角色管理
 */
@RestController
@RequestMapping("/sys/role")
public class RoleController {

	@Autowired
	private IRoleService iRoleService;
	@Autowired
	private IRoleMenuService iRoleMenuService;
	@Autowired
	private IRoleDeptService iRoleDeptService;

	/**
	 * 角色列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:role:list")
	public Result list(@RequestParam Map<String, Object> params){
		PageUtils page = iRoleService.queryPage(params);
		return Result.ok().put("page", page);
	}

	/**
	 * 角色列表
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:role:select")
	public Result select(){
		List<Role> list = iRoleService.list();

		return Result.ok().put("list", list);
	}

	/**
	 * 角色信息
	 */
	@RequestMapping("/info/{roleId}")
	@RequiresPermissions("sys:role:info")
	public Result info(@PathVariable("roleId") Long roleId){
		Role role = iRoleService.getById(roleId);

		//查询角色对应的菜单
		List<Long> menuIdList = iRoleMenuService.queryMenuIdList(roleId);
		role.setMenuIdList(menuIdList);

		//查询角色对应的部门
		List<Long> deptIdList = iRoleDeptService.queryDeptIdList(new Long[]{roleId});
		role.setDeptIdList(deptIdList);

		return Result.ok().put("role", role);
	}

	/**
	 * 保存角色
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:role:save")
	public Result save(@RequestBody Role role){
//		ValidatorUtils.validateEntity(role);

		iRoleService.saveRole(role);

		return Result.ok();
	}

	/**
	 * 修改角色
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:role:update")
	public Result update(@RequestBody Role role){
//		ValidatorUtils.validateEntity(role);

		iRoleService.update(role);

		return Result.ok();
	}

	/**
	 * 删除角色
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:role:delete")
	public Result delete(@RequestBody Long[] roleIds){
		iRoleService.deleteBatch(roleIds);

		return Result.ok();
	}
}
