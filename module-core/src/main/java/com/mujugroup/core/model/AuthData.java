package com.mujugroup.core.model;


import javax.persistence.*;
import java.io.Serializable;

/**
 * 数据权限关系表
 * 类名:AuthData
 * 创建人:leolaurel
 * 创建时间:20180927
 */
@SuppressWarnings("serial")
@Table(name = "t_auth_data")
public class AuthData implements Serializable {


    /**
     * 主键
     * <p>
     * 表字段 : t_auth_data.id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 用户ID
     * 表字段 : t_auth_data.uid
     */
    @Column(name = "uid")
    private Integer uid;

    /**
     * 关系ID [aid, hid, oid]
     * 表字段 : t_auth_data.rid
     */
    @Column(name = "rid")
    private Integer rid;

    /**
     * 关系类型 AID 1; HID 2; OID: 3
     * 表字段 : t_auth_data.type
     */
    @Column(name = "type")
    private Integer type;


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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}