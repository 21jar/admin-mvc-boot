package com.ixiangliu.modules.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.ixiangliu.modules.sys.oss.CloudStorageConfig;
import com.ixiangliu.modules.sys.oss.OSSFactory;
import com.ixiangliu.common.constant.Const;
import com.ixiangliu.common.exception.BizException;
import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.common.utils.Result;
import com.ixiangliu.common.validator.ValidatorUtils;
import com.ixiangliu.modules.sys.entity.Oss;
import com.ixiangliu.modules.sys.service.IConfigService;
import com.ixiangliu.modules.sys.service.IOssService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * 文件上传
 */
@Slf4j
@RestController
@RequestMapping("sys/oss")
public class OssController {
    @Autowired
    private IOssService ossService;

    private final static String KEY = Const.CLOUD_STORAGE_CONFIG_KEY;

    @Autowired
    private IConfigService iConfigService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:oss:all")
    public Result list(@RequestParam Map<String, Object> params) {
        PageUtils page = ossService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 云存储配置信息
     */
    @RequestMapping("/config")
    @RequiresPermissions("sys:oss:all")
    public Result config() {
        CloudStorageConfig config = iConfigService.getConfigObject(KEY, CloudStorageConfig.class);
        return Result.ok().put("config", config);
    }

    /**
     * 保存云存储配置信息
     */
    @PostMapping("/saveConfig")
    @RequiresPermissions("sys:oss:all")
    public Result saveConfig(@RequestBody CloudStorageConfig config) {
        //校验类型
        ValidatorUtils.validateEntity(config);
        if (config.getType() == Const.CloudService.ALIYUN.getValue()) {
            //校验阿里云数据
            ValidatorUtils.validateEntity(config);
        }
        iConfigService.updateValueByKey(KEY, JSONObject.toJSONString(config));
        return Result.ok();
    }

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    @RequiresPermissions("sys:oss:all")
    public Result upload(MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new BizException("上传文件不能为空");
        }
        //上传文件
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);

        //保存文件信息
        Oss ossEntity = new Oss();
        ossEntity.setUrl(url);
        ossEntity.setCreateDate(new Date());
        ossService.save(ossEntity);

        return Result.ok().put("url", url);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:oss:all")
    public Result delete(@RequestBody Long[] ids) {
        ossService.removeByIds(Arrays.asList(ids));
        return Result.ok();
    }

}
