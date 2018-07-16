package com.mujugroup.core.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 医院信息表
 * 类名:Hospital
 * 创建人:LEOLAUREL
 * 创建时间:20180627
 */
@SuppressWarnings("serial")
@Table(name = "t_hospital")
public class Hospital implements Serializable {

    /**
     * 主键
     * 
     * 表字段 : t_hospital.id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 名称
     * 表字段 : t_hospital.name
     */
    @Column(name = "name")
    private String name;

    /**
     * 代理商id
     * 表字段 : t_hospital.agentId
     */
    @Column(name = "agentId")
    private String agentId;

    /**
     * 电话
     * 表字段 : t_hospital.tel
     */
    @Column(name = "tel")
    private String tel;

    /**
     * 联系人
     * 表字段 : t_hospital.person
     */
    @Column(name = "person")
    private String person;

    /**
     * 备注
     * 表字段 : t_hospital.remark
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 创建时间
     * 表字段 : t_hospital.crtTime
     */
    @Column(name = "crtTime")
    private Date crtTime;

    /**
     * 创建id
     * 表字段 : t_hospital.crtId
     */
    @Column(name = "crtId")
    private Integer crtId;

    /**
     * 
     * 表字段 : t_hospital.address
     */
    @Column(name = "address")
    private String address;

    /**
     * 
     * 表字段 : t_hospital.country
     */
    @Column(name = "country")
    private Integer country;

    /**
     * 
     * 表字段 : t_hospital.province
     */
    @Column(name = "province")
    private Integer province;

    /**
     * 
     * 表字段 : t_hospital.city
     */
    @Column(name = "city")
    private Integer city;

    /**
     * 经度
     * 表字段 : t_hospital.longitude
     */
    @Column(name = "longitude")
    private Double longitude;

    /**
     * 纬度
     * 表字段 : t_hospital.latitude
     */
    @Column(name = "latitude")
    private Double latitude;

    /**
     * 医院状态 22 启用 23 禁用 17 删除
     * 表字段 : t_hospital.enable
     */
    @Column(name = "enable")
    private Integer enable;

    /**
     * 是否与子服务器同步，0：已同步，1：未同步
     * 表字段 : t_hospital.issync
     */
    @Column(name = "issync")
    private Integer issync;

    /**
     * 医院等级
     * 表字段 : t_hospital.level
     */
    @Column(name = "level")
    private String level;



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

    public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

    public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

    public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

    public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

    public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

    public Integer getCountry() {
		return country;
	}

	public void setCountry(Integer country) {
		this.country = country;
	}

    public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

    public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

    public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

    public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

    public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

    public Integer getIssync() {
		return issync;
	}

	public void setIssync(Integer issync) {
		this.issync = issync;
	}

    public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

}