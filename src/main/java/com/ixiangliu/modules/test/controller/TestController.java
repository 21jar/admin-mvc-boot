package com.ixiangliu.modules.test.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ixiangliu.common.excel.ExcelListener;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    /**
     * 导入
     */
    @PostMapping("/signUser/upload")
    public Result upload(MultipartFile file) {
        //实例化实现了AnalysisEventListener接口的类
        ExcelListener listener = new ExcelListener();
        try{
            //传入参数
            EasyExcel.read(file.getInputStream(), TestSignUser.class, listener).sheet().doRead();
            //获取数据
            List<TestSignUser> list = listener.getDataList();
            iTestSignUserService.saveBatch(list);
        } catch (Exception e) {
            log.error("exception:", e);
            return Result.error("上传失败");
        }
        return Result.ok();
    }

    /**
     * 导出
     */
    @RequestMapping("/signUser/download")
    public void download(HttpServletResponse response, @RequestParam Map<String, Object> params) throws IOException {
        String name = (String)params.get("name");
        String date = (String)params.get("date");
        List<TestSignUser> testSignUsers = iTestSignUserService.list(new QueryWrapper<TestSignUser>().like(StringUtils.isNotBlank(name),"name", name).eq(StringUtils.isNotBlank(date),"date", date));

        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode(date + "进入分行名单", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), TestSignUser.class).sheet("sheet1").doWrite(testSignUsers);
    }

}
