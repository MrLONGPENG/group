package com.lveqia.cloud.zuul.model;


import java.util.Date;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 * 
 * 类名:SysMenu
 * 创建人:leolaurel
 * 创建时间:20180725
 */
@SuppressWarnings("serial")
@Table(name = "t_sys_menu")
public class SysMenu implements Serializable {

    /**
     * 主键
     * 
     * 表字段 : t_sys_menu.id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 
     * 表字段 : t_sys_menu.url
     */
    @Column(name = "url")
    private String url;

    /**
     * 
     * 表字段 : t_sys_menu.path
     */
    @Column(name = "path")
    private String path;

    /**
     * 
     * 表字段 : t_sys_menu.component
     */
    @Column(name = "component")
    private String component;

    /**
     * 
     * 表字段 : t_sys_menu.name
     */
    @Column(name = "name")
    private String name;

    /**
     * 
     * 表字段 : t_sys_menu.iconCls
     */
    @Column(name = "iconCls")
    private String iconCls;

    /**
     * 
     * 表字段 : t_sys_menu.keepAlive
     */
    @Column(name = "keepAlive")
    private Boolean keepAlive;

    /**
     * 
     * 表字段 : t_sys_menu.requireAuth
     */
    @Column(name = "requireAuth")
    private Boolean requireAuth;

    /**
     * 
     * 表字段 : t_sys_menu.parentId
     */
    @Column(name = "parentId")
    private Integer parentId;

    /**
     * 
     * 表字段 : t_sys_menu.enabled
     */
    @Column(name = "enabled")
    private Boolean enabled;

	private List<SysRole> roles;

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

    public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

    public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

    public Boolean getKeepAlive() {
		return keepAlive;
	}

	public void setKeepAlive(Boolean keepAlive) {
		this.keepAlive = keepAlive;
	}

    public Boolean getRequireAuth() {
		return requireAuth;
	}

	public void setRequireAuth(Boolean requireAuth) {
		this.requireAuth = requireAuth;
	}

    public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

    public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}
}