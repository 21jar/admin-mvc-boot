package com.ixiangliu.modules.spider.controller;

import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.common.utils.Result;
import com.ixiangliu.common.validator.ValidatorUtils;
import com.ixiangliu.modules.spider.entity.SpiderResult;
import com.ixiangliu.modules.spider.service.ISpiderResultService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
    @RequestMapping("/updateOrder")
    public Result updateOrder(@RequestBody Map<String, Object> params){
        String keyword = (String) params.get("keyword");
        if (StringUtils.isBlank(keyword)){
            keyword="XpShopMemberAuth="+"290AFCDA02BEF11E36F174210411D57C9232ADC9DC40D2F773AB12C38B270C8D64CFFDE0A15361EE3DF0BD9186C6805E9240C145A7B859743F19CA98E833A9AB7D52FC8156D53B045DA735E7B5CA97F3E4F93311FC799CAAA91503A43F331E288E959A64B257E4F0446C051161A63C14645EBE9E4D14CCB77085AD6F5BB363B790493BFCE082E7F7A7EC8234CF66A3B6";
        } else {
            keyword="XpShopMemberAuth="+keyword;
        }
        boolean flag = iSpiderResultService.updateOrder(keyword);
        if (flag) {
            return Result.ok("订单数据保存成功");
        }else {
            return Result.error("订单数据保存失败");
        }
    }

    @RequestMapping("/delete")
    public Result delete(@RequestBody Long[] ids){
        iSpiderResultService.removeByIds(Arrays.asList(ids));
        return Result.ok();
    }
}
