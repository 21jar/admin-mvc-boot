package com.ixiangliu.modules.work.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 *
 */
@Data
@TableName("work_share_check")
public class ShareCheck implements Serializable {
	private static final long serialVersionUID = 1L;
	@TableId
	private Long id;
	private String eventId;
	private String eventType;
	private String eventTime;
	private String itName;
	private String ip;
	private String um;
	private String fileName;
	private String fileUrl;
	private String eventNum;
	private String eventSize;
	private String fileType;
	private String fileRealType;
	private String proofNum;
	private String systemUrl;
	private String pattern;
	private String type;
	private String role;
	private String exist;
	private String clear;
	private String reason;
	private String remark;

	@TableField(exist=false)
	private String deptName;

}
