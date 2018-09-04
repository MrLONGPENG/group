package com.lveqia.cloud.zuul.mapper;

import com.lveqia.cloud.zuul.model.SysUser;
import com.lveqia.cloud.zuul.sql.SysUserSqlProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * ,数据库操作接口类
 * 类名:SysUserMapper
 * @author leolaurel
 * 创建时间:20180725
 */
@Mapper
@Component(value ="sysUserMapper")
public interface SysUserMapper {

    @InsertProvider(type = SysUserSqlProvider.class, method = "insert")
    boolean insert(SysUser sysUser);

    @UpdateProvider(type = SysUserSqlProvider.class, method = "update")
    boolean update(SysUser sysUser);

    @Delete("delete from t_sys_user where id= #{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_sys_user WHERE id = #{id}")
    @Results(id = "sysUser", value = {
         @Result(id=true, column="id",property="id",javaType=Integer.class)
             ,@Result(column="name",property="name",javaType=String.class)
             ,@Result(column="phone",property="phone",javaType=String.class)
             ,@Result(column="telephone",property="telephone",javaType=String.class)
             ,@Result(column="address",property="address",javaType=String.class)
             ,@Result(column="enabled",property="enabled",javaType=Boolean.class)
             ,@Result(column="username",property="username",javaType=String.class)
             ,@Result(column="password",property="password",javaType=String.class)
             ,@Result(column="avatar_url",property="avatarUrl",javaType=String.class)
             ,@Result(column="remark",property="remark",javaType=String.class)
             ,@Result(column="id",property="roles",
                many=@Many(
                        select="com.lveqia.cloud.zuul.mapper.SysRoleMapper.findListByUid",
                        fetchType=FetchType.EAGER
                ))
    })
    SysUser findById(Integer id);

    @Select("SELECT * FROM t_sys_user limit 1000")
    @ResultMap("sysUser")
    List<SysUser> findListAll();

    @ResultMap("sysUser")
    @Select("SELECT * FROM t_sys_user WHERE phone = #{phone}")
    SysUser loadUserByPhone(@Param("phone") String phone);

    @ResultMap("sysUser")
    @Select("SELECT * FROM t_sys_user WHERE username = #{username}")
    SysUser loadUserByUsername(@Param("username") String username);


}