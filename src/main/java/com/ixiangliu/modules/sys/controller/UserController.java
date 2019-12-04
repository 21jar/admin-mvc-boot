package com.ixiangliu.modules.sys.controller;


import com.ixiangliu.common.exception.BizException;
import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.common.utils.Result;
import com.ixiangliu.modules.sys.entity.User;
import com.ixiangliu.modules.sys.service.IUserRoleService;
import com.ixiangliu.modules.sys.service.IUserService;
import com.ixiangliu.modules.sys.shiro.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 */
@Slf4j
@RestController
@RequestMapping("/sys/user")
public class UserController {

	@Autowired
	private IUserService iUserService;

	@Autowired
	private IUserRoleService iUserRoleService;

	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:user:list")
	public Result list(@RequestParam Map<String, Object> params){
		PageUtils page = iUserService.queryPage(params);
		return Result.ok().put("page", page);
	}
	
	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	public Result info(){
		return Result.ok().put("user", ShiroUtils.getUser());
	}

	/**
	 * 修改登录用户密码
	 */
	@RequestMapping("/password")
	public Result password(String password, String newPassword){
		if (StringUtils.isBlank(newPassword)) {
			throw new BizException("新密码不为能空");
		}
		//原密码
		password = ShiroUtils.sha256(password, ShiroUtils.getUser().getSalt());
		//新密码
		newPassword = ShiroUtils.sha256(newPassword, ShiroUtils.getUser().getSalt());
		//更新密码
		boolean flag = iUserService.updatePassword(ShiroUtils.getUserId(), password, newPassword);
		if(!flag){
			return Result.error("原密码不正确");
		}
		return Result.ok();
	}

	/**
	 * 用户信息
	 */
	@RequestMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public Result info(@PathVariable("userId") Long userId){
		User user = iUserService.getById(userId);
		//获取用户所属的角色列表
		List<Long> roleIdList = iUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		return Result.ok().put("user", user);
	}

	/**
	 * 保存用户
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:user:save")
	public Result save(@RequestBody User user){
//		ValidatorUtils.validateEntity(user, AddGroup.class);
		iUserService.saveUser(user);
		return Result.ok();
	}

	/**
	 * 修改用户
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:user:update")
	public Result update(@RequestBody User user){
//		ValidatorUtils.validateEntity(user, UpdateGroup.class);
		iUserService.update(user);
		return Result.ok();
	}

	/**
	 * 删除用户
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public Result delete(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, 1L)){
			return Result.error("系统管理员不能删除");
		}
		if(ArrayUtils.contains(userIds, ShiroUtils.getUserId())){
			return Result.error("当前用户不能删除");
		}
		iUserService.removeByIds(Arrays.asList(userIds));
		return Result.ok();
	}
}
