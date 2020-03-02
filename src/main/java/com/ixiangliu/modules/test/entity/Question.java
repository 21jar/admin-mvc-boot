package com.ixiangliu.modules.test.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 问答
 */
@Data
@TableName("test_question")
public class Question implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private Long id;
	/**
	 * 标题
	 */
	@NotBlank(message="标题不能为空")
	private String title;
	/**
	 * 类型
	 */
	@NotBlank(message="类型不能为空")
	private String type;
	/**
	 * 最新修改人
	 */
	private Long userId;
	/**
	 * 最新修改人
	 */
	@TableField(exist=false)
	private String userName;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateTime;
	/**
	 * 排序
	 */
	private Integer orderNum;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 删除标记  -1：已删除  0：正常
	 */
	@TableLogic
	private Integer delFlag;

}
