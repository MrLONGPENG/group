package com.mujugroup.core.objeck.vo.hospital;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "ListVo", description = "医院列表")
public class ListVo implements Serializable {
    @ApiModelProperty(value = "id", notes = "医院编号", required = true)
    private Integer id;
    @ApiModelProperty(value = "name", notes = "医院名称")
    private String name;
    @ApiModelProperty(value = "aid", notes = "代理商编号")
    private String aid;
    @ApiModelProperty(value = "aName", notes = "代理商名称")
    private String aName;
    @ApiModelProperty(value = "tel", notes = "电话")
    private String tel;
    @ApiModelProperty(value = "person", notes = "联系人")
    private String person;
    @ApiModelProperty(value = "remark", notes = "备注")
    private String remark;
    @ApiModelProperty(value = "crtTime", notes = "创建时间")
    private Date crtTime;
    @ApiModelProperty(value = "uid", notes = "创建ID")
    private Integer uid;
    @ApiModelProperty(value = "address", notes = "医院地址")
    private String address;
    @ApiModelProperty(value = "pid", notes = "省份ID")
    private Integer pid;
    @ApiModelProperty(value = "cid", notes = "城市ID")
    private Integer cid;
    @ApiModelProperty(value = "provinceName", notes = "省份名称")
    private String provinceName;
    @ApiModelProperty(value = "cityName", notes = "城市名称")
    private String cityName;
}
