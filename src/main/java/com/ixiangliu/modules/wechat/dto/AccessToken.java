package com.ixiangliu.modules.wechat.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * accessToken对象
 */
@Data
public class AccessToken extends BaseResult {
    private static final long serialVersionUID = 1L;

    //accessToken最多生存100分钟必须刷新，单位ms
    public static long MAX_LIVE_TIME = 6000000;
    //缓存时长100分钟，单位s
    public static long EXPIRE_TIME = 6000;

    @JsonProperty("access_token")
    @JSONField(name = "access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    @JSONField(name = "expires_in")
    private int expiresIn;
    //上次刷新时间
    private long lastRefreshTime;

    public AccessToken(String accessToken, int expiresIn) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.lastRefreshTime = System.currentTimeMillis();
    }

    public AccessToken(String accessToken, int expiresIn, long lastRefreshTime) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.lastRefreshTime = lastRefreshTime;
    }

    public AccessToken() {
    }
}
