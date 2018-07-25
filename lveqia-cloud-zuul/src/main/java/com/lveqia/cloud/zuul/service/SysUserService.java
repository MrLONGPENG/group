package com.lveqia.cloud.zuul.service;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author leolaurel
 */
public interface SysUserService extends UserDetailsService {
    String test();
}
