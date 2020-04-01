package com.ixiangliu.modules.work.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.common.utils.Query;
import com.ixiangliu.modules.sys.dao.DictDao;
import com.ixiangliu.modules.sys.entity.Dict;
import com.ixiangliu.modules.sys.service.IDictService;
import com.ixiangliu.modules.work.dao.ShareCheckDao;
import com.ixiangliu.modules.work.entity.ShareCheck;
import com.ixiangliu.modules.work.service.IShareCheckService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ShareCheckServiceImpl extends ServiceImpl<ShareCheckDao, ShareCheck> implements IShareCheckService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String um = (String)params.get("um");
        String deptName = (String)params.get("deptName");
        long currPage = Long.parseLong((String) params.get("page"));
        long pageSize = Long.parseLong((String)params.get("limit"));

//        IPage<ShareCheck> page = this.page(new Query<ShareCheck>().getPage(params));
        Page<ShareCheck> page = new Page<>(currPage , pageSize);
        List<ShareCheck> shareChecks = baseMapper.findList(page, um, deptName);
        page.setRecords(shareChecks);
        return new PageUtils(page);

    }

}
