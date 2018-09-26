package com.lveqia.cloud.common.objeck.info;

import java.util.Date;
import java.util.List;

public class UserInfo {

    private long id;
    private String tag;  // 安卓、苹果登陆为app;网页登陆为vue
    private String name;
    private String username;
    private String token;
    private Date expiration;
    private List<?> roleInfo;

    public UserInfo(String username) {
        this.username = username;
    }


    public UserInfo(Integer id, String name, String username) {
        this.id = id;
        this.name = name;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public List<?> getRoleInfo() {
        return roleInfo;
    }

    public void setRoleInfo(List<?> roleInfo) {
        this.roleInfo = roleInfo;
    }
}
