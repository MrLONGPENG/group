package com.mujugroup.lock.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 锁设备信息表
 * 类名:LockInfo
 * 创建人:leolaurel
 * 创建时间:20180623
 */
@SuppressWarnings("serial")
@Table(name = "t_lock_info")
public class LockInfo implements Serializable {

    /**
     * 主键
     * 
     * 表字段 : t_lock_info.id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 设备ID
     * 表字段 : t_lock_info.did
     */
    @Column(name = "did")
    private Long did;

    /**
     * 设备锁厂商品牌(1:连旅；2:待定)
     * 表字段 : t_lock_info.brand
     */
    @Column(name = "brand")
    private Integer brand;

    /**
     * 设备蓝牙地址
     * 表字段 : t_lock_info.mac
     */
    @Column(name = "mac")
    private String mac;

    /**
     * 设备蓝牙密钥
     * 表字段 : t_lock_info.key
     */
    @Column(name = "key")
    private String key;

	/**
	 * SIM卡ID
	 * 表字段 : t_lock_info.sim_id
	 */
	@Column(name = "sim_id")
	private String simId;

    /**
     * 固件版本
     * 表字段 : t_lock_info.f_version
     */
    @Column(name = "f_version")
    private Integer fVersion;

	/**
     * 硬件版本
     * 表字段 : t_lock_info.h_version
     */
    @Column(name = "h_version")
    private Integer hVersion;

    /**
     * 经度
     * 表字段 : t_lock_info.longitude
     */
    @Column(name = "longitude")
    private java.math.BigDecimal longitude;

    /**
     * 纬度
     * 表字段 : t_lock_info.latitude
     */
    @Column(name = "latitude")
    private java.math.BigDecimal latitude;

    /**
     * 信号指标
     * 表字段 : t_lock_info.csq
     */
    @Column(name = "csq")
    private Integer csq;

    /**
     * 温度
     * 表字段 : t_lock_info.temp
     */
    @Column(name = "temp")
    private Integer temp;

    /**
     * 充电电压(原值:vbus)
     * 表字段 : t_lock_info.charge
     */
    @Column(name = "charge")
    private Integer charge;

    /**
     * 电池电压(原值:vbattery)
     * 表字段 : t_lock_info.voltage
     */
    @Column(name = "voltage")
    private Integer voltage;

    /**
     * 充电电流(原值:iCharge)
     * 表字段 : t_lock_info.electric
     */
    @Column(name = "electric")
    private Integer electric;

    /**
     * 升级标识(原值:upgradeFlag)
     * 表字段 : t_lock_info.upgrade
     */
    @Column(name = "upgrade")
    private Integer upgrade;

    /**
     * 剩余电量
     * 表字段 : t_lock_info.battery_stat
     */
    @Column(name = "battery_stat")
    private Integer batteryStat;

    /**
     * 锁状态 1:关 2:开 (助力车 3:中间态 ) 4代表开锁机械故障、5代表关锁机械故障，6代表锁端本地时间不在限制时间范围内
     * 表字段 : t_lock_info.lock_status
     */
    @Column(name = "lock_status")
    private Integer lockStatus;

    /**
     * 最后上报时间
     * 表字段 : t_lock_info.last_refresh
     */
    @Column(name = "last_refresh")
    private Date lastRefresh;


    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    public Long getDid() {
		return did;
	}

	public void setDid(Long did) {
		this.did = did;
	}

    public Integer getBrand() {
		return brand;
	}

	public void setBrand(Integer brand) {
		this.brand = brand;
	}

    public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

    public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSimId() {
		return simId;
	}

	public void setSimId(String simId) {
		this.simId = simId;
	}

	public Integer getFVersion() {
		return fVersion;
	}

	public void setFVersion(Integer fVersion) {
		this.fVersion = fVersion;
	}

    public Integer getHVersion() {
		return hVersion;
	}

	public void setHVersion(Integer hVersion) {
		this.hVersion = hVersion;
	}

    public java.math.BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(java.math.BigDecimal longitude) {
		this.longitude = longitude;
	}

    public java.math.BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(java.math.BigDecimal latitude) {
		this.latitude = latitude;
	}

    public Integer getCsq() {
		return csq;
	}

	public void setCsq(Integer csq) {
		this.csq = csq;
	}

    public Integer getTemp() {
		return temp;
	}

	public void setTemp(Integer temp) {
		this.temp = temp;
	}

    public Integer getCharge() {
		return charge;
	}

	public void setCharge(Integer charge) {
		this.charge = charge;
	}

    public Integer getVoltage() {
		return voltage;
	}

	public void setVoltage(Integer voltage) {
		this.voltage = voltage;
	}

    public Integer getElectric() {
		return electric;
	}

	public void setElectric(Integer electric) {
		this.electric = electric;
	}

    public Integer getUpgrade() {
		return upgrade;
	}

	public void setUpgrade(Integer upgrade) {
		this.upgrade = upgrade;
	}

    public Integer getBatteryStat() {
		return batteryStat;
	}

	public void setBatteryStat(Integer batteryStat) {
		this.batteryStat = batteryStat;
	}

	public Integer getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(Integer lockStatus) {
		this.lockStatus = lockStatus;
	}

	public Date getLastRefresh() {
		return lastRefresh;
	}

	public void setLastRefresh(Date lastRefresh) {
		this.lastRefresh = lastRefresh;
	}

}