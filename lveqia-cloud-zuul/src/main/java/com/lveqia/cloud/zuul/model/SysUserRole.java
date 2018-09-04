package com.lveqia.cloud.zuul.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * 
 * 类名:SysUserRole
 * 创建人:leolaurel
 * 创建时间:20180725
 */
@SuppressWarnings("serial")
@Table(name = "t_sys_user_role")
public class SysUserRole implements Serializable {

    /**
     * 主键
     * 
     * 表字段 : t_sys_user_role.id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 
     * 表字段 : t_sys_user_role.uid
     */
    @Column(name = "uid")
    private Integer uid;

    /**
     * 
     * 表字段 : t_sys_user_role.rid
     */
    @Column(name = "rid")
    private Integer rid;



    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

    public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

}