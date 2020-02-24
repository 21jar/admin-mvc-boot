package com.ixiangliu.modules.test.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;
import org.apache.poi.ss.usermodel.CellStyle;

import javax.validation.constraints.NotBlank;
import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@TableName("test_sign_user")
public class TestSignUser extends BaseRowModel implements Serializable  {
	private static final long serialVersionUID = 1L;

	@TableId
	@ExcelIgnore
	private Long id;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@ExcelProperty(value = "日期", index = 0)
	private Date date;
	@ExcelProperty(value = "序号", index = 1)
	private Long no;
	@ExcelProperty(value = "部门", index = 2)
	private String deptName;
	@NotBlank(message="姓名不能为空")
	@ExcelProperty(value = "姓名", index = 3)
	private String name;
	@ExcelProperty(value = "正编外包", index = 4)
	private String official;
	@ExcelProperty(value = "零售非零", index = 5)
	private String retail;
	@ExcelProperty(value = "手机号码", index = 6)
	private String phone;
	@ExcelProperty(value = "楼层", index = 7)
	private String floor;
	@ExcelProperty(value = "上班体温", index = 8)
	private Float temperatureStart;
	@ExcelProperty(value = "下班体温", index = 9)
	private Float temperatureEnd;

	@TableField(exist=false)
	@ExcelIgnore
	private Map<Integer, CellStyle> cellStyleMap = new HashMap();

}
