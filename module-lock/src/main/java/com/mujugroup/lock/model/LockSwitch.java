package com.mujugroup.lock.model;


import java.util.Date;
import java.io.Serializable;
import javax.persistence.*;

/**
 * 开关锁记录表
 * 类名:LockSwitch
 * 创建人:leolaurel
 * 创建时间:20181102
 */
@SuppressWarnings("serial")
@Table(name = "t_lock_switch")
public class LockSwitch implements Serializable {

    /**
     * 主键
     * <p>
     * 表字段 : t_lock_switch.id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 业务ID
     * 表字段 : t_lock_switch.did
     */
    @Column(name = "did")
    private Long did;

    /**
     * 设备锁十进制ID
     * 表字段 : t_lock_switch.lock_id
     */
    @Column(name = "lock_id")
    private Long lockId;
    /**
     * 开关锁时间
     * 表字段 : t_lock_switch.receiveTime
     */
    @Column(name = "receiveTime")
    private Date receiveTime;
    /**
     * 状态 0 关闭 1 打开
     * 表字段 : t_lock_switch.lockStatus
     */
    @Column(name = "lockStatus")
    private Integer lockStatus;
    /**
     * 本地时间
     * 表字段 : t_lock_switch.localTime
     */
    @Column(name = "localTime")
    private Date localTime;

    public Date getLocalTime() {
        return localTime;
    }

    public void setLocalTime(Date localTime) {
        this.localTime = localTime;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

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

    public Integer getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(Integer lockStatus) {
        this.lockStatus = lockStatus;
    }

}