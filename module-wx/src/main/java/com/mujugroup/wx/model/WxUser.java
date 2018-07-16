package com.mujugroup.wx.model;


import java.util.Date;
import java.io.Serializable;
import javax.persistence.*;

/**
 * 微信用户基础信息表
 * 类名:WxUser
 * 创建人:leolaurel
 * 创建时间:20180714
 */
@SuppressWarnings("serial")
@Table(name = "t_wx_user")
public class WxUser implements Serializable {

    /**
     * 主键
     * 
     * 表字段 : t_wx_user.id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 用户绑定手机号
     * 表字段 : t_wx_user.phone
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 微信对外唯一ID
     * 表字段 : t_wx_user.open_id
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 微信业务唯一ID
     * 表字段 : t_wx_user.union_id
     */
    @Column(name = "union_id")
    private String unionId;

    /**
     * 微信昵称
     * 表字段 : t_wx_user.nick_name
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 用户性别 1 男 2 女
     * 表字段 : t_wx_user.gender
     */
    @Column(name = "gender")
    private Integer gender;

    /**
     * 用户语言
     * 表字段 : t_wx_user.language
     */
    @Column(name = "language")
    private String language;

    /**
     * 用户国家
     * 表字段 : t_wx_user.country
     */
    @Column(name = "country")
    private String country;

    /**
     * 用户省份
     * 表字段 : t_wx_user.province
     */
    @Column(name = "province")
    private String province;

    /**
     * 用户城市
     * 表字段 : t_wx_user.city
     */
    @Column(name = "city")
    private String city;

    /**
     * 微信头像地址
     * 表字段 : t_wx_user.avatar_url
     */
    @Column(name = "avatar_url")
    private String avatarUrl;

    /**
     * 微信临时凭证
     * 表字段 : t_wx_user.session_key
     */
    @Column(name = "session_key")
    private String sessionKey;

    /**
     * 创建时间
     * 表字段 : t_wx_user.crtTime
     */
    @Column(name = "crtTime")
    private Date crtTime;

    /**
     * 更新时间
     * 表字段 : t_wx_user.update_time
     */
    @Column(name = "update_time")
    private Date updateTime;



    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

    public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

    public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

    public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

    public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

    public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

    public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

    public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

    public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

    public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

    public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

    public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

    public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}