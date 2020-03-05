package com.ixiangliu.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ixiangliu.modules.sys.entity.Oss;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 文件上传
 */
@Mapper
@Repository
public interface OssDao extends BaseMapper<Oss> {
	
}
