package com.lveqia.cloud.zuul.objeck.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@ApiModel(value = "AddVo", description = "系统用户或运营账号添加VO")
public class AddVo implements Serializable {

    @NotNull(message = "账号类型不能为空")
    @ApiModelProperty(value = "账号类型 0:系统账号 1：普通账号", required = true)
    private Integer type;

    @NotBlank(message = "登陆账号不能为空")
    @ApiModelProperty(value = "登陆账号", required = true)
    private String username;

    @NotBlank(message = "姓名(昵称)不能为空")
    @ApiModelProperty(value = "姓名(昵称)", required = true)
    private String name;

    @NotBlank(message = "手机号码不能为空")
    @Pattern(regexp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$", message = "手机号格式错误")
    @ApiModelProperty(value = "手机号(可登陆)", required = true)
    private String phone;

    @Email(message = "邮箱地址格式错误")
    @NotBlank(message = "邮箱地址不能为空")
    @ApiModelProperty(value = "电子邮箱(找密码)", required = true)
    private String email;

    @NotBlank(message = "账号密码不能为空")
    @ApiModelProperty(value = "账户密码", required = true)
    private String password;

    @ApiModelProperty(value = "家庭住址")
    private String address;

    @ApiModelProperty(value = "头像地址")
    private String avatarUrl;

    @ApiModelProperty(value = "账户备注")
    private String remark;

    @ApiModelProperty(value = "角色组", notes = "授予账户的角色ID集合")
    private int[] roles;

    @ApiModelProperty(value = "数据权限", notes = "授予账户的数据权限集合")
    private String[] authData;
}
