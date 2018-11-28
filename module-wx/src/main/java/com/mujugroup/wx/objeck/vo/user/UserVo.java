package com.mujugroup.wx.objeck.vo.user;

import lombok.Data;

@Data
public class UserVo {

    private String phone;
    private String nickName;
    private Integer gender;
    private String language;
    private String country;
    private String province;
    private String city;
    private String avatarUrl;
    private Integer price;
    private Integer refund;
}
