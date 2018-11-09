package com.lveqia.cloud.common.objeck.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@EqualsAndHashCode(callSuper = true)
public class AuthVo extends PageVo {

    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty(value = "uid", notes = "用户ID")
    private Integer uid = 1;


    @NotBlank(message = "查询数据级别不能为空")
    @ApiModelProperty(value = "level", notes = "查询数据权限的级别")
    private String level;
}
