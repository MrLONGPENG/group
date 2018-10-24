package com.mujugroup.core.objeck.vo.hospital;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "AddVo", description = "医院添加Vo")
public class AddVo implements Serializable {

    @ApiModelProperty(value = "id", notes = "医院编号")
    private Integer id;
    @ApiModelProperty(value = "name", notes = "医院名称", required = true)
    private String name;
    @ApiModelProperty(value = "aid", notes = "代理商编号", required = true)
    private String aid;
    @ApiModelProperty(value = "tel", notes = "电话")
    private String tel;
    @ApiModelProperty(value = "person", notes = "联系人")
    private String person;
    @ApiModelProperty(value = "remark", notes = "备注")
    private String remark;
    @ApiModelProperty(value = "crtTime", notes = "创建时间")
    private Date crtTime;
    @ApiModelProperty(value = "uid", notes = "创建ID", required = true)
    private Integer uid;
    @ApiModelProperty(value = "address", notes = "医院地址")
    private String address;
    @ApiModelProperty(value = "country", notes = "国家",required = true)
    private Integer country;
    @ApiModelProperty(value = "pid", notes = "省份ID", required = true)
    private Integer pid;
    @ApiModelProperty(value = "cid", notes = "城市ID", required = true)
    private Integer cid;
    @ApiModelProperty(value = "enable", notes = "医院状态 22 启用 23 禁用 17 删除")
    private Integer enable;
    @ApiModelProperty(value = "issync", notes = "是否与子服务器同步，0：已同步，1：未同步")
    private Integer issync;
    @ApiModelProperty(value = "level", notes = "医院等级")
    private String level;

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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Integer getIssync() {
        return issync;
    }

    public void setIssync(Integer issync) {
        this.issync = issync;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }


}
