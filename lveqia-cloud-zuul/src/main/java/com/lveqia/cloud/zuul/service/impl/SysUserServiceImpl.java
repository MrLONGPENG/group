package com.lveqia.cloud.zuul.service.impl;
import com.lveqia.cloud.common.config.CoreConfig;
import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.objeck.info.UserInfo;
import com.lveqia.cloud.common.util.ResultUtil;
import com.lveqia.cloud.common.util.StringUtil;
import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.zuul.mapper.SysUserMapper;
import com.lveqia.cloud.zuul.model.SysUser;
import com.lveqia.cloud.zuul.objeck.vo.UserVO;
import com.lveqia.cloud.zuul.objeck.vo.user.UserAddVo;
import com.lveqia.cloud.zuul.service.SysUserRoleService;
import com.lveqia.cloud.zuul.service.SysUserService;
import com.lveqia.cloud.zuul.service.feign.ModuleCoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

    private final SysUserMapper sysUserMapper;
    private final SysUserRoleService sysUserRoleService;
    private final ModuleCoreService moduleCoreService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    public SysUserServiceImpl(SysUserMapper sysUserMapper, SysUserRoleService sysUserRoleService,ModuleCoreService moduleCoreService) {
        this.sysUserMapper = sysUserMapper;
        this.sysUserRoleService = sysUserRoleService;
        this.moduleCoreService=moduleCoreService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails;
        if (StringUtil.isNumeric(username)) {
            userDetails = sysUserMapper.loadUserByPhone(username);
        } else {
            userDetails = sysUserMapper.loadUserByUsername(username);
        }
        if (userDetails == null) {
            throw new UsernameNotFoundException("用户名不对");
        }
        return userDetails;
    }


    @Override
    public UserInfo getCurrInfo() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserInfo) {
            logger.debug(((UserInfo) principal).getUsername());
            return (UserInfo) principal;
        }
        return null; // anonymousUser
    }


    @Override
    public boolean modify(UserInfo userInfo, String oldPassword, String newPassword) throws BaseException {
        SysUser user = (SysUser) loadUserByUsername(userInfo.getUsername());
        if (!encoder.matches(oldPassword, user.getPassword())) {
            throw new BaseException(ResultUtil.CODE_VALIDATION_FAIL, "原始密码错误，无法修改");
        }

        user.setPassword(encoder.encode(newPassword));
        return sysUserMapper.update(user) == 1;
    }

    @Override
    public List<SysUser> getSysUserListByPid(int id) {
        return sysUserMapper.getSysUserListByPid(id);
    }

    @Override
    public List<SysUser> getSysUserList(boolean fuzzy, String name, String username) {
        return sysUserMapper.getSysUserList(fuzzy, name, username);
    }

    @Override
    @Transactional
    public int addUser(long crtId, UserAddVo userAddVo) throws BaseException {
        if(userAddVo.getUsername() == null) throw new ParamException("缺少必要参数[username]");
        if(userAddVo.getPassword() == null) throw new ParamException("缺少必要参数[password]");
        if(userAddVo.getPhone() == null) throw new ParamException("缺少必要参数[phone]");
        if(userAddVo.getEmail() == null) throw new ParamException("缺少必要参数[email]");
        if(userAddVo.getName() == null) throw new ParamException("缺少必要参数[name]");
        if(userAddVo.getType() == null) throw new ParamException("缺少必要参数[type]");
        if (StringUtil.isNumeric(userAddVo.getUsername())) {
            throw new BaseException(ResultUtil.CODE_REQUEST_FORMAT, "用户名不能全为数字");
        }
        if (!StringUtil.isNumeric(userAddVo.getPhone())) {
            throw new BaseException(ResultUtil.CODE_REQUEST_FORMAT, "手机号只能全部为数字");
        }
        if (sysUserMapper.loadUserByUsername(userAddVo.getUsername()) != null) {
            throw new BaseException(ResultUtil.CODE_DATA_DUPLICATION, "用户名重复，注册失败!");
        }
        if (sysUserMapper.loadUserByPhone(userAddVo.getPhone()) != null) {
            throw new BaseException(ResultUtil.CODE_DATA_DUPLICATION, "手机号码已注册，注册失败!");
        }
        if(userAddVo.getType() == 0){ // 若果是添加系统账号，需要判断当前用户是否拥有全部权限
            Map<String, String> map = moduleCoreService.addAuthData((int) crtId);
            if(!map.containsKey(CoreConfig.AUTH_DATA_ALL))
                throw new BaseException(ResultUtil.CODE_UNAUTHORIZED, "此用户无权添加系统用户!");
            if(map.size() > 1) logger.warn("注意：拥有全部数据权限记录大于一条");
            userAddVo.setAuthData(new String[]{"ALL0"}); // 添加全部权限的用户
        }
        SysUser sysUser = getSysUser((int) crtId, userAddVo);
        int result = sysUserMapper.insert(sysUser);
        //获取当前注册成功后的用户ID
        int id = sysUser.getId();
        try {
            if (userAddVo.getAuthData() != null && userAddVo.getAuthData().length > 0) {
                moduleCoreService.addAuthData(id, userAddVo.getAuthData());
            }
            if(userAddVo.getRoles()!=null && userAddVo.getRoles().length >0){
                sysUserRoleService.putRidToUid(id, userAddVo.getRoles());
            }

        }catch (Exception e){
            logger.debug("add has error, delete");
            sysUserMapper.deleteById(id);
        }
        return result;
    }

    private SysUser getSysUser(int crtId, UserAddVo userAddVo) {
        SysUser sysUser = new SysUser();
        sysUser.setName(userAddVo.getName());
        sysUser.setPhone(userAddVo.getPhone());
        sysUser.setEmail(userAddVo.getEmail());
        sysUser.setRemark(userAddVo.getRemark());
        sysUser.setAddress(userAddVo.getAddress());
        sysUser.setUsername(userAddVo.getUsername());
        sysUser.setAvatarUrl(userAddVo.getAvatarUrl());
        sysUser.setCrtId(crtId);
        sysUser.setPassword(new BCryptPasswordEncoder().encode(userAddVo.getPassword()));
        return sysUser;
    }


    @Override
    @Transactional
    public int delUser(int uid) {
        int result = sysUserRoleService.delUserRoleByUid(uid);
        logger.debug("当前用户引用的{}个角色已删除", result);
        return sysUserMapper.deleteById(uid);
    }

    @Override
    public int putUser(SysUser sysUser) {
        return sysUserMapper.update(sysUser);
    }

    @Override
    public SysUser getUser(int uid) {
        return sysUserMapper.findById(uid);
    }


    @Override
    public boolean update(UserInfo userInfo, String name, String email, String address, String password) {
        SysUser sysUser = (SysUser) loadUserByUsername(userInfo.getUsername());
        if (!StringUtil.isEmpty(name)) sysUser.setName(name);
        if (!StringUtil.isEmpty(email)) sysUser.setEmail(email);
        if (!StringUtil.isEmpty(address)) sysUser.setAddress(address);
        if (!StringUtil.isEmpty(password)) sysUser.setPassword(encoder.encode(password));
        return sysUserMapper.update(sysUser) == 1;
    }

    // TODO: 2018-09-27
    public List<UserVO> getUserTreeList(int pid) {
        UserVO tree;
        List<UserVO> trees = new ArrayList<UserVO>();
        List<SysUser> list = sysUserMapper.getSysUserListByPid(pid);
        for (SysUser sysUser : list) {
            tree = new UserVO();
            tree.setSysUser(sysUser);
            tree.setChildren(getUserTreeList(sysUser.getId()));
            trees.add(tree);
        }
        return trees;
    }


}
