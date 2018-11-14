package com.mujugroup.lock.model;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import javax.persistence.*;

/**
 * 锁故障表
 * 类名:LockFail
 * 创建人:leolaurel
 * 创建时间:20181107
 */
@Data
@SuppressWarnings("serial")
@Table(name = "t_lock_fail")
public class LockFail implements Serializable {

    public static final String FAIL_TYPE_POWER = "1";
    public static final String FAIL_TYPE_SIGNAL = "2";
    public static final String FAIL_TYPE_SWITCH = "4";
    //低电量
    public static final String FE_PW_LOW = "1";
    //电量下降异常
    public static final String FE_PW_DOWN = "2";
    //无法充电
    public static final String FE_PW_CHARGE = "3";
    //低信号
    public static final String FE_SG_NULL ="4" ;
    //信号波动异常
    public static final String FE_SG_WAVE = "5";
    //开锁机械故障
    public static final String FE_SW_OPEN = "6";
    //关锁机械故障
    public static final String FE_SW_CLOSE = "7";
    //超时未关锁
    public static final String FE_SW_TIMEOUT = "8";
    //无订单异常开锁
    public static final String FE_SW_ORDER = "9";
    //非使用时段开锁
    public static final String FE_SW_USING = "10";
    //没电
    public static final String FR_PW_POWER = "1";
    //信号接收器故障
    public static final String FR_SG_RECEIVE = "2";
    //离线状态
    public static final String FE_SG_OFFLINE = "11";
    /**
     * 主键
     * 
     * 表字段 : t_lock_fail.id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 业务ID
     * 表字段 : t_lock_fail.did
     */
    @Column(name = "did")
    private Long did;

    /**
     * 设备锁十进制ID
     * 表字段 : t_lock_fail.lock_id
     */
    @Column(name = "lock_id")
    private Long lockId;

    /**
     * 代理商id
     * 表字段 : t_lock_fail.aid
     */
    @Column(name = "aid")
    private Integer aid;

    /**
     * 医院id
     * 表字段 : t_lock_fail.hid
     */
    @Column(name = "hid")
    private Integer hid;

    /**
     * 科室id
     * 表字段 : t_lock_fail.oid
     */
    @Column(name = "oid")
    private Integer oid;

    /**
     * 故障类型 1:电量异常 2:信号异常 4:开关锁异常
     * 表字段 : t_lock_fail.fail_type
     */
    @Column(name = "fail_type")
    private Integer failType;

    /**
     * 错误类型 1:低电量 2:电量下降异常 3:无法充电
     * 表字段 : t_lock_fail.error_type
     */
    @Column(name = "error_type")
    private Integer errorType;

    /**
     * 故障上报时间
     * 表字段 : t_lock_fail.last_refresh
     */
    @Column(name = "last_refresh")
    private Date lastRefresh;

    /**
     * 解决时间
     * 表字段 : t_lock_fail.finish_time
     */
    @Column(name = "finish_time")
    private Date finishTime;

    /**
     * 故障状态 1:产生异常 2:解决中 3:已解决 4:未解决
     * 表字段 : t_lock_fail.status
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 处理人
     * 表字段 : t_lock_fail.resolve_man
     */
    @Column(name = "resolve_man")
    private Integer resolveMan;

    /**
     * 造成故障类型 1:没电了
     * 表字段 : t_lock_fail.resolve_type
     */
    @Column(name = "resolve_type")
    private Integer resolveType;

    /**
     * 异常产生原因及解决方法
     * 表字段 : t_lock_fail.explain
     */
    @Column(name = "explain")
    private String explain;



}