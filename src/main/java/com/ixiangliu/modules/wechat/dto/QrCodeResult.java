package com.ixiangliu.modules.wechat.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创建带参二维码结果
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QrCodeResult extends BaseResult {

    private String ticket;

    @JsonProperty("expire_seconds")
    @JSONField(name = "expire_seconds")
    private String expireSeconds;

    private String url;
}