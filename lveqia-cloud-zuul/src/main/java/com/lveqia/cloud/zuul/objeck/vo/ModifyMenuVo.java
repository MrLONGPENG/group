package com.lveqia.cloud.zuul.objeck.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(value = "ModifyMenuVo", description = "菜单编辑的Vo")
public class ModifyMenuVo implements Serializable {
    @NotNull(message = "要编辑的菜单ID不能为空")
    private Integer id;
    private String url;
    private String path;
    private String component;
    private String name;
    private String iconCls;
    private Integer parentId;
    private Integer sort;

}
