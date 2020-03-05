package com.ixiangliu.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.modules.sys.entity.Oss;

import java.util.Map;

/**
 * 文件上传
 */
public interface IOssService extends IService<Oss> {

	PageUtils queryPage(Map<String, Object> params);
}
