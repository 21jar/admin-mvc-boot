package com.ixiangliu.modules.test.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("test_sign_log")
public class TestSignLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private Long id;
	private Long userId;
	private Float temperature;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date date;
	private String remark;
}
