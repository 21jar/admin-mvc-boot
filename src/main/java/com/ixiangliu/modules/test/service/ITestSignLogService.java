package com.ixiangliu.modules.test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ixiangliu.modules.test.entity.TestSignLog;

import java.util.List;
import java.util.Map;


public interface ITestSignLogService extends IService<TestSignLog> {

    List<TestSignLog> selectList(Map<String, Object> params);
}

