package com.lveqia.cloud.zuul.sql;

import com.lveqia.cloud.zuul.model.SysUserRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * ,SQL语句组装类
 * 类名:SysUserRoleSqlProvider
 * @author leolaurel
 * 创建时间:20180725
 */
public class SysUserRoleSqlProvider {

    public String insert(SysUserRole sysUserRole){
        return new SQL(){{
            INSERT_INTO("t_sys_user_role");
            if(sysUserRole.getId()!= null) VALUES("`id`", "#{id}");
            if(sysUserRole.getUid()!= null) VALUES("`uid`", "#{uid}");
            if(sysUserRole.getRid()!= null) VALUES("`rid`", "#{rid}");
        }}.toString();
    }



    public String update(SysUserRole sysUserRole){
        return new SQL(){{
            UPDATE("t_sys_user_role");
            if(sysUserRole.getId()!= null) SET("`id` = #{id}");
            if(sysUserRole.getUid()!= null) SET("`uid` = #{uid}");
            if(sysUserRole.getRid()!= null) SET("`rid` = #{rid}");
            WHERE("id = #{id}");
        }}.toString();
    }

    @SuppressWarnings("unused")
    public String addUidRid(@Param("uid")int uid, @Param("ids") int[] ids){
        return new SQL(){{
            INSERT_INTO("t_sys_user_role");
            INTO_COLUMNS("`uid`","`rid`");
            StringBuffer sb = new StringBuffer();
            for (int rid:ids) {
                if(sb.length() != 0) sb.append("), (");
                sb.append(uid).append(", ").append(rid);
            }
            INTO_VALUES(new String(sb));
        }}.toString();
    }
}
