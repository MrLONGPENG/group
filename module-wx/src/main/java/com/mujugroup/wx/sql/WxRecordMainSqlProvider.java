package com.mujugroup.wx.sql;

import com.mujugroup.wx.model.WxRecordMain;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * 支付记录主表,SQL语句组装类
 * 类名:WxRecordMainSqlProvider
 *
 * @author leolaurel
 * 创建时间:20181127
 */
public class WxRecordMainSqlProvider {

    public String insert(WxRecordMain wxRecordMain) {
        return new SQL() {{
            INSERT_INTO("t_wx_record_main");
            if (wxRecordMain.getId() != null) VALUES("`id`", "#{id}");
            if (wxRecordMain.getDid() != null) VALUES("`did`", "#{did}");
            if (wxRecordMain.getAid() != null) VALUES("`aid`", "#{aid}");
            if (wxRecordMain.getHid() != null) VALUES("`hid`", "#{hid}");
            if (wxRecordMain.getOid() != null) VALUES("`oid`", "#{oid}");
            if (wxRecordMain.getOpenId() != null) VALUES("`open_id`", "#{openId}");
            if (wxRecordMain.getTradeNo() != null) VALUES("`trade_no`", "#{tradeNo}");
            if (wxRecordMain.getTransactionId() != null) VALUES("`transaction_id`", "#{transactionId}");
            if (wxRecordMain.getTotalPrice() != null) VALUES("`total_price`", "#{totalPrice}");
            if (wxRecordMain.getRefundCount() != null) VALUES("`refund_count`", "#{refundCount}");
            if (wxRecordMain.getRefundPrice() != null) VALUES("`refund_price`", "#{refundPrice}");
            if (wxRecordMain.getPayStatus() != null) VALUES("`pay_status`", "#{payStatus}");
            if (wxRecordMain.getCrtTime() != null) VALUES("`crtTime`", "#{crtTime}");
        }}.toString();
    }


    public String update(WxRecordMain wxRecordMain) {
        return new SQL() {{
            UPDATE("t_wx_record_main");
            if (wxRecordMain.getId() != null) SET("`id` = #{id}");
            if (wxRecordMain.getDid() != null) SET("`did` = #{did}");
            if (wxRecordMain.getAid() != null) SET("`aid` = #{aid}");
            if (wxRecordMain.getHid() != null) SET("`hid` = #{hid}");
            if (wxRecordMain.getOid() != null) SET("`oid` = #{oid}");
            if (wxRecordMain.getOpenId() != null) SET("`open_id` = #{openId}");
            if (wxRecordMain.getTradeNo() != null) SET("`trade_no` = #{tradeNo}");
            if (wxRecordMain.getTransactionId() != null) SET("`transaction_id` = #{transactionId}");
            if (wxRecordMain.getTotalPrice() != null) SET("`total_price` = #{totalPrice}");
            if (wxRecordMain.getRefundCount() != null) SET("`refund_count` = #{refundCount}");
            if (wxRecordMain.getRefundPrice() != null) SET("`refund_price` = #{refundPrice}");
            if (wxRecordMain.getPayStatus() != null) SET("`pay_status` = #{payStatus}");
            if (wxRecordMain.getCrtTime() != null) SET("`crtTime` = #{crtTime}");
            WHERE("id = #{id}");
        }}.toString();
    }

    public String getPayRecordList(@Param(value = "orderNo") String orderNo) {
        return new SQL() {{
            SELECT("");
            FROM("");
        }}.toString();
    }
}
