package com.mujugroup.core.objeck.vo.hospital;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
@Data
@ApiModel(value = "AddVo", description = "医院添加Vo")
public class AddVo implements Serializable {
    @ApiModelProperty(value = "id", notes = "医院编号")
    private Integer id;
    @NotBlank(message = "医院名称不能为空")
    @ApiModelProperty(value = "name", notes = "医院名称", required = true)
    private String name;
    @NotBlank(message = "代理商编号不能为空")
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
}
