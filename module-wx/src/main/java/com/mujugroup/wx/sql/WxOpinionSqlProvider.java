package com.mujugroup.wx.sql;

import com.mujugroup.wx.model.WxOpinion;
import org.apache.ibatis.jdbc.SQL;

/**
 * 意见反馈表,SQL语句组装类
 * 类名:WxOpinionSqlProvider
 * @author leolaurel
 * 创建时间:20180714
 */
public class WxOpinionSqlProvider {

    public String insert(WxOpinion wxOpinion){
        return new SQL(){{
            INSERT_INTO("t_wx_opinion");
            if(wxOpinion.getId()!= null) VALUES("`id`", "#{id}");
            if(wxOpinion.getDid()!= null) VALUES("`did`", "#{did}");
            if(wxOpinion.getOpenId()!= null) VALUES("`open_id`", "#{openId}");
            if(wxOpinion.getContent()!= null) VALUES("`content`", "#{content}");
            if(wxOpinion.getReader()!= null) VALUES("`reader`", "#{reader}");
            if(wxOpinion.getReadStatus()!= null) VALUES("`read_status`", "#{readStatus}");
            if(wxOpinion.getCrtTime()!= null) VALUES("`crtTime`", "#{crtTime}");
            if(wxOpinion.getUpdTime()!= null) VALUES("`updTime`", "#{updTime}");
        }}.toString();
    }



    public String update(WxOpinion wxOpinion){
        return new SQL(){{
            UPDATE("t_wx_opinion");
            if(wxOpinion.getId()!= null) SET("`id` = #{id}");
            if(wxOpinion.getDid()!= null) SET("`did` = #{did}");
            if(wxOpinion.getOpenId()!= null) SET("`open_id` = #{openId}");
            if(wxOpinion.getContent()!= null) SET("`content` = #{content}");
            if(wxOpinion.getReader()!= null) SET("`reader` = #{reader}");
            if(wxOpinion.getReadStatus()!= null) SET("`read_status` = #{readStatus}");
            if(wxOpinion.getCrtTime()!= null) SET("`crtTime` = #{crtTime}");
            if(wxOpinion.getUpdTime()!= null) SET("`updTime` = #{updTime}");
            WHERE("id = #{id}");
        }}.toString();
    }
}
