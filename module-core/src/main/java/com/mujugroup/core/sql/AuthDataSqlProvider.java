package com.mujugroup.core.sql;

import com.mujugroup.core.model.AuthData;
import org.apache.ibatis.jdbc.SQL;

/**
 * 数据权限关系表,SQL语句组装类
 * 类名:AuthDataSqlProvider
 * @author leolaurel
 * 创建时间:20180927
 */
public class AuthDataSqlProvider {

    public String insert(AuthData authData){
        return new SQL(){{
            INSERT_INTO("t_auth_data");
            if(authData.getId()!= null) VALUES("`id`", "#{id}");
            if(authData.getUid()!= null) VALUES("`uid`", "#{uid}");
            if(authData.getRid()!= null) VALUES("`rid`", "#{rid}");
            if(authData.getType()!= null) VALUES("`type`", "#{type}");
        }}.toString();
    }



    public String update(AuthData authData){
        return new SQL(){{
            UPDATE("t_auth_data");
            if(authData.getId()!= null) SET("`id` = #{id}");
            if(authData.getUid()!= null) SET("`uid` = #{uid}");
            if(authData.getRid()!= null) SET("`rid` = #{rid}");
            if(authData.getType()!= null) SET("`type` = #{type}");
            WHERE("id = #{id}");
        }}.toString();
    }
}
