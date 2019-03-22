package com.ixiangliu.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ixiangliu.modules.sys.entity.Menu;
import com.ixiangliu.modules.sys.entity.User;

import java.util.List;

public interface IUserService extends IService<User> {
    List<Long> queryAllMenuId(Long userId);
}
