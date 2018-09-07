package com.lveqia.cloud.zuul.service.impl;

import com.lveqia.cloud.common.ResultUtil;
import com.lveqia.cloud.common.StringUtil;
import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.zuul.mapper.SysUserMapper;
import com.lveqia.cloud.zuul.model.SysUser;
import com.lveqia.cloud.zuul.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;


@Transactional
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

    private final SysUserMapper sysUserMapper;
    private final BCryptPasswordEncoder encoder=  new BCryptPasswordEncoder();
    private final Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);
    @Autowired
    public SysUserServiceImpl(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails;
        if(StringUtil.isNumeric(username)){
            userDetails = sysUserMapper.loadUserByPhone(username);
        }else {
            userDetails = sysUserMapper.loadUserByUsername(username);
        }
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

    @Override
    public boolean register(String username, String password, String phone) throws BaseException {
        if(StringUtil.isNumeric(username)){
            throw new BaseException(ResultUtil.CODE_REQUEST_FORMAT, "用户名不能全为数字");
        }
        if(!StringUtil.isNumeric(phone)){
            throw new BaseException(ResultUtil.CODE_REQUEST_FORMAT, "手机号只能全部为数字");
        }
        if (sysUserMapper.loadUserByUsername(username) != null) {
            throw new BaseException(ResultUtil.CODE_DATA_DUPLICATION, "用户名重复，注册失败!");
        }
        if (sysUserMapper.loadUserByPhone(phone) != null) {
            throw new BaseException(ResultUtil.CODE_DATA_DUPLICATION, "手机号码已注册，注册失败!");
        }
        SysUser sysUser = new SysUser();
        sysUser.setPhone(phone);
        sysUser.setUsername(username);
        sysUser.setPassword(new BCryptPasswordEncoder().encode(password));
        return sysUserMapper.insert(sysUser);
    }

    @Override
    public boolean modify(SysUser sysUser, String oldPassword, String newPassword) throws BaseException {
        String password = encoder.encode(oldPassword);
        if(encoder.matches(password, sysUser.getPassword())){
            throw new BaseException(ResultUtil.CODE_VALIDATION_FAIL, "原始密码错误，无法修改");
        }
        sysUser.setPassword(password);
        return sysUserMapper.update(sysUser);
    }


    @Override
    public boolean update(SysUser sysUser, String name, String email, String address, String password){
        if(!StringUtil.isEmpty(name)) sysUser.setName(name);
        if(!StringUtil.isEmpty(email)) sysUser.setEmail(email);
        if(!StringUtil.isEmpty(address)) sysUser.setAddress(address);
        if(!StringUtil.isEmpty(password)) sysUser.setPassword(encoder.encode(password));
        return sysUserMapper.update(sysUser);
    }



}
