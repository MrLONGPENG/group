package com.mujugroup.wx.sql;

import com.lveqia.cloud.common.util.Constant;
import com.mujugroup.wx.model.WxOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * 订单信息表,SQL语句组装类
 * 类名:WxOrderSqlProvider
 * @author leolaurel
 * 创建时间:20180713
 */
public class WxOrderSqlProvider {

    public String insert(WxOrder wxOrder){
        return new SQL(){{
            INSERT_INTO("t_wx_order");
            if(wxOrder.getId()!= null) VALUES("`id`", "#{id}");
            if(wxOrder.getDid()!= null) VALUES("`did`", "#{did}");
            if(wxOrder.getAid()!= null) VALUES("`aid`", "#{aid}");
            if(wxOrder.getHid()!= null) VALUES("`hid`", "#{hid}");
            if(wxOrder.getOid()!= null) VALUES("`oid`", "#{oid}");
            if(wxOrder.getGid()!= null) VALUES("`gid`", "#{gid}");
            if(wxOrder.getOpenId()!= null) VALUES("`open_id`", "#{openId}");
            if(wxOrder.getTradeNo()!= null) VALUES("`trade_no`", "#{tradeNo}");
            if(wxOrder.getTransactionId()!= null) VALUES("`transaction_id`", "#{transactionId}");
            if(wxOrder.getPayPrice()!= null) VALUES("`pay_price`", "#{payPrice}");
            if(wxOrder.getPayStatus()!= null) VALUES("`pay_status`", "#{payStatus}");
            if(wxOrder.getPayTime()!= null) VALUES("`pay_time`", "#{payTime}");
            if(wxOrder.getEndTime()!= null) VALUES("`end_time`", "#{endTime}");
            if(wxOrder.getCrtTime()!= null) VALUES("`crtTime`", "#{crtTime}");
        }}.toString();
    }



    public String update(WxOrder wxOrder){
        return new SQL(){{
            UPDATE("t_wx_order");
            if(wxOrder.getId()!= null) SET("`id` = #{id}");
            if(wxOrder.getDid()!= null) SET("`did` = #{did}");
            if(wxOrder.getAid()!= null) SET("`aid` = #{aid}");
            if(wxOrder.getHid()!= null) SET("`hid` = #{hid}");
            if(wxOrder.getOid()!= null) SET("`oid` = #{oid}");
            if(wxOrder.getGid()!= null) SET("`gid` = #{gid}");
            if(wxOrder.getOpenId()!= null) SET("`open_id` = #{openId}");
            if(wxOrder.getTradeNo()!= null) SET("`trade_no` = #{tradeNo}");
            if(wxOrder.getTransactionId()!= null) SET("`transaction_id` = #{transactionId}");
            if(wxOrder.getPayPrice()!= null) SET("`pay_price` = #{payPrice}");
            if(wxOrder.getPayStatus()!= null) SET("`pay_status` = #{payStatus}");
            if(wxOrder.getPayTime()!= null) SET("`pay_time` = #{payTime}");
            if(wxOrder.getEndTime()!= null) SET("`end_time` = #{endTime}");
            if(wxOrder.getCrtTime()!= null) SET("`crtTime` = #{crtTime}");
            WHERE("id = #{id}");
        }}.toString();
    }

    public String getYesterdayUsageCount(@Param("aid")String aid, @Param("start")String start, @Param("end")String end){
        return new SQL(){{
            SELECT("COUNT(DISTINCT `did`) as `value`");
            FROM("t_wx_order");
            if(!Constant.DIGIT_ZERO.equals(aid)) AND().WHERE("aid = #{aid}");
            if(!Constant.DIGIT_ZERO.equals(start)) AND().WHERE("pay_time >= #{start}");
            if(!Constant.DIGIT_ZERO.equals(end)) AND().WHERE("pay_time < #{end}");
        }}.toString();
    }
}
