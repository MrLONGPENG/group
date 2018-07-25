package com.lveqia.cloud.zuul.sql;

import com.lveqia.cloud.zuul.model.SysMenu;
import org.apache.ibatis.jdbc.SQL;

/**
 * ,SQL语句组装类
 * 类名:SysMenuSqlProvider
 * @author leolaurel
 * 创建时间:20180725
 */
public class SysMenuSqlProvider {

    public String insert(SysMenu sysMenu){
        return new SQL(){{
            INSERT_INTO("t_sys_menu");
            if(sysMenu.getId()!= null) VALUES("`id`", "#{id}");
            if(sysMenu.getUrl()!= null) VALUES("`url`", "#{url}");
            if(sysMenu.getPath()!= null) VALUES("`path`", "#{path}");
            if(sysMenu.getComponent()!= null) VALUES("`component`", "#{component}");
            if(sysMenu.getName()!= null) VALUES("`name`", "#{name}");
            if(sysMenu.getIconCls()!= null) VALUES("`iconCls`", "#{iconCls}");
            if(sysMenu.getKeepAlive()!= null) VALUES("`keepAlive`", "#{keepAlive}");
            if(sysMenu.getRequireAuth()!= null) VALUES("`requireAuth`", "#{requireAuth}");
            if(sysMenu.getParentId()!= null) VALUES("`parentId`", "#{parentId}");
            if(sysMenu.getEnabled()!= null) VALUES("`enabled`", "#{enabled}");
        }}.toString();
    }



    public String update(SysMenu sysMenu){
        return new SQL(){{
            UPDATE("t_sys_menu");
            if(sysMenu.getId()!= null) SET("`id` = #{id}");
            if(sysMenu.getUrl()!= null) SET("`url` = #{url}");
            if(sysMenu.getPath()!= null) SET("`path` = #{path}");
            if(sysMenu.getComponent()!= null) SET("`component` = #{component}");
            if(sysMenu.getName()!= null) SET("`name` = #{name}");
            if(sysMenu.getIconCls()!= null) SET("`iconCls` = #{iconCls}");
            if(sysMenu.getKeepAlive()!= null) SET("`keepAlive` = #{keepAlive}");
            if(sysMenu.getRequireAuth()!= null) SET("`requireAuth` = #{requireAuth}");
            if(sysMenu.getParentId()!= null) SET("`parentId` = #{parentId}");
            if(sysMenu.getEnabled()!= null) SET("`enabled` = #{enabled}");
            WHERE("id = #{id}");
        }}.toString();
    }
}
