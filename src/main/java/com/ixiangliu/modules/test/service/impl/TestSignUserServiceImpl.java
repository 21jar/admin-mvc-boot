package com.ixiangliu.modules.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ixiangliu.modules.test.dao.TestSignUserDao;
import com.ixiangliu.modules.test.entity.TestSignUser;
import com.ixiangliu.modules.test.service.ITestSignUserService;
import org.springframework.stereotype.Service;


@Service
public class TestSignUserServiceImpl extends ServiceImpl<TestSignUserDao, TestSignUser> implements ITestSignUserService {

}
