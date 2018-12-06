package com.lveqia.cloud.zuul.objeck.vo;

import lombok.Data;

import java.util.List;

@Data
public class MetaVo {

    private boolean keepAlive;
    private boolean requireAuth;
    private List<String> roles;


}
