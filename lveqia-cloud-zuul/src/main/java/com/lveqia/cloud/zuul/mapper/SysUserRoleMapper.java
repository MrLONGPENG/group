package com.lveqia.cloud.zuul.mapper;

import com.lveqia.cloud.zuul.model.SysUserRole;
import com.lveqia.cloud.zuul.sql.SysUserRoleSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * ,数据库操作接口类
 * 类名:SysUserRoleMapper
 * @author leolaurel
 * 创建时间:20180725
 */
@Mapper
@Component(value ="sysUserRoleMapper")
public interface SysUserRoleMapper {

    @InsertProvider(type = SysUserRoleSqlProvider.class, method = "insert")
    boolean insert(SysUserRole sysUserRole);

    @UpdateProvider(type = SysUserRoleSqlProvider.class, method = "update")
    boolean update(SysUserRole sysUserRole);

    @Delete("delete from t_sys_user_role where id= #{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_sys_user_role WHERE id = #{id}")
    @Results(id = "sysUserRole", value = {
         @Result(id=true, column="id",property="id",javaType=Integer.class)
             ,@Result(column="uid",property="uid",javaType=Integer.class)
             ,@Result(column="rid",property="rid",javaType=Integer.class)
    })
    SysUserRole findById(Integer id);

    @Select("SELECT * FROM t_sys_user_role limit 1000")
    @ResultMap("sysUserRole")
    List<SysUserRole> findListAll();


    @Delete("DELETE FROM t_sys_user_role WHERE `uid`= #{uid} AND `rid`= #{rid}")
    boolean delUserRole(@Param("uid") long uid, @Param("rid") int rid);


    @ResultType(Integer.class)
    @Select("SELECT count(*) FROM t_sys_user_role WHERE `rid`=#{rid}")
    int getUserCountByRid(@Param("rid") int rid);
}