package com.ixiangliu.modules.test.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ixiangliu.modules.sys.entity.Dict;
import com.ixiangliu.modules.test.entity.TestSignUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 数据字典
 */
@Mapper
@Repository
public interface TestDao extends BaseMapper<TestSignUser> {
	
}
