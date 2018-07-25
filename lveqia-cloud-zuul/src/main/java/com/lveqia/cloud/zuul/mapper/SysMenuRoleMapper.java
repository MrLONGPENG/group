package com.lveqia.cloud.zuul.mapper;

import com.lveqia.cloud.zuul.model.SysMenuRole;
import com.lveqia.cloud.zuul.sql.SysMenuRoleSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * ,数据库操作接口类
 * 类名:SysMenuRoleMapper
 * @author leolaurel
 * 创建时间:20180725
 */
@Mapper
@Component(value ="sysMenuRoleMapper")
public interface SysMenuRoleMapper {

    @InsertProvider(type = SysMenuRoleSqlProvider.class, method = "insert")
    boolean insert(SysMenuRole sysMenuRole);

    @UpdateProvider(type = SysMenuRoleSqlProvider.class, method = "update")
    boolean update(SysMenuRole sysMenuRole);

    @Delete("delete from t_sys_menu_role where id= #{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_sys_menu_role WHERE id = #{id}")
    @Results(id = "sysMenuRole", value = {
         @Result(id=true, column="id",property="id",javaType=Integer.class)
             ,@Result(column="mid",property="mid",javaType=Integer.class)
             ,@Result(column="rid",property="rid",javaType=Integer.class)
    })
    SysMenuRole findById(Integer id);

    @Select("SELECT * FROM t_sys_menu_role limit 1000")
    @ResultMap("sysMenuRole")
    List<SysMenuRole> findListAll();

}