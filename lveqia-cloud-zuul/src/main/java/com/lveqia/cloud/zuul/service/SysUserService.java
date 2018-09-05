package com.lveqia.cloud.zuul.service;

import com.lveqia.cloud.common.exception.OtherException;
import com.lveqia.cloud.zuul.model.SysUser;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author leolaurel
 */
public interface SysUserService extends UserDetailsService {

    SysUser getCurrInfo();

    boolean register(String username, String password, String phone) throws OtherException;

    boolean update(SysUser sysUser, String name, String telephone, String address, String password);
}
