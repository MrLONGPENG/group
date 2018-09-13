package com.lveqia.cloud.common.objeck.info;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 
 * 类名:UserInfo
 * 创建人:leolaurel
 * 创建时间:20180725
 * 用户信息
 */
public class UserInfo implements UserDetails {

    private Integer id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private Boolean enabled;
    private String username;
    private String password;
    private String avatarUrl;
    private String remark;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public boolean isEnabled() {
		return enabled == null ? false: enabled;
	}

	@Override
    public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
//		for (SysRole role : roles) {
//			authorities.add(new SimpleGrantedAuthority(role.getName()));
//		}
		return authorities;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
}