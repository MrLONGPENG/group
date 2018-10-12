package com.mujugroup.wx.sql;

import com.mujugroup.wx.model.WxUptime;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * 木巨柜运行时间表,SQL语句组装类
 * 类名:WxUptimeSqlProvider
 * @author leolaurel
 * 创建时间:20180712
 */
public class WxUptimeSqlProvider {

    public String insert(WxUptime wxUptime){
        return new SQL(){{
            INSERT_INTO("t_wx_uptime");
            if(wxUptime.getId()!= null) VALUES("`id`", "#{id}");
            if(wxUptime.getStartTime()!= null) VALUES("`start_time`", "#{startTime}");
            if(wxUptime.getStopTime()!= null) VALUES("`stop_time`", "#{stopTime}");
            if(wxUptime.getStartDesc()!= null) VALUES("`start_desc`", "#{startDesc}");
            if(wxUptime.getStopDesc()!= null) VALUES("`stop_desc`", "#{stopDesc}");
            if(wxUptime.getExplain()!= null) VALUES("`explain`", "#{explain}");
        }}.toString();
    }



    public String update(WxUptime wxUptime){
        return new SQL(){{
            UPDATE("t_wx_uptime");
            if(wxUptime.getId()!= null) SET("`id` = #{id}");
            if(wxUptime.getStartTime()!= null) SET("`start_time` = #{startTime}");
            if(wxUptime.getStopTime()!= null) SET("`stop_time` = #{stopTime}");
            if(wxUptime.getStartDesc()!= null) SET("`start_desc` = #{startDesc}");
            if(wxUptime.getStopDesc()!= null) SET("`stop_desc` = #{stopDesc}");
            if(wxUptime.getExplain()!= null) SET("`explain` = #{explain}");
            WHERE("id = #{id}");
        }}.toString();
    }

    public String findListByRelation(@Param("type")Integer type, @Param("key")Integer key, @Param("kid") Integer kid){
        return new SQL(){ {
            SELECT(" a.id ,start_time,stop_time,start_desc,stop_desc,`explain`,b.id AS relationId ,rid,kid,`key`,`type`");
            FROM("t_wx_uptime A");
            INNER_JOIN("t_wx_relation B ON A.id = B.rid");
            if(type!= null) WHERE("B.`type` = #{type}"); // 2:运行时间 3:午休时间
            else WHERE("B.`type` = 2");                  // 默认2
            if(key != null) AND().WHERE("B.`key` = #{key}");
            if(kid != null) AND().WHERE("B.`kid` = #{kid}");
        }}.toString();
    }

    public  String getByRelation(@Param("type")Integer type, @Param("key")Integer key, @Param("id") Integer id){
        return new SQL(){ {
            SELECT(" a.id ,start_time,stop_time,start_desc,stop_desc,`explain`,b.id AS relationId ,rid,kid,`key`,`type`");
            FROM("t_wx_uptime A");
            INNER_JOIN("t_wx_relation B ON A.id = B.rid");
            if(type!= null) WHERE("B.`type` = #{type}"); // 2:运行时间 3:午休时间
            else WHERE("B.`type` = 2");                  // 默认2
            if(key != null) AND().WHERE("B.`key` = #{key}");
            if(id != null) AND().WHERE("A.id = #{id}");
        }}.toString();
    }
}
