package com.lveqia.cloud.zuul.objeck.vo;

import lombok.Data;

import java.util.List;

@Data
public class MenuVo {

    private Integer id;
    private String url;
    private String path;
    private String component;
    private String name;
    private String iconCls;
    private Integer parentId;
    private List<MenuVo> children;
    private MetaVo meta;
}
