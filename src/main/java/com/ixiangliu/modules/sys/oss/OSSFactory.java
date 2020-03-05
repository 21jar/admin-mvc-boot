package com.ixiangliu.modules.sys.oss;

import com.ixiangliu.common.constant.Const;
import com.ixiangliu.common.utils.SpringContextUtils;
import com.ixiangliu.modules.sys.service.IConfigService;

/**
 * 文件上传Factory
 */
public final class OSSFactory {
    private static IConfigService iConfigService;

    static {
        OSSFactory.iConfigService = (IConfigService) SpringContextUtils.getBean("configService");
    }

    public static CloudStorageService build(){
        //获取云存储配置信息
        CloudStorageConfig config = iConfigService.getConfigObject(Const.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig.class);
        if(config.getType() == Const.CloudService.ALIYUN.getValue()){
            return new AliyunCloudStorageService(config);
        }
        return null;
    }

}
