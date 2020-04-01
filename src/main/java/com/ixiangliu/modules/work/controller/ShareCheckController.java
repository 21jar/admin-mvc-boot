package com.ixiangliu.modules.work.controller;

import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.common.utils.Result;
import com.ixiangliu.common.validator.ValidatorUtils;
import com.ixiangliu.modules.work.entity.ShareCheck;
import com.ixiangliu.modules.work.service.IShareCheckService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 *
 */
@Slf4j
@RestController
@RequestMapping("work/shareCheck")
public class ShareCheckController {
    @Autowired
    private IShareCheckService iShareCheckService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("work:shareCheck:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = iShareCheckService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("work:shareCheck:info")
    public Result info(@PathVariable("id") Long id){
        ShareCheck shareCheck = iShareCheckService.getById(id);
        return Result.ok().put("shareCheck", shareCheck);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("work:shareCheck:save")
    public Result save(@RequestBody ShareCheck shareCheck){
        //校验类型
        ValidatorUtils.validateEntity(shareCheck);
        iShareCheckService.save(shareCheck);
        return Result.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("work:shareCheck:update")
    public Result update(@RequestBody ShareCheck shareCheck){
        //校验类型
        ValidatorUtils.validateEntity(shareCheck);
        iShareCheckService.updateById(shareCheck);
        return Result.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("work:shareCheck:delete")
    public Result delete(@RequestBody Long[] ids){
        iShareCheckService.removeByIds(Arrays.asList(ids));
        return Result.ok();
    }

}
