package com.lveqia.cloud.zuul.objeck.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AddMenuVo implements Serializable {
    @NotNull(message = "url不能为空")
    private String url;
    @NotNull(message = "path不能为空")
    private String path;
    @NotNull(message = "component不能为空")
    private String component;
    @NotNull(message = "菜单名称不能为空")
    private String name;
    @NotNull(message = "菜单图标不能为空")
    private String iconCls;
    private Integer parentId;
    private Integer sort;
}
