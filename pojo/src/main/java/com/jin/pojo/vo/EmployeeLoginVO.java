package com.jin.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;  // OpenAPI 3 注解
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "员工登录返回的数据格式")  // 替代 @ApiModel
public class EmployeeLoginVO implements Serializable {

    @Schema(description = "主键值")  // 替代 @ApiModelProperty
    private Long id;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "jwt令牌")
    private String token;
}