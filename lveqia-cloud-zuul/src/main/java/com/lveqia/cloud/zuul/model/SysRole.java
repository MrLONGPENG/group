package com.lveqia.cloud.zuul.model;


import java.io.Serializable;
import javax.persistence.*;

/**
 * 
 * 类名:SysRole
 * 创建人:leolaurel
 * 创建时间:20180725
 */
@SuppressWarnings("serial")
@Table(name = "t_sys_role")
public class SysRole implements Serializable {

    /**
     * 主键
     * 
     * 表字段 : t_sys_role.id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 
     * 表字段 : t_sys_role.name
     */
    @Column(name = "name")
    private String name;

    /**
     * 角色名称
     * 表字段 : t_sys_role.desc
     */
    @Column(name = "desc")
    private String desc;



    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}