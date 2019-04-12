package com.ixiangliu.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ixiangliu.common.constant.Const;
import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.common.utils.Query;
import com.ixiangliu.modules.sys.dao.RoleDao;
import com.ixiangliu.modules.sys.entity.Dept;
import com.ixiangliu.modules.sys.entity.Role;
import com.ixiangliu.modules.sys.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;


/**
 * 角色
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements IRoleService {
	@Autowired
	private IRoleMenuService iRoleMenuService;
	@Autowired
	private IRoleDeptService iRoleDeptService;
	@Autowired
	private IUserRoleService iUserRoleService;
	@Autowired
	private IDeptService iDeptService;

	@Override
//	@DataFilter(subDept = true, user = false)
	public PageUtils queryPage(Map<String, Object> params) {
		String roleName = (String)params.get("roleName");

		IPage<Role> page = this.page(
			new Query<Role>().getPage(params),
			new QueryWrapper<Role>()
				.like(StringUtils.isNotBlank(roleName),"role_name", roleName)
				.apply(params.get(Const.SQL_FILTER) != null, (String)params.get(Const.SQL_FILTER))
		);

		for(Role sysRoleEntity : page.getRecords()){
			Dept sysDeptEntity = iDeptService.getById(sysRoleEntity.getDeptId());
			if(sysDeptEntity != null){
				sysRoleEntity.setDeptName(sysDeptEntity.getName());
			}
		}

		return new PageUtils(page);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveRole(Role role) {
		role.setCreateTime(new Date());
		this.save(role);

		//保存角色与菜单关系
		iRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());

		//保存角色与部门关系
		iRoleDeptService.saveOrUpdate(role.getRoleId(), role.getDeptIdList());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(Role role) {
		this.updateById(role);

		//更新角色与菜单关系
		iRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());

		//保存角色与部门关系
		iRoleDeptService.saveOrUpdate(role.getRoleId(), role.getDeptIdList());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteBatch(Long[] roleIds) {
		//删除角色
		this.removeByIds(Arrays.asList(roleIds));

		//删除角色与菜单关联
		iRoleMenuService.deleteBatch(roleIds);

		//删除角色与部门关联
		iRoleDeptService.deleteBatch(roleIds);

		//删除角色与用户关联
		iUserRoleService.deleteBatch(roleIds);
	}


}
