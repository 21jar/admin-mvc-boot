package com.ixiangliu.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ixiangliu.common.constant.Const;
import com.ixiangliu.modules.sys.dao.MenuDao;
import com.ixiangliu.modules.sys.dao.UserDao;
import com.ixiangliu.modules.sys.entity.Menu;
import com.ixiangliu.modules.sys.entity.User;
import com.ixiangliu.modules.sys.service.IMenuService;
import com.ixiangliu.modules.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements IUserService {


    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return baseMapper.queryAllMenuId(userId);
    }

}
