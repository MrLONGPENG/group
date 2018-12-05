package com.mujugroup.wx.config;

import com.lveqia.cloud.common.config.AuthFilter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@Component
public class WxAuthFilter extends AuthFilter {
    public WxAuthFilter() { // 指定需要权限的Url
        super(new HashSet<>(Arrays.asList("/deposit/list","/goods/*", "/uptime/*","/order/refund"))
                , AuthFilter.MODE_REFUSE);
    }
}
