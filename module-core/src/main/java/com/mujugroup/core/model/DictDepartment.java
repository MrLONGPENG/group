package com.mujugroup.core.model;


import java.util.Date;
import java.io.Serializable;
import javax.persistence.*;

/**
 * 科室字典表
 * 类名:DictDepartment
 * 创建人:leolaurel
 * 创建时间:20181022
 */
@SuppressWarnings("serial")
@Table(name = "t_dict_department")
public class DictDepartment implements Serializable {
    //设置当前状态为启用状态
    public static final int TYPE_ENABLE = 1;
    //设置当前状态为禁用状态
    public static final int TYPE_DISABLE = 0;
    /**
     * 主键
     * <p>
     * 表字段 : t_dict_department.id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 科室名称
     * 表字段 : t_dict_department.name
     */
    @Column(name = "name")
    private String name;

    /**
     * 备注
     * 表字段 : t_dict_department.remark
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 创建时间
     * 表字段 : t_dict_department.create_date
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 科室启用状态
     * 表字段 : t_dict_department.enable
     */
    @Column(name = "enable")
    private Integer enable;

    /**
     * 创建者ID
     * 表字段 : t_dict_department.create_userid
     */
    @Column(name = "create_userid")
    private Integer createUserid;

    /**
     * 更新者ID
     * 表字段 : t_dict_department.update_userid
     */
    @Column(name = "update_userid")
    private Integer updateUserid;

    /**
     * 更新者时间
     * 表字段 : t_dict_department.update_time
     */
    @Column(name = "update_time")
    private Date updateTime;


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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Integer getCreateUserid() {
        return createUserid;
    }

    public void setCreateUserid(Integer createUserid) {
        this.createUserid = createUserid;
    }

    public Integer getUpdateUserid() {
        return updateUserid;
    }

    public void setUpdateUserid(Integer updateUserid) {
        this.updateUserid = updateUserid;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}