package com.lveqia.cloud.zuul.model;


import java.util.Date;
import java.io.Serializable;
import javax.persistence.*;

/**
 * 
 * 类名:SysMenuRole
 * 创建人:leolaurel
 * 创建时间:20180725
 */
@SuppressWarnings("serial")
@Table(name = "t_sys_menu_role")
public class SysMenuRole implements Serializable {

    /**
     * 主键
     * 
     * 表字段 : t_sys_menu_role.id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 
     * 表字段 : t_sys_menu_role.mid
     */
    @Column(name = "mid")
    private Integer mid;

    /**
     * 
     * 表字段 : t_sys_menu_role.rid
     */
    @Column(name = "rid")
    private Integer rid;



    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

    public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

}