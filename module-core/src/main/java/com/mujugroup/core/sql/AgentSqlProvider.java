package com.mujugroup.core.sql;

import com.mujugroup.core.model.Agent;
import org.apache.ibatis.jdbc.SQL;

/**
 * 代理商信息表,SQL语句组装类
 * 类名:AgentSqlProvider
 * @author leolaurel
 * 创建时间:20180927
 */
public class AgentSqlProvider {

    public String insert(Agent agent){
        return new SQL(){{
            INSERT_INTO("t_agent");
            if(agent.getId()!= null) VALUES("`id`", "#{id}");
            if(agent.getName()!= null) VALUES("`name`", "#{name}");
            if(agent.getCrtTime()!= null) VALUES("`crtTime`", "#{crtTime}");
            if(agent.getEnable()!= null) VALUES("`enable`", "#{enable}");
        }}.toString();
    }



    public String update(Agent agent){
        return new SQL(){{
            UPDATE("t_agent");
            if(agent.getId()!= null) SET("`id` = #{id}");
            if(agent.getName()!= null) SET("`name` = #{name}");
            if(agent.getCrtTime()!= null) SET("`crtTime` = #{crtTime}");
            if(agent.getEnable()!= null) SET("`enable` = #{enable}");
            WHERE("id = #{id}");
        }}.toString();
    }
}
