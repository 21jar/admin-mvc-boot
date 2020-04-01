package com.ixiangliu.modules.work.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ixiangliu.modules.work.entity.ShareCheck;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Mapper
@Repository
public interface ShareCheckDao extends BaseMapper<ShareCheck> {

    List<ShareCheck> findList(Page<ShareCheck> page, String um, String deptName);
	
}
