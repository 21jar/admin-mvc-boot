package com.ixiangliu.modules.test.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ixiangliu.common.utils.DateUtil;
import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.common.utils.Result;
import com.ixiangliu.modules.sys.entity.Dict;
import com.ixiangliu.modules.sys.service.IDictService;
import com.ixiangliu.modules.test.entity.TestSignUser;
import com.ixiangliu.modules.test.service.ITestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
    private ITestService iTestService;

    /**
     * 列表
     */
    @RequestMapping("/sign")
    public Result list(@RequestParam Map<String, Object> params){
        if (params == null || StringUtils.isBlank((String)params.get("name"))|| StringUtils.isBlank((String)params.get("temperature"))) {
            return Result.error("请输入姓名和温度");
        }
        Float temperature = Float.parseFloat((String)params.get("temperature"));
        TestSignUser testSignUser = iTestService.getOne(new QueryWrapper<TestSignUser>().eq("name", (String)params.get("name")));
        if (testSignUser == null) {
            return Result.error("未找到您的信息");
        }

        if (temperature >= 37.3F) {
            return Result.error("您的体温高于37.3");
        }
        return Result.ok("已通过，时间："+ DateUtil.formatDate(new Date(), DateUtil.HH_MM_SS));
    }

}
