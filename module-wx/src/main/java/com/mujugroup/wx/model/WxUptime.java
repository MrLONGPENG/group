package com.mujugroup.wx.model;


import java.io.Serializable;
import javax.persistence.*;

/**
 * 木巨柜运行时间表
 * 类名:WxUptime
 * 创建人:leolaurel
 * 创建时间:20180712
 */
@SuppressWarnings("serial")
@Table(name = "t_wx_uptime")
public class WxUptime implements Serializable {

    /**
     * 主键
     * 
     * 表字段 : t_wx_uptime.id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 开锁最早时间(秒,不含日期)
     * 表字段 : t_wx_uptime.start_time
     */
    @Column(name = "start_time")
    private Integer startTime;

    /**
     * 关锁最晚时间(秒,不含日期)
     * 表字段 : t_wx_uptime.stop_time
     */
    @Column(name = "stop_time")
    private Integer stopTime;

    /**
     * 开锁最早时间描述
     * 表字段 : t_wx_uptime.start_desc
     */
    @Column(name = "start_desc")
    private String startDesc;

    /**
     * 关锁最晚时间描述
     * 表字段 : t_wx_uptime.stop_desc
     */
    @Column(name = "stop_desc")
    private String stopDesc;

    /**
     * 此属性使用说明
     * 表字段 : t_wx_uptime.explain
     */
    @Column(name = "explain")
    private String explain;



    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    public Integer getStartTime() {
		return startTime;
	}

	public void setStartTime(Integer startTime) {
		this.startTime = startTime;
	}

    public Integer getStopTime() {
		return stopTime;
	}

	public void setStopTime(Integer stopTime) {
		this.stopTime = stopTime;
	}

    public String getStartDesc() {
		return startDesc;
	}

	public void setStartDesc(String startDesc) {
		this.startDesc = startDesc;
	}

    public String getStopDesc() {
		return stopDesc;
	}

	public void setStopDesc(String stopDesc) {
		this.stopDesc = stopDesc;
	}

    public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

}