package com.ixiangliu.modules.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.common.utils.Query;
import com.ixiangliu.modules.sys.dao.DictDao;
import com.ixiangliu.modules.sys.entity.Dict;
import com.ixiangliu.modules.sys.service.IDictService;
import com.ixiangliu.modules.test.dao.TestDao;
import com.ixiangliu.modules.test.entity.TestSignUser;
import com.ixiangliu.modules.test.service.ITestService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class TestServiceImpl extends ServiceImpl<TestDao, TestSignUser> implements ITestService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");

        IPage<TestSignUser> page = this.page(
            new Query<TestSignUser>().getPage(params),
            new QueryWrapper<TestSignUser>()
                .like(StringUtils.isNotBlank(name),"name", name)
        );

        return new PageUtils(page);
    }

}
