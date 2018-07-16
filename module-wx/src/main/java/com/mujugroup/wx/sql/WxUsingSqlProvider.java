package com.mujugroup.wx.sql;

import com.mujugroup.wx.model.WxUsing;
import org.apache.ibatis.jdbc.SQL;

/**
 * 使用支付记录表,SQL语句组装类
 * 类名:WxUsingSqlProvider
 * 创建人:LEOLAUREL
 * 创建时间:20180710
 */
public class WxUsingSqlProvider {

    public String insert(WxUsing wxUsing){
        return new SQL(){{
            INSERT_INTO("t_wx_using");
            if(wxUsing.getId()!= null) VALUES("`id`", "#{id}");
            if(wxUsing.getDid()!= null) VALUES("`did`", "#{did}");
            if(wxUsing.getOpenId()!= null) VALUES("`open_id`", "#{openId}");
            if(wxUsing.getPayCost()!= null) VALUES("`pay_cost`", "#{payCost}");
            if(wxUsing.getPayTime()!= null) VALUES("`pay_time`", "#{payTime}");
            if(wxUsing.getEndTime()!= null) VALUES("`end_time`", "#{endTime}");
            if(wxUsing.getUnlockTime()!= null) VALUES("`unlock_time`", "#{unlockTime}");
            if(wxUsing.getUsing()!= null) VALUES("`using`", "#{using}");
            if(wxUsing.getDeleted()!= null) VALUES("`deleted`", "#{deleted}");
        }}.toString();
    }



    public String update(WxUsing wxUsing){
        return new SQL(){{
            UPDATE("t_wx_using");
            if(wxUsing.getId()!= null) SET("`id` = #{id}");
            if(wxUsing.getDid()!= null) SET("`did` = #{did}");
            if(wxUsing.getOpenId()!= null) SET("`open_id` = #{openId}");
            if(wxUsing.getPayCost()!= null) SET("`pay_cost` = #{payCost}");
            if(wxUsing.getPayTime()!= null) SET("`pay_time` = #{payTime}");
            if(wxUsing.getEndTime()!= null) SET("`end_time` = #{endTime}");
            if(wxUsing.getUnlockTime()!= null) SET("`unlock_time` = #{unlockTime}");
            if(wxUsing.getUsing()!= null) SET("`using` = #{using}");
            if(wxUsing.getDeleted()!= null) SET("`deleted` = #{deleted}");
            WHERE("id = #{id}");
        }}.toString();
    }
}
