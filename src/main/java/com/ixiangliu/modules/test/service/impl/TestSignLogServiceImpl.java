package com.ixiangliu.modules.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ixiangliu.modules.test.dao.TestSignLogDao;
import com.ixiangliu.modules.test.entity.TestSignLog;
import com.ixiangliu.modules.test.service.ITestSignLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class TestSignLogServiceImpl extends ServiceImpl<TestSignLogDao, TestSignLog> implements ITestSignLogService {

    @Override
    public List<TestSignLog> selectList(Map<String, Object> params) {
        String name = (String)params.get("name");
        String date = (String)params.get("date");
        List<TestSignLog> testSignLogs = baseMapper.findList(StringUtils.isNotBlank(name) ? name : "", StringUtils.isNotBlank(date) ? date : "");
        return testSignLogs;
    }
}
