package com.ixiangliu.modules.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.ixiangliu.common.constant.Const;
import com.ixiangliu.common.exception.BizException;
import com.ixiangliu.common.utils.PageUtils;
import com.ixiangliu.common.utils.Result;
import com.ixiangliu.common.validator.ValidatorUtils;
import com.ixiangliu.modules.sys.entity.Oss;
import com.ixiangliu.modules.sys.oss.CloudStorageConfig;
import com.ixiangliu.modules.sys.oss.CloudStorageService;
import com.ixiangliu.modules.sys.oss.OSSFactory;
import com.ixiangliu.modules.sys.service.IConfigService;
import com.ixiangliu.modules.sys.service.IOssService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

/**
 * 文件上传
 */
@Slf4j
@RestController
@RequestMapping("sys/oss")
public class OssController {

    // 本地存储位置
    @Value("${web.upload-path}")
    private String webUploadPath;

    // 静态资源文件映射路径
    @Value("${spring.mvc.static-path-pattern}")
    private String staticPathPattern;

    @Autowired
    private IOssService ossService;

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
        CloudStorageConfig config = iConfigService.getConfigObject(Const.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig.class);
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
        iConfigService.updateValueByKey(Const.CLOUD_STORAGE_CONFIG_KEY, JSONObject.toJSONString(config));
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

        String url = null;
        //获取云存储配置信息
        CloudStorageConfig config = iConfigService.getConfigObject(Const.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig.class);
        if(config.getType() == Const.CloudService.LOCAL.getValue()){
            //上传文件
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            url= CloudStorageService.getPath("", suffix);
            File dest = new File(webUploadPath + url);
            //判断文件父目录是否存在
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdir();
            }
            file.transferTo(dest);
            url = (config.getLocalDomain() == null ? "" : config.getLocalDomain()) + staticPathPattern.replace("**","") +url;
        }else {
            //上传文件
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);
        }
        //保存文件信息
        Oss ossEntity = new Oss();
        ossEntity.setUrl(url);
        ossEntity.setCreateDate(new Date());
        ossService.save(ossEntity);
        return Result.ok().put("url", url);
    }

    /**
     * 上传多个文件
     */
    @PostMapping("/uploadList")
    @RequiresPermissions("sys:oss:all")
    public Result uploadList(List<MultipartFile> files) throws Exception {
        if (CollectionUtils.isEmpty(files)) {
            Result.error("上传文件不能为空");
        }

        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            String url = null;
            //获取云存储配置信息
            CloudStorageConfig config = iConfigService.getConfigObject(Const.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig.class);
            if(config.getType() == Const.CloudService.LOCAL.getValue()){
                //上传文件
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                url= CloudStorageService.getPath("", suffix);
                File dest = new File(webUploadPath + url);
                //判断文件父目录是否存在
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdir();
                }
                file.transferTo(dest);
                url = (config.getLocalDomain() == null ? "" : config.getLocalDomain()) + staticPathPattern.replace("**","") +url;
            }else {
                //上传文件
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);
            }
            //保存文件信息
            Oss ossEntity = new Oss();
            ossEntity.setUrl(url);
            ossEntity.setCreateDate(new Date());
            ossService.save(ossEntity);
            urls.add(url);
        }
        return Result.ok().put("data", urls).put("errno", 0);
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
