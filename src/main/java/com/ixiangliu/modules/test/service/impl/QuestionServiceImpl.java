package com.ixiangliu.modules.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.common.utils.Query;
import com.ixiangliu.modules.test.dao.QuestionDao;
import com.ixiangliu.modules.test.entity.Question;
import com.ixiangliu.modules.test.service.IQuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionDao, Question> implements IQuestionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String searchTitle = (String)params.get("searchTitle");

        IPage<Question> page = this.page(
            new Query<Question>().getPage(params),
            new QueryWrapper<Question>()
                .like(StringUtils.isNotBlank(searchTitle),"title", searchTitle)
        );

        return new PageUtils(page);
    }

}
