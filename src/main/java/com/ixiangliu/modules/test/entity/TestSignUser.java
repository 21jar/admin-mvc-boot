package com.ixiangliu.modules.test.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("test_sign_user")
public class TestSignUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private Long id;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date date;
	private Long no;
	private String deptName;
	@NotBlank(message="姓名不能为空")
	private String name;
	private String official;
	private String retail;
	private String phone;
	private String floor;
	private Float temperatureStart;
	private Float temperatureEnd;
}
