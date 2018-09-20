package com.lveqia.cloud.zuul.sql;

import com.lveqia.cloud.common.util.StringUtil;
import com.lveqia.cloud.zuul.model.SysUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * ,SQL语句组装类
 * 类名:SysUserSqlProvider
 * @author leolaurel
 * 创建时间:20180725
 */
public class SysUserSqlProvider {

    public String insert(SysUser sysUser){
        return new SQL(){{
            INSERT_INTO("t_sys_user");
            if(sysUser.getId()!= null) VALUES("`id`", "#{id}");
            if(sysUser.getName()!= null) VALUES("`name`", "#{name}");
            if(sysUser.getPhone()!= null) VALUES("`phone`", "#{phone}");
            if(sysUser.getEmail()!= null) VALUES("`email`", "#{email}");
            if(sysUser.getAddress()!= null) VALUES("`address`", "#{address}");
            if(sysUser.isEnabled()) VALUES("`enabled`", "#{enabled}");
            if(sysUser.getUsername()!= null) VALUES("`username`", "#{username}");
            if(sysUser.getPassword()!= null) VALUES("`password`", "#{password}");
            if(sysUser.getAvatarUrl()!= null) VALUES("`avatar_url`", "#{avatarUrl}");
            if(sysUser.getRemark()!= null) VALUES("`remark`", "#{remark}");
            if(sysUser.getCrtId()!= null) VALUES("`crt_id`", "#{crtId}");
            if(sysUser.getCrtTime()!= null) VALUES("`crt_time`", "#{crtTime}");
        }}.toString();
    }



    public String update(SysUser sysUser){
        return new SQL(){{
            UPDATE("t_sys_user");
            SET("`enabled` = #{enabled}");
            if(sysUser.getName()!= null) SET("`name` = #{name}");
            if(sysUser.getPhone()!= null) SET("`phone` = #{phone}");
            if(sysUser.getEmail()!= null) SET("`email` = #{email}");
            if(sysUser.getAddress()!= null) SET("`address` = #{address}");
            if(sysUser.getUsername()!= null) SET("`username` = #{username}");
            if(sysUser.getPassword()!= null) SET("`password` = #{password}");
            if(sysUser.getAvatarUrl()!= null) SET("`avatar_url` = #{avatarUrl}");
            if(sysUser.getRemark()!= null) SET("`remark` = #{remark}");
            if(sysUser.getCrtId()!= null) SET("`crt_id` = #{crtId}");
            if(sysUser.getCrtTime()!= null) SET("`crt_time` = #{crtTime}");
            WHERE("id = #{id}");
        }}.toString();
    }

    /**
     * 关键字查询系统用户
     */
    @SuppressWarnings("unused")
    public String  getSysUserList(boolean fuzzy,@Param("name") String name, @Param("username") String username){
        return new SQL(){{
            SELECT("*");
            FROM("t_sys_user");
            WHERE("id <> 1"); // 过滤系统管理员
            if(!StringUtil.isEmpty(name) && !"all".equals(name)){
                if(fuzzy){
                    AND().WHERE("`name` like concat('%',#{name},'%')");
                }else{
                    AND().WHERE("`name` = #{name}");
                }
            }
            if(!StringUtil.isEmpty(username) && !"all".equals(username)){
                if(fuzzy){
                    AND().WHERE("`username` like concat('%',#{username},'%')");
                }else{
                    AND().WHERE("`username` = #{username}");
                }
            }
        }}.toString();
    }
}
