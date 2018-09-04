package com.lveqia.cloud.zuul.service.impl;

import com.lveqia.cloud.zuul.mapper.SysUserMapper;
import com.lveqia.cloud.zuul.model.SysUser;
import com.lveqia.cloud.zuul.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;


@Transactional
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

    private final SysUserMapper sysUserMapper;
    private final Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);
    @Autowired
    public SysUserServiceImpl(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = sysUserMapper.loadUserByUsername(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException("用户名不对");
        }
        return userDetails;
    }


    @Override
    public SysUser getCurrInfo() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            logger.debug(((UserDetails) principal).getUsername());
            return (SysUser) principal;
        }
        if (principal instanceof Principal) {
            logger.debug( ((Principal) principal).getName());
        }
        return null; // anonymousUser
    }
}
