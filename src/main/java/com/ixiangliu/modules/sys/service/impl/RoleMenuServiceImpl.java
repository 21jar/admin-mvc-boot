package com.ixiangliu.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ixiangliu.modules.sys.dao.RoleMenuDao;
import com.ixiangliu.modules.sys.entity.RoleMenu;
import com.ixiangliu.modules.sys.service.IRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色与菜单对应关系
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuDao, RoleMenu> implements IRoleMenuService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
		//先删除角色与菜单关系
		deleteBatch(new Long[]{roleId});
		if(menuIdList.size() == 0){
			return ;
		}
		//保存角色与菜单关系
		for(Long menuId : menuIdList){
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setMenuId(menuId);
			roleMenu.setRoleId(roleId);

			this.save(roleMenu);
		}
	}

	@Override
	public List<Long> queryMenuIdList(Long roleId) {
		return baseMapper.queryMenuIdList(roleId);
	}

	@Override
	public int deleteBatch(Long[] roleIds){
		return baseMapper.deleteBatch(roleIds);
	}

}
