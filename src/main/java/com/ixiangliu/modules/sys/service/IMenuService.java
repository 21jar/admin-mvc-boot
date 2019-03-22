package com.ixiangliu.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ixiangliu.modules.sys.entity.Menu;
import com.ixiangliu.modules.sys.entity.User;

import java.util.List;

public interface IMenuService extends IService<Menu> {

    List<Menu> getUserMenuList(Long userId);

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     * @param menuIdList  用户菜单ID
     */
    List<Menu> queryListParentId(Long parentId, List<Long> menuIdList);

    List<Menu> queryListParentId(Long parentId);
}
