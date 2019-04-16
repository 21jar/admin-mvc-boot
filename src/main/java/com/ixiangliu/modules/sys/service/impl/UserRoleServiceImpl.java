package com.ixiangliu.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ixiangliu.modules.sys.dao.UserRoleDao;
import com.ixiangliu.modules.sys.entity.UserRole;
import com.ixiangliu.modules.sys.service.IUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户与角色对应关系
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRole> implements IUserRoleService {
	@Override
	public void saveOrUpdate(Long userId, List<Long> roleIdList) {
		//先删除用户与角色关系
		this.remove(new QueryWrapper<UserRole>().eq("user_id", userId));

		if(roleIdList == null || roleIdList.size() == 0){
			return ;
		}
		
		//保存用户与角色关系
		for(Long roleId : roleIdList){
			UserRole sysUserRoleEntity = new UserRole();
			sysUserRoleEntity.setUserId(userId);
			sysUserRoleEntity.setRoleId(roleId);

			this.save(sysUserRoleEntity);
		}

	}

	@Override
	public List<Long> queryRoleIdList(Long userId) {
		return baseMapper.queryRoleIdList(userId);
	}

	@Override
	public int deleteBatch(Long[] roleIds){
		return baseMapper.deleteBatch(roleIds);
	}
}
