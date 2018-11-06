package com.lveqia.cloud.zuul.service;

import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.objeck.info.UserInfo;
import com.lveqia.cloud.zuul.model.SysUser;
import com.lveqia.cloud.zuul.objeck.vo.UserVo;
import com.lveqia.cloud.zuul.objeck.vo.user.UserAddVo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * @author leolaurel
 */
public interface SysUserService extends UserDetailsService {

    UserInfo getCurrInfo();

    boolean update(UserInfo userInfo, String name, String telephone, String address, String password);

    boolean modify(UserInfo userInfo, String oldPassword, String newPassword) throws BaseException;

    List<SysUser> getSysUserListByPid(int id);

    List<SysUser> getSysUserList(boolean fuzzy, String name, String username);

    int addUser(long id, UserAddVo userAddVo) throws BaseException;

    int delUser(int uid);

    int putUser(SysUser sysUser);

    SysUser getUser(int uid);

    // TODO: 2018-09-27
    List<UserVo> getUserTreeList(int pid);
}
