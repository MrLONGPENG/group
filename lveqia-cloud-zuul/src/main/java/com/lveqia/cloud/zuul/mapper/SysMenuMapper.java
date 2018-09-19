package com.lveqia.cloud.zuul.mapper;

import com.lveqia.cloud.zuul.model.SysMenu;
import com.lveqia.cloud.zuul.objeck.vo.MenuVO;
import com.lveqia.cloud.zuul.sql.SysMenuSqlProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * ,数据库操作接口类
 * 类名:SysMenuMapper
 * @author leolaurel
 * 创建时间:20180725
 */
@Mapper
@Component(value ="sysMenuMapper")
public interface SysMenuMapper {

    @InsertProvider(type = SysMenuSqlProvider.class, method = "insert")
    boolean insert(SysMenu sysMenu);

    @UpdateProvider(type = SysMenuSqlProvider.class, method = "update")
    boolean update(SysMenu sysMenu);

    @Delete("delete from t_sys_menu where id= #{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_sys_menu WHERE id = #{id}")
    @Results(id = "sysMenu", value = {
         @Result(id=true, column="id",property="id",javaType=Integer.class)
             ,@Result(column="url",property="url",javaType=String.class)
             ,@Result(column="path",property="path",javaType=String.class)
             ,@Result(column="component",property="component",javaType=String.class)
             ,@Result(column="name",property="name",javaType=String.class)
             ,@Result(column="iconCls",property="iconCls",javaType=String.class)
             ,@Result(column="keepAlive",property="keepAlive",javaType=Boolean.class)
             ,@Result(column="requireAuth",property="requireAuth",javaType=Boolean.class)
             ,@Result(column="parentId",property="parentId",javaType=Integer.class)
             ,@Result(column="enabled",property="enabled",javaType=Boolean.class)
             ,@Result(column="id",property="roles",
                many=@Many(
                    select="com.lveqia.cloud.zuul.mapper.SysRoleMapper.getRoleListByMid",
                    fetchType=FetchType.LAZY
             ))
    })
    SysMenu findById(Integer id);

    @ResultMap("sysMenu")
    @Select("SELECT * FROM t_sys_menu WHERE `enabled`=true")
    List<SysMenu> findListAll();

    @ResultMap("sysMenu")
    @Select("SELECT * FROM t_sys_menu WHERE `enabled`=true AND `parentId` IS NULL")
    List<SysMenu> getMainMenus();

    @ResultMap("sysMenu")
    @Select("SELECT * FROM t_sys_menu WHERE `enabled`=true AND `parentId` IS NOT NULL")
    List<SysMenu> getMenusByAdmin();

    @ResultMap("sysMenu")
    @Select("SELECT * FROM t_sys_menu WHERE `enabled`=true AND `parentId` IS NOT NULL ORDER BY LENGTH(url) DESC")
    List<SysMenu> getAllMenuByLength();

    @ResultMap("sysMenu")
    @Select("SELECT * FROM t_sys_menu WHERE `id` IN(SELECT mr.`mid` FROM t_sys_user_role ur,t_sys_menu_role mr " +
            " WHERE ur.`rid`=mr.`rid` AND ur.`uid` =#{uid}) AND `enabled`=true AND `parentId` IS NOT NULL")
    List<SysMenu> getMenusByUserId(@Param("uid") Long uid);


    @ResultMap("sysMenu")
    @Select("SELECT * FROM t_sys_menu WHERE `id` IN(SELECT mr.`mid` FROM t_sys_user_role ur,t_sys_menu_role mr " +
            " WHERE ur.`rid`=mr.`rid` AND ur.`uid` =#{rid}) AND `enabled`=true AND `parentId` IS NOT NULL")
    List<SysMenu> getMenusByRoleId(@Param("rid") Long rid);



}