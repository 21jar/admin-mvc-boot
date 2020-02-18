package com.ixiangliu.modules.test.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ixiangliu.modules.test.entity.TestSignLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TestSignLogDao extends BaseMapper<TestSignLog> {
	
}
