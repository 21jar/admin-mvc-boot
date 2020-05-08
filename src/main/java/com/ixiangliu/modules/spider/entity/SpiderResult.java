package com.ixiangliu.modules.spider.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 *
 */
@Data
@TableName("spider_result")
public class SpiderResult implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private Long id;
	private String type;
	private String detail;
	private String title;
	private String paramOne;
	private String paramTwo;
	private String paramThree;
	private String paramId;
	private float orderNum;

}
