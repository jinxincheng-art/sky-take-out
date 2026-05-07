package com.jin.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;  // OpenAPI 3 注解
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "员工登录时传递的数据模型")  // 替代 @ApiModel
public class EmployeeLoginDTO implements Serializable {

    @Schema(description = "用户名")  // 替代 @ApiModelProperty
    private String username;

    @Schema(description = "密码")
    private String password;
}