package com.ixiangliu.modules.test.controller;

import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.common.utils.Result;
import com.ixiangliu.common.validator.ValidatorUtils;
import com.ixiangliu.modules.sys.shiro.ShiroUtils;
import com.ixiangliu.modules.test.entity.Question;
import com.ixiangliu.modules.test.service.IQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * 问答
 */
@Slf4j
@RestController
@RequestMapping("test/question")
public class QuestionController {
    @Autowired
    private IQuestionService iQuestionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("test:question:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = iQuestionService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("test:question:info")
    public Result info(@PathVariable("id") Long id){
        Question question = iQuestionService.getById(id);
        return Result.ok().put("question", question);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("test:question:save")
    public Result save(@RequestBody Question question){
        //校验类型
        ValidatorUtils.validateEntity(question);
        question.setUpdateTime(new Date());
        question.setUserId(ShiroUtils.getUser().getId());
        iQuestionService.save(question);
        return Result.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("test:question:update")
    public Result update(@RequestBody Question question){
        //校验类型
        ValidatorUtils.validateEntity(question);
        question.setUpdateTime(new Date());
        question.setUserId(ShiroUtils.getUser().getId());
        iQuestionService.updateById(question);
        return Result.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("test:question:delete")
    public Result delete(@RequestBody Long[] ids){
        iQuestionService.removeByIds(Arrays.asList(ids));
        return Result.ok();
    }

}
