package com.lveqia.cloud.zuul.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 * 
 * 类名:SysUser
 * 创建人:leolaurel
 * 创建时间:20180725
 */
@SuppressWarnings("serial")
@Table(name = "t_sys_user")
public class SysUser implements UserDetails {

    /**
     * 主键
     * UID
     * 表字段 : t_sys_user.id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 姓名
     * 表字段 : t_sys_user.name
     */
    @Column(name = "name")
    private String name;

    /**
     * 手机号码
     * 表字段 : t_sys_user.phone
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 住宅电话
     * 表字段 : t_sys_user.telephone
     */
    @Column(name = "telephone")
    private String telephone;

    /**
     * 联系地址
     * 表字段 : t_sys_user.address
     */
    @Column(name = "address")
    private String address;

    /**
     * 
     * 表字段 : t_sys_user.enabled
     */
    @Column(name = "enabled")
    private Boolean enabled;

    /**
     * 用户名
     * 表字段 : t_sys_user.username
     */
    @Column(name = "username")
    private String username;

    /**
     * 密码
     * 表字段 : t_sys_user.password
     */
    @Column(name = "password")
    private String password;

    /**
     * 
     * 表字段 : t_sys_user.avatar_url
     */
    @Column(name = "avatar_url")
    private String avatarUrl;

    /**
     * 
     * 表字段 : t_sys_user.remark
     */
    @Column(name = "remark")
    private String remark;

	private List<SysRole> roles;

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
		for (SysRole role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
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

	public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}
}