package com.lveqia.cloud.common.objeck.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PageVo implements Serializable {

    @ApiModelProperty(value = "pageNum", notes = "当前页")
    private Integer pageNum = 1;

    @ApiModelProperty(value = "pageSize", notes = "每页显示")
    private Integer pageSize = 10;
}
