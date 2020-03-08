package com.ixiangliu.modules.test.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ixiangliu.modules.test.entity.TestSignLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TestSignLogDao extends BaseMapper<TestSignLog> {

    @Select("select test_sign_log.*,test_sign_user.name AS userName from test_sign_log LEFT JOIN  test_sign_user ON test_sign_log.user_id = test_sign_user.id WHERE test_sign_log.date like CONCAT('',#{date},'%') and test_sign_user.name like CONCAT('',#{name},'%')")
    List<TestSignLog> findList(String name, String date);
}
