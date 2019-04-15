package com.ixiangliu.modules.sys.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ixiangliu.modules.sys.entity.Log;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统日志
 */
@Mapper
public interface LogDao extends BaseMapper<Log> {
	
}
