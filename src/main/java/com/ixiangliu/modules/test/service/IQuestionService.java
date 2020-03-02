package com.ixiangliu.modules.test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.modules.sys.entity.Dict;
import com.ixiangliu.modules.test.entity.Question;

import java.util.Map;

/**
 *
 */
public interface IQuestionService extends IService<Question> {

    PageUtils queryPage(Map<String, Object> params);
}

