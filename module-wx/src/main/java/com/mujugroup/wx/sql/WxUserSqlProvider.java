package com.mujugroup.wx.sql;

import com.lveqia.cloud.common.util.Constant;
import com.mujugroup.wx.model.WxUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * 微信用户基础信息表,SQL语句组装类
 * 类名:WxUserSqlProvider
 * @author leolaurel
 * 创建时间:20180714
 */
public class WxUserSqlProvider {

    public String insert(WxUser wxUser){
        return new SQL(){{
            INSERT_INTO("t_wx_user");
            if(wxUser.getId()!= null) VALUES("`id`", "#{id}");
            if(wxUser.getPhone()!= null) VALUES("`phone`", "#{phone}");
            if(wxUser.getOpenId()!= null) VALUES("`open_id`", "#{openId}");
            if(wxUser.getUnionId()!= null) VALUES("`union_id`", "#{unionId}");
            if(wxUser.getNickName()!= null) VALUES("`nick_name`", "#{nickName}");
            if(wxUser.getGender()!= null) VALUES("`gender`", "#{gender}");
            if(wxUser.getLanguage()!= null) VALUES("`language`", "#{language}");
            if(wxUser.getCountry()!= null) VALUES("`country`", "#{country}");
            if(wxUser.getProvince()!= null) VALUES("`province`", "#{province}");
            if(wxUser.getCity()!= null) VALUES("`city`", "#{city}");
            if(wxUser.getAvatarUrl()!= null) VALUES("`avatar_url`", "#{avatarUrl}");
            if(wxUser.getSessionKey()!= null) VALUES("`session_key`", "#{sessionKey}");
            if(wxUser.getCrtTime()!= null) VALUES("`crtTime`", "#{crtTime}");
            if(wxUser.getUpdateTime()!= null) VALUES("`update_time`", "#{updateTime}");
        }}.toString();
    }



    public String update(WxUser wxUser){
        return new SQL(){{
            UPDATE("t_wx_user");
            if(wxUser.getId()!= null) SET("`id` = #{id}");
            if(wxUser.getPhone()!= null) SET("`phone` = #{phone}");
            if(wxUser.getOpenId()!= null) SET("`open_id` = #{openId}");
            if(wxUser.getUnionId()!= null) SET("`union_id` = #{unionId}");
            if(wxUser.getNickName()!= null) SET("`nick_name` = #{nickName}");
            if(wxUser.getGender()!= null) SET("`gender` = #{gender}");
            if(wxUser.getLanguage()!= null) SET("`language` = #{language}");
            if(wxUser.getCountry()!= null) SET("`country` = #{country}");
            if(wxUser.getProvince()!= null) SET("`province` = #{province}");
            if(wxUser.getCity()!= null) SET("`city` = #{city}");
            if(wxUser.getAvatarUrl()!= null) SET("`avatar_url` = #{avatarUrl}");
            if(wxUser.getSessionKey()!= null) SET("`session_key` = #{sessionKey}");
            if(wxUser.getCrtTime()!= null) SET("`crtTime` = #{crtTime}");
            if(wxUser.getUpdateTime()!= null) SET("`update_time` = #{updateTime}");
            WHERE("id = #{id}");
        }}.toString();
    }

    public String getTotalUserCount(@Param("start")String start, @Param("end") String end){
        return new SQL(){{
            SELECT("COUNT(DISTINCT `open_id`) as `value`");
            FROM("t_wx_user");
            if(!Constant.DIGIT_ZERO.equals(start)) AND().WHERE("crtTime >= FROM_UNIXTIME(#{start})");
            if(!Constant.DIGIT_ZERO.equals(end)) AND().WHERE("crtTime < FROM_UNIXTIME(#{end})");
        }}.toString();
    }
}
