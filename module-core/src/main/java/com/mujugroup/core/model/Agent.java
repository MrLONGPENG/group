package com.mujugroup.core.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 代理商信息表
 * 类名:agent
 * 创建人:leolaurel
 * 创建时间:20180927
 */
@SuppressWarnings("serial")
@Table(name = "t_agent")
public class Agent implements Serializable {
    public static final int TYPE_ENABLE = 1;
    public static final int TYPE_FORBIDDEN = 2;
    public static final int TYPE_DELETE = 0;
    /**
     * 主键
     * <p>
     * 表字段 : t_agent.id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 名称
     * 表字段 : t_agent.name
     */
    @Column(name = "name")
    private String name;

    /**
     * 创建时间
     * 表字段 : t_agent.crtTime
     */
    @Column(name = "crtTime")
    private Date crtTime;

    /**
     * 代理商状态 1 启用 2 禁用 0 删除
     * 表字段 : t_agent.enable
     */
    @Column(name = "enable")
    private Integer enable;

    public Agent(int id, String name, int enable) {
        this.id = id;
        this.name = name;
        this.enable = enable;

    }

    public Agent() {
    }

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

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

}