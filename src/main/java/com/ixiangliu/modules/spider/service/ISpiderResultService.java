package com.ixiangliu.modules.spider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.modules.spider.entity.SpiderResult;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface ISpiderResultService extends IService<SpiderResult> {

    PageUtils queryPage(Map<String, Object> params);

    boolean updateBatchId(List<SpiderResult> list);

    boolean updateOrder(String keyword);
}

