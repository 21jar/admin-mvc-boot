package com.ixiangliu.modules.sys.controller;

import com.ixiangliu.common.constant.Const;
import com.ixiangliu.common.utils.Result;
import com.ixiangliu.modules.sys.entity.Dept;
import com.ixiangliu.modules.sys.service.IDeptService;
import com.ixiangliu.modules.sys.shiro.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;


/**
 * 部门管理
 */
@Slf4j
@RestController
@RequestMapping("/sys/dept")
public class DeptController {
	@Autowired
	private IDeptService iDeptService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:dept:list")
	public List<Dept> list(){
		List<Dept> deptList = iDeptService.queryList(new HashMap<String, Object>());

		return deptList;
	}

	/**
	 * 选择部门(添加、修改菜单)
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:dept:select")
	public Result select(){
		List<Dept> deptList = iDeptService.queryList(new HashMap<String, Object>());

		//添加一级部门
		if(ShiroUtils.getUserId() == Const.SUPER_ADMIN){
			Dept root = new Dept();
			root.setDeptId(0L);
			root.setName("一级部门");
			root.setParentId(-1L);
			root.setOpen(true);
			deptList.add(root);
		}

		return Result.ok().put("deptList", deptList);
	}

	/**
	 * 上级部门Id(管理员则为0)
	 */
	@RequestMapping("/info")
	@RequiresPermissions("sys:dept:list")
	public Result info(){
		long deptId = 0;
		if(ShiroUtils.getUserId() != Const.SUPER_ADMIN){
			List<Dept> deptList = iDeptService.queryList(new HashMap<String, Object>());
			Long parentId = null;
			for(Dept Dept : deptList){
				if(parentId == null){
					parentId = Dept.getParentId();
					continue;
				}

				if(parentId > Dept.getParentId().longValue()){
					parentId = Dept.getParentId();
				}
			}
			deptId = parentId;
		}

		return Result.ok().put("deptId", deptId);
	}
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{deptId}")
	@RequiresPermissions("sys:dept:info")
	public Result info(@PathVariable("deptId") Long deptId){
		Dept dept = iDeptService.getById(deptId);
		
		return Result.ok().put("dept", dept);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:dept:save")
	public Result save(@RequestBody Dept dept){
		iDeptService.save(dept);
		
		return Result.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:dept:update")
	public Result update(@RequestBody Dept dept){
		iDeptService.updateById(dept);
		
		return Result.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:dept:delete")
	public Result delete(long deptId){
		//判断是否有子部门
		List<Long> deptList = iDeptService.queryDetpIdList(deptId);
		if(deptList.size() > 0){
			return Result.error("请先删除子部门");
		}

		iDeptService.removeById(deptId);
		
		return Result.ok();
	}
	
}
