package com.lveqia.cloud.zuul.service;

import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.objeck.info.UserInfo;
import com.lveqia.cloud.common.objeck.to.PageTo;
import com.lveqia.cloud.zuul.model.SysUser;
import com.lveqia.cloud.zuul.objeck.vo.UserVo;
import com.lveqia.cloud.zuul.objeck.vo.user.AddVo;
import com.lveqia.cloud.zuul.objeck.vo.user.ListVo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * @author leolaurel
 */
public interface SysUserService extends UserDetailsService {

    UserInfo getCurrInfo();

    boolean update(int uid, String name, String telephone, String address, String password);

    boolean modify(int uid, String oldPassword, String newPassword) throws BaseException;

    List<SysUser> getSysUserListByPid(int id);

    PageTo<SysUser> getSysUserList(ListVo listVo);

    int addUser(long id, AddVo userAddVo) throws BaseException;

    int delUser(int uid);

    int putUser(SysUser sysUser);

    SysUser getUser(int uid);

    // TODO: 2018-09-27
    PageTo<UserVo> getUserTreeList(int pid, int pageNum, int pageSize);
}
