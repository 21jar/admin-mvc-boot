package com.ixiangliu.modules.sys.controller;


import com.ixiangliu.common.constant.Const;
import com.ixiangliu.common.exception.BizException;
import com.ixiangliu.common.utils.Result;
import com.ixiangliu.modules.sys.entity.Menu;
import com.ixiangliu.modules.sys.service.IMenuService;
import com.ixiangliu.modules.sys.shiro.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统用户
 */
@Slf4j
@RestController
@RequestMapping("/sys/menu")
public class MenuController {

    @Autowired
    private IMenuService iMenuService;

    /**
     * 导航菜单
     */
    @RequestMapping("/nav")
    public Result nav() {
        List<Menu> menuList = iMenuService.getUserMenuList(ShiroUtils.getUserId());
        return Result.ok().put("menuList", menuList);
    }

    /**
     * 所有菜单列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:menu:list")
    public List<Menu> list() {
        List<Menu> menuList = iMenuService.list();
        for (Menu sysMenuEntity : menuList) {
            Menu parentMenuEntity = iMenuService.getById(sysMenuEntity.getParentId());
            if (parentMenuEntity != null) {
                sysMenuEntity.setParentName(parentMenuEntity.getName());
            }
        }

        return menuList;
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:menu:select")
    public Result select() {
        //查询列表数据
        List<Menu> menuList = iMenuService.queryNotButtonList();

        //添加顶级菜单
        Menu root = new Menu();
        root.setMenuId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);
        menuList.add(root);

        return Result.ok().put("menuList", menuList);
    }

    /**
     * 菜单信息
     */
    @RequestMapping("/info/{menuId}")
    @RequiresPermissions("sys:menu:info")
    public Result info(@PathVariable("menuId") Long menuId) {
        Menu menu = iMenuService.getById(menuId);
        return Result.ok().put("menu", menu);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:menu:save")
    public Result save(@RequestBody Menu menu) {
        //数据校验
        try {
            verifyForm(menu);
            iMenuService.save(menu);
        } catch (Exception e) {
            return Result.error("操作失败");
        }
        return Result.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:menu:update")
    public Result update(@RequestBody Menu menu) {
        //数据校验
        verifyForm(menu);

        iMenuService.updateById(menu);

        return Result.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:menu:delete")
    public Result delete(long menuId) {
        if (menuId <= 31) {
            return Result.error("系统菜单，不能删除");
        }

        //判断是否有子菜单或按钮
        List<Menu> menuList = iMenuService.queryListParentId(menuId);
        if (menuList.size() > 0) {
            return Result.error("请先删除子菜单或按钮");
        }

        iMenuService.delete(menuId);

        return Result.ok();
    }

    /**
     * 验证参数是否正确
     */
    private void verifyForm(Menu menu) {
        if (StringUtils.isBlank(menu.getName())) {
            throw new BizException("菜单名称不能为空");
        }

        if (menu.getParentId() == null) {
            throw new BizException("上级菜单不能为空");
        }

        //菜单
        if (menu.getType() == Const.MenuType.MENU.getValue()) {
            if (StringUtils.isBlank(menu.getUrl())) {
                throw new BizException("菜单URL不能为空");
            }
        }

        //上级菜单类型
        int parentType = Const.MenuType.CATALOG.getValue();
        if (menu.getParentId() != 0) {
            Menu parentMenu = iMenuService.getById(menu.getParentId());
            parentType = parentMenu.getType();
        }

        //目录、菜单
        if (menu.getType() == Const.MenuType.CATALOG.getValue() ||
                menu.getType() == Const.MenuType.MENU.getValue()) {
            if (parentType != Const.MenuType.CATALOG.getValue()) {
                throw new BizException("上级菜单只能为目录类型");
            }
            return;
        }

        //按钮
        if (menu.getType() == Const.MenuType.BUTTON.getValue()) {
            if (parentType != Const.MenuType.MENU.getValue()) {
                throw new BizException("上级菜单只能为菜单类型");
            }
            return;
        }
    }
}
