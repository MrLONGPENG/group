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
        private int flag;
        // 构造方法
        FailType(String code, int flag) {
            this.code = code;
            this.flag = flag;
        }
    }

    public enum  ErrorType{
        TYPE_POWER_LOW(FailType.TYPE_POWER, "FE_PW_Low"), //低电量
        TYPE_POWER_DOWN(FailType.TYPE_POWER, "FE_PW_Down"),  //电量下降异常
        TYPE_POWER_CHARGE(FailType.TYPE_POWER, "FE_PW_Charge"),  //无法充电
        TYPE_SIGNAL_OFFLINE(FailType.TYPE_SIGNAL, "FE_SG_Offline"),  //离线状态
        TYPE_SIGNAL_LOW(FailType.TYPE_SIGNAL, "FE_SG_Low"),    //低信号
        TYPE_SIGNAL_WAVE(FailType.TYPE_SIGNAL, "FE_SG_Wave"),       //信号波动异常
        TYPE_SWITCH_OPEN(FailType.TYPE_SWITCH, "FE_SW_Open"),    //开锁机械故障
        TYPE_SWITCH_CLOSE(FailType.TYPE_SWITCH, "FE_SW_Close"),     //关锁机械故障
        TYPE_SWITCH_TIMEOUT(FailType.TYPE_SWITCH, "FE_SW_Timeout"),    //超时未关锁
        TYPE_SWITCH_ORDER(FailType.TYPE_SWITCH, "FE_SW_Order"),    //无订单异常开锁
        TYPE_SWITCH_USING(FailType.TYPE_SWITCH, "FE_SW_Using");    //非使用时段开锁
        // 成员变量
        private final FailType failType;
        private String errorCode;
        // 构造方法
        ErrorType(FailType failType, String errorCode) {
            this.failType = failType;
            this.errorCode = errorCode;
        }
        public int getFailFlag(){
            return failType.flag;
        }
        public String getFailCode() {
            return failType.code;
        }

        public String getErrorCode() {
            return errorCode;
        }

    }

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