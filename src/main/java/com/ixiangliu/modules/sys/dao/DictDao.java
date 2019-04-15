package com.ixiangliu.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ixiangliu.modules.sys.entity.Dict;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据字典
 */
@Mapper
public interface DictDao extends BaseMapper<Dict> {
	
}
