package com.mujugroup.wx.sql;

import com.mujugroup.wx.model.WxRecordAssist;
import org.apache.ibatis.jdbc.SQL;

/**
 * 支付记录辅表,SQL语句组装类
 * 类名:WxRecordAssistSqlProvider
 * @author leolaurel
 * 创建时间:20181127
 */
public class WxRecordAssistSqlProvider {

    public String insert(WxRecordAssist wxRecordAssist){
        return new SQL(){{
            INSERT_INTO("t_wx_record_assist");
            if(wxRecordAssist.getId()!= null) VALUES("`id`", "#{id}");
            if(wxRecordAssist.getMid()!= null) VALUES("`mid`", "#{mid}");
            if(wxRecordAssist.getGid()!= null) VALUES("`gid`", "#{gid}");
            if(wxRecordAssist.getPrice()!= null) VALUES("`price`", "#{price}");
            if(wxRecordAssist.getCrtTime()!= null) VALUES("`crtTime`", "#{crtTime}");
            if(wxRecordAssist.getType()!= null) VALUES("`type`", "#{type}");
        }}.toString();
    }



    public String update(WxRecordAssist wxRecordAssist){
        return new SQL(){{
            UPDATE("t_wx_record_assist");
            if(wxRecordAssist.getId()!= null) SET("`id` = #{id}");
            if(wxRecordAssist.getMid()!= null) SET("`mid` = #{mid}");
            if(wxRecordAssist.getGid()!= null) SET("`gid` = #{gid}");
            if(wxRecordAssist.getPrice()!= null) SET("`price` = #{price}");
            if(wxRecordAssist.getCrtTime()!= null) SET("`crtTime` = #{crtTime}");
            if(wxRecordAssist.getType()!= null) SET("`type` = #{type}");
            WHERE("id = #{id}");
        }}.toString();
    }
}
