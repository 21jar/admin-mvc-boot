package com.ixiangliu.modules.sys.controller;

import com.ixiangliu.common.utils.RedisKeys;
import com.ixiangliu.common.utils.RedisUtils;
import com.ixiangliu.modules.sys.entity.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 系统配置Redis
 */
@Component
public class ConfigRedis {
    @Autowired
    private RedisUtils redisUtils;

    public void saveOrUpdate(Config config) {
        if(config == null){
            return ;
        }
        String key = RedisKeys.getSysConfigKey(config.getParamKey());
        redisUtils.set(key, config);
    }

    public void delete(String configKey) {
        String key = RedisKeys.getSysConfigKey(configKey);
        redisUtils.delete(key);
    }

    public Config get(String configKey){
        String key = RedisKeys.getSysConfigKey(configKey);
        return redisUtils.get(key, Config.class);
    }
}
