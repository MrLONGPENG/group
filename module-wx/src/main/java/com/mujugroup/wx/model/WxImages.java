package com.mujugroup.wx.model;


import java.util.Date;
import java.io.Serializable;
import javax.persistence.*;

/**
 * 保修图片表
 * 类名:WxImages
 * 创建人:LEOLAUREL
 * 创建时间:20180707
 */
@SuppressWarnings("serial")
@Table(name = "t_wx_images")
public class WxImages implements Serializable {

	public static final Integer TYPE_REPAIR = 1;
    /**
     * 主键
     * 
     * 表字段 : t_wx_images.id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 图片类型主键
     * 表字段 : t_wx_images.pid
     */
    @Column(name = "pid")
    private Integer pid;

    /**
     * 图片类型 1.保修图片 2.待定
     * 表字段 : t_wx_images.type
     */
    @Column(name = "type")
    private Integer type;

    /**
     * 图片地址
     * 表字段 : t_wx_images.image_url
     */
    @Column(name = "image_url")
    private String imageUrl;

    /**
     * 创建时间
     * 表字段 : t_wx_images.crtTime
     */
    @Column(name = "crtTime")
    private Date crtTime;



    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

    public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

    public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

}