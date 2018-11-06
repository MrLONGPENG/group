package com.lveqia.cloud.zuul.objeck.vo;

import java.util.List;

public class MenuVo {

    private Integer id;
    private String url;
    private String path;
    private String component;
    private String name;
    private String iconCls;
    private Integer parentId;
    private List<RoleVo> roles;
    private List<MenuVo> children;
    private MetaVo meta;

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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<RoleVo> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleVo> roles) {
        this.roles = roles;
    }

    public List<MenuVo> getChildren() {
        return children;
    }

    public void setChildren(List<MenuVo> children) {
        this.children = children;
    }

    public MetaVo getMeta() {
        return meta;
    }

    public void setMeta(MetaVo meta) {
        this.meta = meta;
    }

    public void setMeta(Boolean keepAlive, Boolean requireAuth) {
        this.meta = new MetaVo();
        this.meta.setKeepAlive(keepAlive!=null? keepAlive:false);
        this.meta.setRequireAuth(requireAuth!=null? requireAuth:false);
    }
}