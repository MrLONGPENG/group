package com.lveqia.cloud.zuul.objeck.vo;


import com.lveqia.cloud.zuul.model.SysRole;
import com.lveqia.cloud.zuul.model.SysUser;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * 类名:UserVo
 * 创建人:leolaurel
 * 创建时间:20180725
 */

public class UserVo implements Serializable {

    private Integer id;
    private String name;
    private String phone;
    private String telephone;
    private String address;
    private String username;
    private String avatarUrl;
    private String remark;
    private Boolean enabled;
	private Integer crtId;
	private Date crtTime;
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	public Integer getCrtId() {
		return crtId;
	}

	public void setCrtId(Integer crtId) {
		this.crtId = crtId;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	private List<SysRole> roles;
	private List<UserVo> children;

	public List<UserVo> getChildren() {
		return children;
	}

	public void setChildren(List<UserVo> children) {
		this.children = children;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}

	public void setSysUser(SysUser sysUser) {
		setId(sysUser.getId());
		setName(sysUser.getName());
		setPhone(sysUser.getPhone());
		setEnabled(sysUser.isEnabled());
		setAddress(sysUser.getAddress());
		setAvatarUrl(sysUser.getAvatarUrl());
		setRemark(sysUser.getRemark());
		setRoles(sysUser.getRoles());
		setUsername(sysUser.getUsername());
		setCrtId(sysUser.getCrtId());
		setCrtTime(sysUser.getCrtTime());
	}
}