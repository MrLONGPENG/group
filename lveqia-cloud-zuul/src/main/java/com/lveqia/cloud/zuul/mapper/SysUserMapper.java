package com.lveqia.cloud.zuul.mapper;

import com.lveqia.cloud.common.objeck.DBMap;
import com.lveqia.cloud.zuul.model.SysUser;
import com.lveqia.cloud.zuul.sql.SysUserSqlProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ,数据库操作接口类
 * 类名:SysUserMapper
 *
 * @author leolaurel
 * 创建时间:20180725
 */
@Mapper
@Component(value = "sysUserMapper")
public interface SysUserMapper {

    @InsertProvider(type = SysUserSqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(SysUser sysUser);

    @UpdateProvider(type = SysUserSqlProvider.class, method = "update")
    int update(SysUser sysUser);

    @Delete("delete from t_sys_user where id= #{id}")
    int deleteById(int id);

    @Select("SELECT * FROM t_sys_user WHERE id = #{id}")
    @Results(id = "sysUser", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class)
            , @Result(column = "name", property = "name", javaType = String.class)
            , @Result(column = "phone", property = "phone", javaType = String.class)
            , @Result(column = "telephone", property = "telephone", javaType = String.class)
            , @Result(column = "address", property = "address", javaType = String.class)
            , @Result(column = "enabled", property = "enabled", javaType = Boolean.class)
            , @Result(column = "username", property = "username", javaType = String.class)
            , @Result(column = "password", property = "password", javaType = String.class)
            , @Result(column = "avatar_url", property = "avatarUrl", javaType = String.class)
            , @Result(column = "remark", property = "remark", javaType = String.class)
            , @Result(column = "crt_id", property = "crtId", javaType = Integer.class)
            , @Result(column = "crt_time", property = "crtTime", javaType = Date.class)
            , @Result(column = "email", property = "email", javaType = String.class)
            , @Result(column = "id", property = "roles",
            many = @Many(
                    select = "com.lveqia.cloud.zuul.mapper.SysRoleMapper.getRoleListByUid",
                    fetchType = FetchType.EAGER
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

    @ResultMap("sysUser")
    @Select("SELECT * FROM t_sys_user WHERE `crt_id` = #{uid}")
    List<SysUser> getSysUserListByPid(@Param("uid") int uid);

    @ResultMap("sysUser")
    @SelectProvider(type = SysUserSqlProvider.class, method = "getSysUserList")
    List<SysUser> getSysUserList(boolean fuzzy, @Param("name") String name, @Param("username") String username);

    @Select("SELECT `name`  FROM t_sys_user WHERE id=#{id}")
    @ResultType(String.class)
    String getNameById(@Param(value = "id") String id);

}