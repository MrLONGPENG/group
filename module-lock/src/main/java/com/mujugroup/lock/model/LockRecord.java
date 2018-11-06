package com.mujugroup.lock.model;


import java.util.Date;
import java.io.Serializable;
import javax.persistence.*;

/**
 * 锁记录表
 * 类名:LockRecord
 * 创建人:leolaurel
 * 创建时间:20181106
 */
@SuppressWarnings("serial")
@Table(name = "t_lock_record")
public class LockRecord implements Serializable {

    /**
     * 主键
     * 
     * 表字段 : t_lock_record.id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 业务ID
     * 表字段 : t_lock_record.did
     */
    @Column(name = "did")
    private Long did;

    /**
     * 设备锁十进制ID
     * 表字段 : t_lock_record.lock_id
     */
    @Column(name = "lock_id")
    private Long lockId;

    /**
     * 信号指标
     * 表字段 : t_lock_record.csq
     */
    @Column(name = "csq")
    private Integer csq;

    /**
     * 温度
     * 表字段 : t_lock_record.temp
     */
    @Column(name = "temp")
    private Integer temp;

    /**
     * 充电电压(原值:vbus)
     * 表字段 : t_lock_record.charge
     */
    @Column(name = "charge")
    private Integer charge;

    /**
     * 电池电压(原值:vbattery)
     * 表字段 : t_lock_record.voltage
     */
    @Column(name = "voltage")
    private Integer voltage;

    /**
     * 充电电流(原值:iCharge)
     * 表字段 : t_lock_record.electric
     */
    @Column(name = "electric")
    private Integer electric;

    /**
     * 剩余电量
     * 表字段 : t_lock_record.battery_stat
     */
    @Column(name = "battery_stat")
    private Integer batteryStat;

    /**
     * 锁状态 1:关 2:开 (助力车 3:中间态 ) 4代表开锁机械故障、5代表关锁机械故障，6代表锁端本地时间不在限制时间范围内
     * 表字段 : t_lock_record.lock_status
     */
    @Column(name = "lock_status")
    private Integer lockStatus;

    /**
     * 最后上报时间
     * 表字段 : t_lock_record.last_refresh
     */
    @Column(name = "last_refresh")
    private Date lastRefresh;

    /**
     * 创建时间
     * 表字段 : t_lock_record.crtTime
     */
    @Column(name = "crtTime")
    private Date crtTime;



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

    public Long getLockId() {
		return lockId;
	}

	public void setLockId(Long lockId) {
		this.lockId = lockId;
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

    public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

}