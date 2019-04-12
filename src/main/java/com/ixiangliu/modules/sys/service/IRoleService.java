package com.ixiangliu.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.modules.sys.entity.Role;

import java.util.Map;

/**
 * 角色
 */
public interface IRoleService extends IService<Role> {

	PageUtils queryPage(Map<String, Object> params);

	void saveRole(Role role);

	void update(Role role);
	
	void deleteBatch(Long[] roleIds);

}
