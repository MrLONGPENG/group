package com.mujugroup.lock.objeck.vo.fail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(value = "PutVo", description = "故障维修状态的VO")
public class PutVo implements Serializable {
    @NotNull(message = "故障更新必须指定id")
    @ApiModelProperty(value = "id", notes = "主键ID")
    private long id;
    @ApiModelProperty(value = "type", notes = "故障解决状态(4:已解决 8:未解决)")
    @Range(min = 4, max = 8, message = "状态可选[4,8]")
    private int type;
    @ApiModelProperty(value = "resolveMan", notes = "可选的维修人员")
    private int resolveMan;
    @ApiModelProperty(value = "explain", notes = "异常产生原因及解决方法")
    private String explain;
}
