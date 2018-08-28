package com.mujugroup.core.model;


import java.util.Date;
import java.io.Serializable;
import javax.persistence.*;

/**
 * 
 * 类名:Agent
 * 创建人:leolaurel
 * 创建时间:20180828
 */
@SuppressWarnings("serial")
@Table(name = "t_sys_manager")
public class Agent implements Serializable {

    /**
     * 
     * 表字段 : t_sys_manager.managerid
     */
    @Column(name = "managerid")
    private Integer managerid;

    /**
     * 账号
     * 表字段 : t_sys_manager.account
     */
    @Column(name = "account")
    private String account;

    /**
     * 
     * 表字段 : t_sys_manager.password
     */
    @Column(name = "password")
    private String password;

    /**
     * 
     * 表字段 : t_sys_manager.role
     */
    @Column(name = "role")
    private Integer role;

    /**
     * 
     * 表字段 : t_sys_manager.name
     */
    @Column(name = "name")
    private String name;

    /**
     * 登陆IP
     * 表字段 : t_sys_manager.lastloginip
     */
    @Column(name = "lastloginip")
    private String lastloginip;

    /**
     * 最后一次登陆时间
     * 表字段 : t_sys_manager.lastlogintime
     */
    @Column(name = "lastlogintime")
    private Date lastlogintime;

    /**
     * 验证凭证
     * 表字段 : t_sys_manager.credentialsSalt
     */
    @Column(name = "credentialsSalt")
    private String credentialsSalt;

    /**
     * 是否启用 0=禁用 1=启用
     * 表字段 : t_sys_manager.locked
     */
    @Column(name = "locked")
    private String locked;

    /**
     * 
     * 表字段 : t_sys_manager.email
     */
    @Column(name = "email")
    private String email;

    /**
     * 
     * 表字段 : t_sys_manager.phone
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 
     * 表字段 : t_sys_manager.sex
     */
    @Column(name = "sex")
    private String sex;

    /**
     * 
     * 表字段 : t_sys_manager.type
     */
    @Column(name = "type")
    private String type;

    /**
     * 
     * 表字段 : t_sys_manager.photo
     */
    @Column(name = "photo")
    private String photo;

    /**
     * 
     * 表字段 : t_sys_manager.crt_id
     */
    @Column(name = "crt_id")
    private Integer crtId;

    /**
     * 
     * 表字段 : t_sys_manager.crt_time
     */
    @Column(name = "crt_time")
    private Date crtTime;

    /**
     * 医院id
     * 表字段 : t_sys_manager.hospitalId
     */
    @Column(name = "hospitalId")
    private Integer hospitalId;

    /**
     * 
     * 表字段 : t_sys_manager.countryId
     */
    @Column(name = "countryId")
    private Integer countryId;

    /**
     * 
     * 表字段 : t_sys_manager.proId
     */
    @Column(name = "proId")
    private Integer proId;

    /**
     * 
     * 表字段 : t_sys_manager.cityId
     */
    @Column(name = "cityId")
    private Integer cityId;



    public Integer getManagerid() {
		return managerid;
	}

	public void setManagerid(Integer managerid) {
		this.managerid = managerid;
	}

    public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public String getLastloginip() {
		return lastloginip;
	}

	public void setLastloginip(String lastloginip) {
		this.lastloginip = lastloginip;
	}

    public Date getLastlogintime() {
		return lastlogintime;
	}

	public void setLastlogintime(Date lastlogintime) {
		this.lastlogintime = lastlogintime;
	}

    public String getCredentialsSalt() {
		return credentialsSalt;
	}

	public void setCredentialsSalt(String credentialsSalt) {
		this.credentialsSalt = credentialsSalt;
	}

    public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

    public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

    public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

    public Integer getCrtId() {
		return crtId;
	}

	public void setCrtId(Integer crtId) {
		this.crtId = crtId;
	}

    public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

    public Integer getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(Integer hospitalId) {
		this.hospitalId = hospitalId;
	}

    public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

    public Integer getProId() {
		return proId;
	}

	public void setProId(Integer proId) {
		this.proId = proId;
	}

    public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

}