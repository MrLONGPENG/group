package com.mujugroup.wx.sql;

import com.mujugroup.wx.model.WxImages;
import org.apache.ibatis.jdbc.SQL;

/**
 * 保修图片表,SQL语句组装类
 * 类名:WxImagesSqlProvider
 * 创建人:LEOLAUREL
 * 创建时间:20180707
 */
public class WxImagesSqlProvider {

    public String insert(WxImages wxImages){
        return new SQL(){{
            INSERT_INTO("t_wx_images");
            if(wxImages.getId()!= null) VALUES("`id`", "#{id}");
            if(wxImages.getPid()!= null) VALUES("`pid`", "#{pid}");
            if(wxImages.getType()!= null) VALUES("`type`", "#{type}");
            if(wxImages.getImageUrl()!= null) VALUES("`image_url`", "#{imageUrl}");
            if(wxImages.getCrtTime()!= null) VALUES("`crtTime`", "#{crtTime}");
        }}.toString();
    }



    public String update(WxImages wxImages){
        return new SQL(){{
            UPDATE("t_wx_images");
            if(wxImages.getId()!= null) SET("`id` = #{id}");
            if(wxImages.getPid()!= null) SET("`pid` = #{pid}");
            if(wxImages.getType()!= null) SET("`type` = #{type}");
            if(wxImages.getImageUrl()!= null) SET("`image_url` = #{imageUrl}");
            if(wxImages.getCrtTime()!= null) SET("`crtTime` = #{crtTime}");
            WHERE("id = #{id}");
        }}.toString();
    }
}
