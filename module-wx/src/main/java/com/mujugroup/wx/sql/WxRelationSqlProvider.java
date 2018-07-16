package com.mujugroup.wx.sql;

import com.mujugroup.wx.model.WxRelation;
import org.apache.ibatis.jdbc.SQL;

/**
 * 扩展关系数据表,SQL语句组装类
 * 类名:WxRelationSqlProvider
 * @author leolaurel
 * 创建时间:20180712
 */
public class WxRelationSqlProvider {

    public String insert(WxRelation wxRelation){
        return new SQL(){{
            INSERT_INTO("t_wx_relation");
            if(wxRelation.getId()!= null) VALUES("`id`", "#{id}");
            if(wxRelation.getRid()!= null) VALUES("`rid`", "#{rid}");
            if(wxRelation.getKid()!= null) VALUES("`kid`", "#{kid}");
            if(wxRelation.getKey()!= null) VALUES("`key`", "#{key}");
            if(wxRelation.getType()!= null) VALUES("`type`", "#{type}");
        }}.toString();
    }



    public String update(WxRelation wxRelation){
        return new SQL(){{
            UPDATE("t_wx_relation");
            if(wxRelation.getId()!= null) SET("`id` = #{id}");
            if(wxRelation.getRid()!= null) SET("`rid` = #{rid}");
            if(wxRelation.getKid()!= null) SET("`kid` = #{kid}");
            if(wxRelation.getKey()!= null) SET("`key` = #{key}");
            if(wxRelation.getType()!= null) SET("`type` = #{type}");
            WHERE("id = #{id}");
        }}.toString();
    }
}
