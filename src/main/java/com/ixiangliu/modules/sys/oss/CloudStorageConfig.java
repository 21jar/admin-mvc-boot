package com.ixiangliu.modules.sys.oss;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 云存储配置信息
 */
@Data
public class CloudStorageConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 类型2：阿里云  3：腾讯云
     */
    @Range(min=1, max=3, message = "类型错误")
    private Integer type;

    @NotBlank(message="阿里云绑定的域名不能为空")
    @URL(message = "阿里云绑定的域名格式不正确")
    private String aliyunDomain;
    private String aliyunPrefix;
    @NotBlank(message="阿里云EndPoint不能为空")
    private String aliyunEndPoint;
    @NotBlank(message="阿里云AccessKeyId不能为空")
    private String aliyunAccessKeyId;
    @NotBlank(message="阿里云AccessKeySecret不能为空")
    private String aliyunAccessKeySecret;
    @NotBlank(message="阿里云BucketName不能为空")
    private String aliyunBucketName;
}
