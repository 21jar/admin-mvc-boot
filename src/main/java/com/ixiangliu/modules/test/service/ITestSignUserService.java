package com.ixiangliu.modules.test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.modules.test.entity.TestSignUser;

import java.util.Map;


public interface ITestSignUserService extends IService<TestSignUser> {

    PageUtils queryPage(Map<String, Object> params);
}

