package com.ixiangliu.modules.sys.controller;

import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.common.utils.Result;
import com.ixiangliu.modules.sys.entity.Dict;
import com.ixiangliu.modules.sys.service.IDictService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 数据字典
 */
@RestController
@RequestMapping("sys/dict")
public class DictController {
    @Autowired
    private IDictService iDictService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:dict:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = iDictService.queryPage(params);
        return Result.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:dict:info")
    public Result info(@PathVariable("id") Long id){
        Dict dict = iDictService.getById(id);
        return Result.ok().put("dict", dict);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:dict:save")
    public Result save(@RequestBody Dict dict){
        //校验类型
//        ValidatorUtils.validateEntity(dict);
        iDictService.save(dict);
        return Result.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:dict:update")
    public Result update(@RequestBody Dict dict){
        //校验类型
//        ValidatorUtils.validateEntity(dict);
        iDictService.updateById(dict);
        return Result.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:dict:delete")
    public Result delete(@RequestBody Long[] ids){
        iDictService.removeByIds(Arrays.asList(ids));
        return Result.ok();
    }

}
