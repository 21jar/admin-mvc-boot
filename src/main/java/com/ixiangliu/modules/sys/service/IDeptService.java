package com.ixiangliu.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ixiangliu.modules.sys.entity.Dept;

import java.util.List;
import java.util.Map;

/**
 * 部门管理
 */
public interface IDeptService extends IService<Dept> {

	List<Dept> queryList(Map<String, Object> map);

	/**
	 * 查询子部门ID列表
	 * @param parentId  上级部门ID
	 */
	List<Long> queryDetpIdList(Long parentId);

	/**
	 * 获取子部门ID，用于数据过滤
	 */
	List<Long> getSubDeptIdList(Long deptId);

}
