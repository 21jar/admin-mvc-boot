package com.ixiangliu.modules.spider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.common.utils.Query;
import com.ixiangliu.modules.spider.dao.SpiderResultDao;
import com.ixiangliu.modules.spider.entity.SpiderResult;
import com.ixiangliu.modules.spider.service.ISpiderResultService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("spiderResultService")
public class SpiderResultServiceImpl extends ServiceImpl<SpiderResultDao, SpiderResult> implements ISpiderResultService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String title = (String)params.get("title");
        String order = (String)params.get("order");
        String sidx = (String)params.get("sidx");

        IPage<SpiderResult> page = this.page(
            new Query<SpiderResult>().getPage(params),
            new QueryWrapper<SpiderResult>().like(StringUtils.isNotBlank(title),"title", title).orderByAsc(StringUtils.isBlank(sidx),"param_one desc,order_num desc,param_three"));

        return new PageUtils(page);
    }

    @Override
    public boolean updateBatchId(List<SpiderResult> list) {
        return baseMapper.updateBatchId(list);
    }

}
