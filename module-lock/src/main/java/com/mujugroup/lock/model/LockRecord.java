package com.mujugroup.lock.model;


import java.util.Date;
import java.io.Serializable;
import javax.persistence.*;

/**
 * 开关锁记录表
 * 类名:LockRecord
 * 创建人:leolaurel
 * 创建时间:20181102
 */
@SuppressWarnings("serial")
@Table(name = "t_lock_record")
public class LockRecord implements Serializable {

    /**
     * 主键
     * <p>
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
     * 开关锁时间
     * 表字段 : t_lock_record.time
     */
    @Column(name = "time")
    private Date time;

    /**
     * 状态 0 关闭 1 打开
     * 表字段 : t_lock_record.lockStatus
     */
    @Column(name = "lockStatus")
    private Integer lockStatus;


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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(Integer lockStatus) {
        this.lockStatus = lockStatus;
    }

}