package com.ixiangliu.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.modules.sys.entity.Dict;

import java.util.Map;

/**
 * 数据字典
 */
public interface IDictService extends IService<Dict> {

    PageUtils queryPage(Map<String, Object> params);
}

