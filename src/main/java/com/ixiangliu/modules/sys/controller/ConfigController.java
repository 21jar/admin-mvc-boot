package com.ixiangliu.modules.sys.controller;

import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.common.utils.Result;
import com.ixiangliu.common.validator.ValidatorUtils;
import com.ixiangliu.modules.sys.entity.Config;
import com.ixiangliu.modules.sys.service.IConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 系统配置信息
 */
@Slf4j
@RestController
@RequestMapping("/sys/config")
public class ConfigController {
	@Autowired
	private IConfigService iConfigService;
	
	/**
	 * 所有配置列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:config:list")
	public Result list(@RequestParam Map<String, Object> params){
		PageUtils page = iConfigService.queryPage(params);

		return Result.ok().put("page", page);
	}


	/**
	 * 配置信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sys:config:info")
	@ResponseBody
	public Result info(@PathVariable("id") Long id){
		Config config = iConfigService.getById(id);

		return Result.ok().put("config", config);
	}

	/**
	 * 保存配置
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:config:save")
	public Result save(@RequestBody Config config){
		ValidatorUtils.validateEntity(config);

		iConfigService.saveConfig(config);

		return Result.ok();
	}

	/**
	 * 修改配置
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:config:update")
	public Result update(@RequestBody Config config){
		ValidatorUtils.validateEntity(config);

		iConfigService.update(config);

		return Result.ok();
	}

	/**
	 * 删除配置
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:config:delete")
	public Result delete(@RequestBody Long[] ids){
		iConfigService.deleteBatch(ids);
		
		return Result.ok();
	}

}
