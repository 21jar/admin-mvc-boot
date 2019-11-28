package com.ixiangliu.modules.sys.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ixiangliu.modules.sys.entity.Log;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 系统日志
 */
@Mapper
@Repository
public interface LogDao extends BaseMapper<Log> {
	
}
