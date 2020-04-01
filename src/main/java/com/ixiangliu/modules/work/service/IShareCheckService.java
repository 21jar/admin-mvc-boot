package com.ixiangliu.modules.work.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.modules.work.entity.ShareCheck;

import java.util.Map;

/**
 *
 */
public interface IShareCheckService extends IService<ShareCheck> {

    PageUtils queryPage(Map<String, Object> params);
}

