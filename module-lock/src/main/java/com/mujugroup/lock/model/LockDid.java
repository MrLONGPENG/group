package com.mujugroup.lock.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 柜子业务编号与锁编号关系表
 * 类名:LockDid
 * 创建人:leolaurel
 * 创建时间:20180623
 */
@SuppressWarnings("serial")
@Table(name = "t_lock_did")
public class LockDid implements Serializable {


	public LockDid(long did, long bid, int brand) {
		this.did = did;
		this.lockId = bid;
		this.lockHex = Long.toHexString(bid);
		this.brand = brand;
	}


	/**
     * 主键
     * 
     * 表字段 : t_lock_did.id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 唯一业务ID
     * 表字段 : t_lock_did.did
     */
    @Column(name = "did")
    private Long did;

    /**
     * 设备锁厂商品牌(1:连旅；2:待定)
     * 表字段 : t_lock_did.brand
     */
    @Column(name = "brand")
    private Integer brand;

    /**
     * 设备锁十进制ID
     * 表字段 : t_lock_did.lock_id
     */
    @Column(name = "lock_id")
    private Long lockId;

    /**
     * 设备锁十六进制ID
     * 表字段 : t_lock_did.lock_hex
     */
    @Column(name = "lock_hex")
    private String lockHex;

    /**
     * 更新时间
     * 表字段 : t_lock_did.update_time
     */
    @Column(name = "update_time")
    private Date updateTime;

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

    public Long getLockId() {
		return lockId;
	}

	public void setLockId(Long lockId) {
		this.lockId = lockId;
	}

    public String getLockHex() {
		return lockHex;
	}

	public void setLockHex(String lockHex) {
		this.lockHex = lockHex;
	}

    public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}