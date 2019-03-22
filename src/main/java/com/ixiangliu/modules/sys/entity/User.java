package com.ixiangliu.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("sys_user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
//    /** 乐观锁 */
//    private Integer revision ;
//    /** 创建人 */
//    private String createdBy ;
//    /** 创建时间 */
//    private Date createdTime ;
//    /** 更新人 */
//    private String updatedBy ;
//    /** 更新时间 */
//    private Date updatedTime ;
//    /** 编号 */
//    @TableId(value = "id", type = IdType.AUTO)
//    private Long id ;
//    /** 姓名 */
//    private String name ;
//    /** 密码 */
//    private String password ;
//    /** 登录名 */
//    private String loginName ;
//    /** 删除标记 */
//    @TableLogic
//    private String delFlag ;
//    /** 备注 */
//    private String remarks ;

    @TableId
    private Long id;
    @NotBlank(message="用户名不能为空")
    private String username;
    @NotBlank(message="密码不能为空")
    private String password;
    private String salt;
    @NotBlank(message="邮箱不能为空")
    @Email(message="邮箱格式不正确")
    private String email;
    private String mobile;
    private Integer status;
    @TableField(exist=false)
    private List<Long> roleIdList;
    private Date createTime;
    @NotNull(message="部门不能为空")
    private Long deptId;
    @TableField(exist=false)
    private String deptName;
}
