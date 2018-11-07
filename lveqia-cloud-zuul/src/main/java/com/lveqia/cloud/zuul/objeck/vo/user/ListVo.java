package com.lveqia.cloud.zuul.objeck.vo.user;

import com.lveqia.cloud.common.objeck.vo.PageVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "listVo", description = "系统用户查询Vo")
public class ListVo extends PageVo {

    @ApiModelProperty(value = "是否模糊查询")
    private boolean fuzzy;

    @ApiModelProperty(value = "登陆账号")
    private String username;

    @ApiModelProperty(value = "姓名(昵称)")
    private String name;

}
