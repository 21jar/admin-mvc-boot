package com.ixiangliu.modules.job.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.modules.job.entity.ScheduleJobLog;

import java.util.Map;

/**
 * 定时任务日志
 */
public interface ScheduleJobLogService extends IService<ScheduleJobLog> {

	PageUtils queryPage(Map<String, Object> params);
	
}
