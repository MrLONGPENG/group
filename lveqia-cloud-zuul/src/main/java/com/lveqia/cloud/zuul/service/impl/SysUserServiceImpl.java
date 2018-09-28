package com.lveqia.cloud.zuul.service.impl;
import com.lveqia.cloud.common.objeck.info.UserInfo;
import com.lveqia.cloud.common.util.ResultUtil;
import com.lveqia.cloud.common.util.StringUtil;
import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.zuul.mapper.SysUserMapper;
import com.lveqia.cloud.zuul.model.SysUser;
import com.lveqia.cloud.zuul.objeck.vo.UserVO;
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
    public int addUser(long crtId, String username, String name, String phone, String email, String password
            , String address, String avatarUrl, String remark, int[] roles, String[] authDatas) throws BaseException {
        if (StringUtil.isNumeric(username)) {
            throw new BaseException(ResultUtil.CODE_REQUEST_FORMAT, "用户名不能全为数字");
        }
        if (!StringUtil.isNumeric(phone)) {
            throw new BaseException(ResultUtil.CODE_REQUEST_FORMAT, "手机号只能全部为数字");
        }
        if (sysUserMapper.loadUserByUsername(username) != null) {
            throw new BaseException(ResultUtil.CODE_DATA_DUPLICATION, "用户名重复，注册失败!");
        }
        if (sysUserMapper.loadUserByPhone(phone) != null) {
            throw new BaseException(ResultUtil.CODE_DATA_DUPLICATION, "手机号码已注册，注册失败!");
        }
        SysUser sysUser = getSysUser((int) crtId, username, name, phone, email, password, address, avatarUrl, remark);
        int result = sysUserMapper.insert(sysUser);
        //获取当前注册成功后的用户ID
        int id = sysUser.getId();
        if (authDatas != null && authDatas.length > 0) {
          addAuthData(id,authDatas);
        }
        addUserRole(id, roles);

        return result;
    }

    private SysUser getSysUser(int crtId, String username, String name, String phone, String email
            , String password, String address, String avatarUrl, String remark) {
        SysUser sysUser = new SysUser();
        sysUser.setName(name);
        sysUser.setPhone(phone);
        sysUser.setEmail(email);
        sysUser.setRemark(remark);
        sysUser.setAddress(address);
        sysUser.setUsername(username);
        sysUser.setAvatarUrl(avatarUrl);
        sysUser.setCrtId(crtId);
        sysUser.setPassword(new BCryptPasswordEncoder().encode(password));
        return sysUser;
    }

    /*
    将当前添加的用户加入角色
     */
    private void addUserRole(int uid, int[] rid) {
        sysUserRoleService.putRidToUid(uid, rid);
    }

    public void addAuthData(int uid,String[] authDatas){

         moduleCoreService.addAuthData(uid,authDatas);
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
            tree.setId(sysUser.getId());
            tree.setName(sysUser.getName());
            tree.setPhone(sysUser.getPhone());
            tree.setAddress(sysUser.getAddress());
            tree.setAvatarUrl(sysUser.getAvatarUrl());
            tree.setRemark(sysUser.getRemark());
            tree.setRoles(sysUser.getRoles());
            tree.setUsername(sysUser.getUsername());
            tree.setCrtId(sysUser.getCrtId());
            tree.setCrtTime(sysUser.getCrtTime());
            tree.setChildren(getUserTreeList(sysUser.getId()));
            trees.add(tree);
        }
        return trees;
    }


}
