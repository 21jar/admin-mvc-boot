package com.ixiangliu.modules.test.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
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
	@ExcelIgnore
	private Long id;
	@ExcelIgnore
	private Long userId;
	@TableField(exist=false)
	@ExcelProperty(value = "姓名", index = 0)
	private String userName;
	@ExcelProperty(value = "体温", index = 2)
	private Float temperature;
	@ExcelProperty(value = "日期", index = 1)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date date;
	@ExcelIgnore
	private String remark;
}
