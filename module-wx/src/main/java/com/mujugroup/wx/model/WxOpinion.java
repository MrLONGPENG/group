package com.mujugroup.wx.model;


import java.util.Date;
import java.io.Serializable;
import javax.persistence.*;

/**
 * 意见反馈表
 * 类名:WxOpinion
 * 创建人:leolaurel
 * 创建时间:20180714
 */
@SuppressWarnings("serial")
@Table(name = "t_wx_opinion")
public class WxOpinion implements Serializable {

    public static final Integer TYPE_WAITING = 1;
    /**
     * 主键
     * 
     * 表字段 : t_wx_opinion.id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 唯一业务ID
     * 表字段 : t_wx_opinion.did
     */
    @Column(name = "did")
    private Long did;

    /**
     * 微信对外唯一ID
     * 表字段 : t_wx_opinion.open_id
     */
    @Column(name = "open_id")
    private String openId;


	/**
	 * 反馈的内容
	 * 表字段 : t_wx_opinion.content
	 */
	@Column(name = "content")
	private String content;


    /**
     * 阅读人信息
     * 表字段 : t_wx_opinion.reader
     */
    @Column(name = "reader")
    private String reader;

    /**
     * 建议处理状态 1.待读阅 2.已查看 3.待采用 4.已收集
     * 表字段 : t_wx_opinion.read_status
     */
    @Column(name = "read_status")
    private Integer readStatus;

    /**
     * 创建时间
     * 表字段 : t_wx_opinion.crtTime
     */
    @Column(name = "crtTime")
    private Date crtTime;

    /**
     * 更新时间
     * 表字段 : t_wx_opinion.updTime
     */
    @Column(name = "updTime")
    private Date updTime;



    public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReader() {
		return reader;
	}

	public void setReader(String reader) {
		this.reader = reader;
	}

    public Integer getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(Integer readStatus) {
		this.readStatus = readStatus;
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