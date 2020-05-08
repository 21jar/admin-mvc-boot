package com.ixiangliu.modules.wechat.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信错误信息类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResult {

    private static final long serialVersionUID = 1L;
    @JsonProperty("errcode")
    @JSONField(name = "errcode")
    private String errCode;
    @JsonProperty("errmsg")
    @JSONField(name = "errmsg")
    private String errMsg;
}
