package com.ixiangliu.modules.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.common.utils.Query;
import com.ixiangliu.modules.sys.entity.Dict;
import com.ixiangliu.modules.test.dao.TestSignUserDao;
import com.ixiangliu.modules.test.entity.TestSignUser;
import com.ixiangliu.modules.test.service.ITestSignUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class TestSignUserServiceImpl extends ServiceImpl<TestSignUserDao, TestSignUser> implements ITestSignUserService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");
        String date = (String)params.get("date");

        IPage<TestSignUser> page = this.page(
                new Query<TestSignUser>().getPage(params),
                new QueryWrapper<TestSignUser>()
                        .like(StringUtils.isNotBlank(name),"name", name).eq(StringUtils.isNotBlank(date),"date", date)
        );

        return new PageUtils(page);
    }
}
