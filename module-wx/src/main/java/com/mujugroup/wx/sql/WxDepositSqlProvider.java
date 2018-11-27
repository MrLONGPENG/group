package com.mujugroup.wx.sql;

import com.mujugroup.wx.model.WxDeposit;
import org.apache.ibatis.jdbc.SQL;

/**
 * 押金表,SQL语句组装类
 * 类名:WxDepositSqlProvider
 * @author leolaurel
 * 创建时间:20181127
 */
public class WxDepositSqlProvider {

    public String insert(WxDeposit wxDeposit){
        return new SQL(){{
            INSERT_INTO("t_wx_deposit");
            if(wxDeposit.getId()!= null) VALUES("`id`", "#{id}");
            if(wxDeposit.getGid()!= null) VALUES("`gid`", "#{gid}");
            if(wxDeposit.getOpenId()!= null) VALUES("`open_id`", "#{openId}");
            if(wxDeposit.getTradeNo()!= null) VALUES("`trade_no`", "#{tradeNo}");
            if(wxDeposit.getDeposit()!= null) VALUES("`deposit`", "#{deposit}");
            if(wxDeposit.getStatus()!= null) VALUES("`status`", "#{status}");
            if(wxDeposit.getCrtTime()!= null) VALUES("`crtTime`", "#{crtTime}");
            if(wxDeposit.getUpdTime()!= null) VALUES("`updTime`", "#{updTime}");
        }}.toString();
    }



    public String update(WxDeposit wxDeposit){
        return new SQL(){{
            UPDATE("t_wx_deposit");
            if(wxDeposit.getId()!= null) SET("`id` = #{id}");
            if(wxDeposit.getGid()!= null) SET("`gid` = #{gid}");
            if(wxDeposit.getOpenId()!= null) SET("`open_id` = #{openId}");
            if(wxDeposit.getTradeNo()!= null) SET("`trade_no` = #{tradeNo}");
            if(wxDeposit.getDeposit()!= null) SET("`deposit` = #{deposit}");
            if(wxDeposit.getStatus()!= null) SET("`status` = #{status}");
            if(wxDeposit.getCrtTime()!= null) SET("`crtTime` = #{crtTime}");
            if(wxDeposit.getUpdTime()!= null) SET("`updTime` = #{updTime}");
            WHERE("id = #{id}");
        }}.toString();
    }
}
