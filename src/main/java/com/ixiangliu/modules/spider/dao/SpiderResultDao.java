package com.ixiangliu.modules.spider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ixiangliu.modules.spider.entity.SpiderResult;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Mapper
@Repository
public interface SpiderResultDao extends BaseMapper<SpiderResult> {
	
}
