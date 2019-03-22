package com.ixiangliu.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ixiangliu.modules.sys.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserDao extends BaseMapper<User> {

    List<String> queryAllPerms(Long userId);

    List<Long> queryAllMenuId(Long userId);
}
