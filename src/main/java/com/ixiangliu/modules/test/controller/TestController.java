package com.ixiangliu.modules.test.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ixiangliu.common.utils.DateUtil;
import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.common.utils.Result;
import com.ixiangliu.modules.test.entity.TestSignLog;
import com.ixiangliu.modules.test.entity.TestSignUser;
import com.ixiangliu.modules.test.service.ITestSignLogService;
import com.ixiangliu.modules.test.service.ITestSignUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * 数据字典
 */
@Slf4j
@RestController
@RequestMapping("test")
public class TestController {
    @Autowired
    private ITestSignUserService iTestSignUserService;
    @Autowired
    private ITestSignLogService iTestSignLogService;

    /**
     * 列表
     */
    @RequestMapping("/sign")
    public Result sign(@RequestParam Map<String, Object> params){
        if (params == null || StringUtils.isBlank((String)params.get("name"))|| StringUtils.isBlank((String)params.get("temperature"))) {
            return Result.error("请输入姓名和温度");
        }
        Float temperature = Float.parseFloat((String)params.get("temperature"));
        TestSignUser testSignUser = iTestSignUserService.getOne(new QueryWrapper<TestSignUser>().eq("name", (String)params.get("name")).eq("date",DateUtil.formatDate(new Date(), DateUtil.YYYY_MM_DD)));
        if (testSignUser == null) {
            return Result.error("未找到您的信息");
        }
        // 更新温度
        if (testSignUser.getTemperatureStart() == null) {
            testSignUser.setTemperatureStart(temperature);
        } else {
            testSignUser.setTemperatureEnd(temperature);
        }
        iTestSignUserService.updateById(testSignUser);
        // 新增log记录
        TestSignLog testSignLog = new TestSignLog();
        testSignLog.setUserId(testSignUser.getId());
        testSignLog.setDate(new Date());
        testSignLog.setTemperature(temperature);
        iTestSignLogService.save(testSignLog);
        // 体温报警
        if (temperature >= 37.3F) {
            return Result.error("您的体温高于37.3");
        }
        return Result.ok("已通过，时间："+ DateUtil.formatDate(new Date(), DateUtil.HH_MM_SS));
    }

    /**
     * 列表
     */
    @RequestMapping("/signUser/list")
    @RequiresPermissions("test:signUser:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = iTestSignUserService.queryPage(params);
        return Result.ok().put("page", page);
    }

}
