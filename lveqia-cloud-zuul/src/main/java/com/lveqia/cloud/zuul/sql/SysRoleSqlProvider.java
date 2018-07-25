package com.lveqia.cloud.zuul.sql;

import com.lveqia.cloud.zuul.model.SysRole;
import org.apache.ibatis.jdbc.SQL;

/**
 * ,SQL语句组装类
 * 类名:SysRoleSqlProvider
 * @author leolaurel
 * 创建时间:20180725
 */
public class SysRoleSqlProvider {

    public String insert(SysRole sysRole){
        return new SQL(){{
            INSERT_INTO("t_sys_role");
            if(sysRole.getId()!= null) VALUES("`id`", "#{id}");
            if(sysRole.getName()!= null) VALUES("`name`", "#{name}");
            if(sysRole.getRemark()!= null) VALUES("`remark`", "#{remark}");
        }}.toString();
    }



    public String update(SysRole sysRole){
        return new SQL(){{
            UPDATE("t_sys_role");
            if(sysRole.getId()!= null) SET("`id` = #{id}");
            if(sysRole.getName()!= null) SET("`name` = #{name}");
            if(sysRole.getRemark()!= null) SET("`remark` = #{remark}");
            WHERE("id = #{id}");
        }}.toString();
    }
}
