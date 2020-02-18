package com.ixiangliu.modules.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ixiangliu.modules.test.dao.TestSignLogDao;
import com.ixiangliu.modules.test.entity.TestSignLog;
import com.ixiangliu.modules.test.service.ITestSignLogService;
import org.springframework.stereotype.Service;


@Service
public class TestSignLogServiceImpl extends ServiceImpl<TestSignLogDao, TestSignLog> implements ITestSignLogService {

}
