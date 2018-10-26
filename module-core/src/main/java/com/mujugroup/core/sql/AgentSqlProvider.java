package com.mujugroup.core.sql;

import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.core.model.Agent;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * 代理商信息表,SQL语句组装类
 * 类名:AgentSqlProvider
 *
 * @author leolaurel
 * 创建时间:20180927
 */
public class AgentSqlProvider {

    public String insert(Agent agent) {
        return new SQL() {{
            INSERT_INTO("t_agent");
            if (agent.getId() != null) VALUES("`id`", "#{id}");
            if (agent.getName() != null) VALUES("`name`", "#{name}");
            if (agent.getCrtTime() != null) VALUES("`crtTime`", "#{crtTime}");
            if (agent.getEnable() != null) VALUES("`enable`", "#{enable}");
        }}.toString();
    }


    public String update(Agent agent) {
        return new SQL() {{
            UPDATE("t_agent");
            if (agent.getId() != null) SET("`id` = #{id}");
            if (agent.getName() != null) SET("`name` = #{name}");
            if (agent.getCrtTime() != null) SET("`crtTime` = #{crtTime}");
            if (agent.getEnable() != null) SET("`enable` = #{enable}");
            WHERE("id = #{id}");
        }}.toString();
    }

    public String findAll(@Param(value = "name") String name, @Param(value = "enable") int enable) {
        return new SQL() {{
            SELECT("*");
            FROM("t_agent");
            if (enable != 0) {
                WHERE("`enable`= #{enable}");
            } else {
                WHERE("`enable`=1");
            }
            if (!StringUtil.isEmpty(name)) {
                AND().WHERE("name like concat(concat('%',#{name}),'%')");
            }
        }}.toString();
    }
}
