package com.ixiangliu.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.modules.sys.entity.Log;

import java.util.Map;


/**
 * 系统日志
 */
public interface ILogService extends IService<Log> {

    PageUtils queryPage(Map<String, Object> params);

}
