package com.mujugroup.core.model;


import java.util.Date;
import java.io.Serializable;
import javax.persistence.*;

/**
 * 国家省份城市表
 * 类名:Region
 * 创建人:leolaurel
 * 创建时间:20180828
 */
@SuppressWarnings("serial")
@Table(name = "t_country_province_city")
public class Region implements Serializable {

    /**
     * 主键
     * 
     * 表字段 : t_country_province_city.id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 国家省份城市名称
     * 表字段 : t_country_province_city.name
     */
    @Column(name = "name")
    private String name;

    /**
     * pid 0 国家
     * 表字段 : t_country_province_city.pid
     */
    @Column(name = "pid")
    private Integer pid;

    /**
     * 备注
     * 表字段 : t_country_province_city.remark
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 备用字段
     * 表字段 : t_country_province_city.key
     */
    @Column(name = "key")
    private String key;

    /**
     * 状态 0 禁用 1 启用
     * 表字段 : t_country_province_city.status
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 排序
     * 表字段 : t_country_province_city.ord
     */
    @Column(name = "ord")
    private Integer ord;

    /**
     * 经度
     * 表字段 : t_country_province_city.longitude
     */
    @Column(name = "longitude")
    private Double longitude;

    /**
     * 纬度
     * 表字段 : t_country_province_city.latitude
     */
    @Column(name = "latitude")
    private Double latitude;



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

    public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

    public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

    public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

    public Integer getOrd() {
		return ord;
	}

	public void setOrd(Integer ord) {
		this.ord = ord;
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

}