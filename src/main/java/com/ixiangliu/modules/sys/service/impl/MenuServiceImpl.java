package com.ixiangliu.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ixiangliu.common.constant.Const;
import com.ixiangliu.modules.sys.dao.MenuDao;
import com.ixiangliu.modules.sys.entity.Menu;
import com.ixiangliu.modules.sys.service.IMenuService;
import com.ixiangliu.modules.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements IMenuService {

    @Autowired
    IUserService iUserService;

    @Override
    public List<Menu> getUserMenuList(Long userId) {
        //系统管理员，拥有最高权限
        if(userId == Const.SUPER_ADMIN){
            return getAllMenuList(null);
        }

        //用户菜单列表
        List<Long> menuIdList = iUserService.queryAllMenuId(userId);
        return getAllMenuList(menuIdList);
    }

    /**
     * 获取所有菜单列表
     */
    private List<Menu> getAllMenuList(List<Long> menuIdList){
        //查询根菜单列表
        List<Menu> menuList = queryListParentId(0L, menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);

        return menuList;
    }

    /**
     * 递归
     */
    private List<Menu> getMenuTreeList(List<Menu> menuList, List<Long> menuIdList){
        List<Menu> subMenuList = new ArrayList<Menu>();

        for(Menu entity : menuList){
            //目录
            if(entity.getType() == Const.MenuType.CATALOG.getValue()){
                entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
            }
            subMenuList.add(entity);
        }

        return subMenuList;
    }

    @Override
    public List<Menu> queryListParentId(Long parentId, List<Long> menuIdList) {
        List<Menu> menuList = queryListParentId(parentId);
        if(menuIdList == null){
            return menuList;
        }

        List<Menu> userMenuList = new ArrayList<>();
        for(Menu menu : menuList){
            if(menuIdList.contains(menu.getMenuId())){
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    @Override
    public List<Menu> queryListParentId(Long parentId) {
        return baseMapper.queryListParentId(parentId);
    }
}
