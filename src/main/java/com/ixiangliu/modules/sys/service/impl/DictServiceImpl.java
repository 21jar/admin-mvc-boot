package com.ixiangliu.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.common.utils.Query;
import com.ixiangliu.modules.sys.dao.DictDao;
import com.ixiangliu.modules.sys.entity.Dict;
import com.ixiangliu.modules.sys.service.IDictService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class DictServiceImpl extends ServiceImpl<DictDao, Dict> implements IDictService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");

        IPage<Dict> page = this.page(
            new Query<Dict>().getPage(params),
            new QueryWrapper<Dict>()
                .like(StringUtils.isNotBlank(name),"name", name)
        );

        return new PageUtils(page);
    }

}
