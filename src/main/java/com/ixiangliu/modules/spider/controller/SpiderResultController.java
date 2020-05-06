package com.ixiangliu.modules.spider.controller;

import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.common.utils.Result;
import com.ixiangliu.common.validator.ValidatorUtils;
import com.ixiangliu.modules.spider.entity.SpiderResult;
import com.ixiangliu.modules.spider.service.ISpiderResultService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@Slf4j
@RestController
@RequestMapping("spider/spiderResult")
public class SpiderResultController {
    @Autowired
    private ISpiderResultService iSpiderResultService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("spider:spiderResult:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = iSpiderResultService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("spider:spiderResult:info")
    public Result info(@PathVariable("id") Long id){
        SpiderResult spiderResult = iSpiderResultService.getById(id);
        return Result.ok().put("spiderResult", spiderResult);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("spider:spiderResult:update")
    public Result update(@RequestBody SpiderResult spiderResult){
        //校验类型
        ValidatorUtils.validateEntity(spiderResult);
        iSpiderResultService.updateById(spiderResult);
        return Result.ok();
    }

}
