package com.mujugroup.wx.sql;

import com.lveqia.cloud.common.StringUtil;
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

    public String getUsageCount(@Param("aid")String aid, @Param("hid")String hid, @Param("oid")String oid
            , @Param("start")long start, @Param("end")long end){
        return new SQL(){{
            SELECT("COUNT(DISTINCT `did`) as `count1`, COUNT(`did`) as `count2`");
            FROM("t_wx_order");
            if(!Constant.DIGIT_ZERO.equals(aid)) AND().WHERE("aid = #{aid}");
            if(!Constant.DIGIT_ZERO.equals(hid)) AND().WHERE("hid = #{hid}");
            if(!Constant.DIGIT_ZERO.equals(oid)) AND().WHERE("oid = #{oid}");
            // TODO 当存在支付时间的数据，即为已支付状态,当增加退款等状态时候，此处还需修改
            if(start != 0 ) AND().WHERE("pay_time >= #{start}");
            if(end != 0 ) AND().WHERE("pay_time < #{end}");
        }}.toString();
    }


    public String findList(@Param("aid")int aid, @Param("hid") int hid, @Param("oid")int oid
            , @Param("start")long start, @Param("end")long end, @Param("tradeNo") String tradeNo){
        return new SQL(){{
            SELECT("*"); FROM("t_wx_order");
            WHERE("pay_status > 1");
            if(aid != 0) AND().WHERE("aid = #{aid}");
            if(hid != 0) AND().WHERE("hid = #{hid}");
            if(oid != 0) AND().WHERE("oid = #{oid}");
            if(start != 0 ) AND().WHERE("pay_time >= #{start}");
            if(end != 0 ) AND().WHERE("pay_time < #{end}");
            if(!StringUtil.isEmpty(tradeNo)) AND().WHERE("trade_no = #{tradeNo}");
            ORDER_BY("id desc");
        }}.toString();
    }

    public String getTotalProfit(@Param("aid")int aid, @Param("hid") int hid, @Param("oid")int oid
            , @Param("did")String did, @Param("tradeNo") String tradeNo
            , @Param("start") long start, @Param("end") long end){
        return new SQL(){{
            SELECT("COALESCE(SUM(`pay_price`),0)"); FROM("t_wx_order");
            WHERE("pay_status > 1");
            if(aid != 0) AND().WHERE("aid = #{aid}");
            if(hid != 0) AND().WHERE("hid = #{hid}");
            if(oid != 0) AND().WHERE("oid = #{oid}");
            if(start != 0 ) AND().WHERE("pay_time >= #{start}");
            if(end != 0 ) AND().WHERE("pay_time < #{end}");
            if(!StringUtil.isEmpty(did)) AND().WHERE("did = #{did}");
            if(!StringUtil.isEmpty(tradeNo)) AND().WHERE("trade_no = #{tradeNo}");
            ORDER_BY("id desc");
        }}.toString();
    }


}
