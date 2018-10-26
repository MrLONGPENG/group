package com.mujugroup.core.objeck.vo.agent;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
@ApiModel(value = "PutVo", description = "代理商修改Vo")
@Data
public class PutVo implements Serializable {
    @NotNull(message = "代理商更新必须指定ID")
    @ApiModelProperty(value = "id", notes = "主键",required = true)
    private Integer id;
    @ApiModelProperty(value = "name", notes = "代理商名称",required = true)
    @NotBlank(message = "代理商名称不能为空")
    private String name;
    @ApiModelProperty(value = "crtTime", notes = "创建时间")
    private Date crtTime;
    @Range(min = 0, max = 2, message = "状态可选[0,1,2]")
    @ApiModelProperty(value = "enable", notes = "代理商状态 1 启用 2 禁用 0 删除")
    private Integer enable;


}
