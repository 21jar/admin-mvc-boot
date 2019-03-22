package com.ixiangliu.modules.sys.controller;


import com.ixiangliu.common.utils.Result;
import com.ixiangliu.modules.sys.entity.Menu;
import com.ixiangliu.modules.sys.service.IMenuService;
import com.ixiangliu.modules.sys.shiro.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统用户
 */
@RestController
@RequestMapping("/sys/menu")
public class MenuController {

	@Autowired
	private IMenuService iMenuService;
	/**
	 * 导航菜单
	 */
	@RequestMapping("/nav")
	public Result nav(){
		List<Menu> menuList = iMenuService.getUserMenuList(ShiroUtils.getId());
		return Result.ok().put("menuList", menuList);
	}
}
