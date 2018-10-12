package com.mujugroup.wx.model;


import java.io.Serializable;
import javax.persistence.*;

/**
 * 扩展关系数据表
 * 类名:WxRelation
 * 创建人:leolaurel
 * 创建时间:20180712
 */
@SuppressWarnings("serial")
@Table(name = "t_wx_relation")
public class WxRelation implements Serializable {
	public static final int KID_DEFAULT = 0;	//默认数据
	public static final int KEY_DEFAULT = 0;	//默认数据
	public static final int KEY_AGENT = 1;		// 代理商
	public static final int KEY_HOSPITAL = 2;  	// 医院
	public static final int KEY_DEPARTMENT = 3; // 科室

	public static final int TYPE_GOODS = 1;  	// 商品套餐
	public static final int TYPE_UPTIME = 2;  	// 运行时间
	public static final int TYPE_MIDDAY = 3;	// 午休时间

	/**
     * 主键
     * 
     * 表字段 : t_wx_relation.id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 关系ID {商品套餐ID 运行时间ID}
     * 表字段 : t_wx_relation.rid
     */
    @Column(name = "rid")
    private Integer rid;

    /**
     * 外键ID {代理商ID、医院ID、科室ID、其他ID}
     * 表字段 : t_wx_relation.kid
     */
    @Column(name = "kid")
    private Integer kid;

    /**
     * 外键类型 0:默认数据 1:代理商 2:医院 3:科室 4:其他
     * 表字段 : t_wx_relation.key
     */
    @Column(name = "key")
    private Integer key;

    /**
     * 关系类型 1:商品套餐 2:运行时间
     * 表字段 : t_wx_relation.type
     */
    @Column(name = "type")
    private Integer type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

    public Integer getKid() {
		return kid;
	}

	public void setKid(Integer kid) {
		this.kid = kid;
	}

    public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}