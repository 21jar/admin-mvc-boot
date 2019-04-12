package com.ixiangliu.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ixiangliu.modules.sys.entity.RoleDept;
import com.ixiangliu.modules.sys.entity.RoleMenu;

import java.util.List;

/**
 * 角色与机构对应关系
 */
public interface IRoleDeptService extends IService<RoleDept> {
    void saveOrUpdate(Long roleId, List<Long> deptIdList);

    /**
     * 根据角色ID，获取部门ID列表
     */
    List<Long> queryDeptIdList(Long[] roleIds) ;

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(Long[] roleIds);
}
