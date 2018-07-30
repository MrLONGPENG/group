package com.lveqia.cloud.zuul.service;

import com.lveqia.cloud.zuul.model.SysUser;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author leolaurel
 */
public interface SysUserService extends UserDetailsService {

    SysUser getCurrInfo();
}
