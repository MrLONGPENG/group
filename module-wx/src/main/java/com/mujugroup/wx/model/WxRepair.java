package com.mujugroup.wx.model;


import java.util.Date;
import java.io.Serializable;
import javax.persistence.*;

/**
 * 保修信息表
 * 类名:WxRepair
 * 创建人:LEOLAUREL
 * 创建时间:20180707
 */
@SuppressWarnings("serial")
@Table(name = "t_wx_repair")
public class WxRepair implements Serializable {

    public static final int TYPE_REPAIR_WAIT = 1;
    /**
     * 主键
     * 
     * 表字段 : t_wx_repair.id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 唯一业务ID
     * 表字段 : t_wx_repair.did
     */
    @Column(name = "did")
    private Long did;

    /**
     * 微信对外唯一ID
     * 表字段 : t_wx_repair.open_id
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 损坏的部位
     * 表字段 : t_wx_repair.fault_cause
     */
    @Column(name = "fault_cause")
    private String faultCause;

    /**
     * 损坏的描述
     * 表字段 : t_wx_repair.fault_describe
     */
    @Column(name = "fault_describe")
    private String faultDescribe;

    /**
     * 修复人信息
     * 表字段 : t_wx_repair.restorer
     */
    @Column(name = "restorer")
    private String restorer;

    /**
     * 报修状态 1.待修复 2.修复中 3.修复完
     * 表字段 : t_wx_repair.repair_status
     */
    @Column(name = "repair_status")
    private Integer repairStatus;

    /**
     * 创建时间
     * 表字段 : t_wx_repair.crtTime
     */
    @Column(name = "crtTime")
    private Date crtTime;

    /**
     * 更新时间
     * 表字段 : t_wx_repair.updTime
     */
    @Column(name = "updTime")
    private Date updTime;



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

    public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

    public String getFaultCause() {
		return faultCause;
	}

	public void setFaultCause(String faultCause) {
		this.faultCause = faultCause;
	}

    public String getFaultDescribe() {
		return faultDescribe;
	}

	public void setFaultDescribe(String faultDescribe) {
		this.faultDescribe = faultDescribe;
	}

    public String getRestorer() {
		return restorer;
	}

	public void setRestorer(String restorer) {
		this.restorer = restorer;
	}

    public Integer getRepairStatus() {
		return repairStatus;
	}

	public void setRepairStatus(Integer repairStatus) {
		this.repairStatus = repairStatus;
	}

    public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

    public Date getUpdTime() {
		return updTime;
	}

	public void setUpdTime(Date updTime) {
		this.updTime = updTime;
	}

}