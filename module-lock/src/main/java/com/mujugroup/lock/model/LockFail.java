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
    public static final String FAIL_TYPE_POWER = "F_Power";
    public static final String FAIL_TYPE_SIGNAL = "F_Signal";
    public static final String FAIL_TYPE_SWITCH = "F_Switch";

    public enum FailType {
        TYPE_POWER("F_Power", 1),
        TYPE_SIGNAL("F_Signal", 2),
        TYPE_SWITCH("F_Switch", 4);
        // 成员变量
        private final String code;
        private int type;

        // 构造方法
        FailType(String code, int type) {
            this.code = code;
            this.type = type;
        }

        public String getCode() {
            return code;
        }


        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    //低电量
    public static final String FE_PW_LOW = "FE_PW_Low";
    //电量下降异常
    public static final String FE_PW_DOWN = "FE_PW_Down";
    //无法充电
    public static final String FE_PW_CHARGE = "FE_PW_Charge";
    //离线状态
    public static final String FE_SG_OFFLINE = "FE_SG_Offline";
    //低信号
    public static final String FE_SG_LOW = "FE_SG_Low";
    //信号波动异常
    public static final String FE_SG_WAVE = "FE_SG_Wave";
    //开锁机械故障
    public static final String FE_SW_OPEN = "FE_SW_Open";
    //关锁机械故障
    public static final String FE_SW_CLOSE = "FE_SW_Close";
    //超时未关锁
    public static final String FE_SW_TIMEOUT = "FE_SW_Timeout";
    //无订单异常开锁
    public static final String FE_SW_ORDER = "FE_SW_Order";
    //非使用时段开锁
    public static final String FE_SW_USING = "FE_SW_Using";
    //没电
    public static final String FR_PW_POWER = "FR_PW_Power";
    //信号接收器故障
    public static final String FR_SG_RECEIVE = "FR_SG_Receive";

    /**
     * 主键
     * <p>
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

    @Column(name = "fail_flag")
    private Integer failFlag;
    /**
     * 故障类型 1:电量异常 2:信号异常 4:开关锁异常
     * 表字段 : t_lock_fail.fail_code
     */
    @Column(name = "fail_code")
    private String failCode;

    /**
     * 错误类型 1:低电量 2:电量下降异常 3:无法充电
     * 表字段 : t_lock_fail.error_code
     */
    @Column(name = "error_code")
    private String errorCode;

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
     * 表字段 : t_lock_fail.resolve_code
     */
    @Column(name = "resolve_code")
    private Integer resolveCode;

    /**
     * 异常产生原因及解决方法
     * 表字段 : t_lock_fail.explain
     */
    @Column(name = "explain")
    private String explain;


}