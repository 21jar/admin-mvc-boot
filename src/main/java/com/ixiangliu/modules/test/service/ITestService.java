package com.ixiangliu.modules.test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.modules.sys.entity.Dict;
import com.ixiangliu.modules.test.entity.TestSignUser;

import java.util.Map;

/**
 * 数据字典
 */
public interface ITestService extends IService<TestSignUser> {

    PageUtils queryPage(Map<String, Object> params);
}

