package com.lveqia.cloud.zuul.service;

import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.objeck.info.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author leolaurel
 */
public interface SysUserService extends UserDetailsService {

    UserInfo getCurrInfo();

    boolean register(String username, String password, String phone) throws BaseException;

    boolean update(UserInfo userInfo, String name, String telephone, String address, String password);

    boolean modify(UserInfo userInfo, String oldPassword, String newPassword) throws BaseException;
}
