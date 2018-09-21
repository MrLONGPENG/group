package com.lveqia.cloud.zuul.mapper;

import com.lveqia.cloud.zuul.model.SysRole;
import com.lveqia.cloud.zuul.sql.SysRoleSqlProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * ,数据库操作接口类
 * 类名:SysRoleMapper
 * @author leolaurel
 * 创建时间:20180725
 */
@Mapper
@Component(value ="sysRoleMapper")
public interface SysRoleMapper {

    @InsertProvider(type = SysRoleSqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    boolean insert(SysRole sysRole);

    @UpdateProvider(type = SysRoleSqlProvider.class, method = "update")
    boolean update(SysRole sysRole);

    @Delete("delete from t_sys_role where id= #{id}")
    int deleteById(int id);

    @Select("SELECT * FROM t_sys_role WHERE id = #{id}")
    @Results(id = "sysRole", value = {
         @Result(id=true, column="id",property="id",javaType=Integer.class)
             ,@Result(column="name",property="name",javaType=String.class)
             ,@Result(column="desc",property="desc",javaType=String.class)
    })
    SysRole findById(Integer id);

    @Select("SELECT * FROM t_sys_role limit 1000")
    @ResultMap("sysRole")
    List<SysRole> findListAll();


    @ResultMap("sysRole")
    @Select("SELECT * FROM t_sys_role WHERE id <> 1")
    List<SysRole> getRoleListByAdmin();

    @ResultMap("sysRole")
    @Select("SELECT r.* FROM t_sys_user_role u,t_sys_role r WHERE u.rid=r.id AND u.uid=#{id}")
    List<SysRole> getRoleListByUid(long id);


    @ResultMap("sysRole")
    @Select("SELECT r.* FROM t_sys_menu_role m,t_sys_role r WHERE m.rid=r.id AND m.mid=#{id}")
    List<SysRole> getRoleListByMid(Integer id);

    @ResultType(Boolean.class)
    @Select("SELECT COUNT(`name`) FROM t_sys_role WHERE `name`=#{name} OR `desc`=#{desc} limit 1")
    boolean exist(@Param("name") String name, @Param("desc")String desc);


    @Results(id = "sysRoleMenu", value = {
            @Result(id=true, column="id",property="id",javaType=Integer.class)
            ,@Result(column="name",property="name",javaType=String.class)
            ,@Result(column="desc",property="desc",javaType=String.class)
            ,@Result(column="id",property="menus", javaType=List.class,
                many=@Many(
                    select="com.lveqia.cloud.zuul.mapper.SysMenuMapper.getMenusByRoleId",
                    fetchType=FetchType.EAGER
            ))
    })
    @Select("SELECT * FROM t_sys_role WHERE id <> 1")
    List<SysRole> getRoleMenuByAdmin();

    @ResultMap("sysRoleMenu")
    @Select("SELECT r.* FROM t_sys_user_role u,t_sys_role r WHERE u.rid=r.id AND u.uid=#{id}")
    List<SysRole> getRoleMenuByUid(long id);


}