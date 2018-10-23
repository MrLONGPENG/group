package com.lveqia.cloud.zuul.objeck.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value = "UserAddVo", description = "系统用户或运营账号添加VO")
public class UserAddVo implements Serializable {

    @ApiModelProperty(value = "账号类型 0:系统账号 1：普通账号", required = true)
    private Integer type;

    @ApiModelProperty(value = "登陆账号", required = true)
    private String username;

    @ApiModelProperty(value = "姓名(昵称)", required = true)
    private String name;

    @ApiModelProperty(value = "手机号(可登陆)", required = true)
    private String phone;

    @ApiModelProperty(value = "电子邮箱(找密码)", required = true)
    private String email;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int[] getRoles() {
        return roles;
    }

    public void setRoles(int[] roles) {
        this.roles = roles;
    }

    public String[] getAuthData() {
        return authData;
    }

    public void setAuthData(String[] authData) {
        this.authData = authData;
    }
}
