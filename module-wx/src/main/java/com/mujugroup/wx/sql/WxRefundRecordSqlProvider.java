package com.mujugroup.wx.sql;

import com.mujugroup.wx.model.WxRefundRecord;
import org.apache.ibatis.jdbc.SQL;

/**
 * 退款记录表,SQL语句组装类
 * 类名:WxRefundRecordSqlProvider
 *
 * @author leolaurel
 * 创建时间:20181130
 */
public class WxRefundRecordSqlProvider {

    public String insert(WxRefundRecord wxRefundRecord) {
        return new SQL() {{
            INSERT_INTO("t_wx_refund_record");
            if (wxRefundRecord.getId() != null) VALUES("`id`", "#{id}");
            if (wxRefundRecord.getOpenId() != null) VALUES("`open_id`", "#{openId}");
            if (wxRefundRecord.getTradeNo() != null) VALUES("`trade_no`", "#{tradeNo}");
            if (wxRefundRecord.getRefundNo() != null) VALUES("`refund_no`", "#{refundNo}");
            if (wxRefundRecord.getRefundCount() != null) VALUES("`refund_count`", "#{refundCount}");
            if (wxRefundRecord.getRefundDesc() != null) VALUES("`refundDesc`", "#{refundDesc}");
            if (wxRefundRecord.getRefundPrice() != null) VALUES("`refund_price`", "#{refundPrice}");
            if (wxRefundRecord.getTotalPrice() != null) VALUES("`total_price`", "#{totalPrice}");
            if (wxRefundRecord.getRefundStatus() != null) VALUES("`refund_status`", "#{refundStatus}");
            if (wxRefundRecord.getRefundType() != null) VALUES("`refund_type`", "#{refundType}");
            if (wxRefundRecord.getCrtTime() != null) VALUES("`crtTime`", "#{crtTime}");
        }}.toString();
    }


    public String update(WxRefundRecord wxRefundRecord) {
        return new SQL() {{
            UPDATE("t_wx_refund_record");
            if (wxRefundRecord.getId() != null) SET("`id` = #{id}");
            if (wxRefundRecord.getOpenId() != null) SET("`open_id` = #{openId}");
            if (wxRefundRecord.getTradeNo() != null) SET("`trade_no` = #{tradeNo}");
            if (wxRefundRecord.getRefundNo() != null) SET("`refund_no` = #{refundNo}");
            if (wxRefundRecord.getRefundCount() != null) SET("`refund_count` = #{refundCount}");
            if (wxRefundRecord.getRefundDesc() != null) SET("`refundDesc` = #{refundDesc}");
            if (wxRefundRecord.getRefundPrice() != null) SET("`refund_price` = #{refundPrice}");
            if (wxRefundRecord.getTotalPrice() != null) SET("`total_price` = #{totalPrice}");
            if (wxRefundRecord.getRefundStatus() != null) SET("`refund_status` = #{refundStatus}");
            if (wxRefundRecord.getRefundType() != null) SET("`refund_type` = #{refundType}");
            if (wxRefundRecord.getCrtTime() != null) SET("`crtTime` = #{crtTime}");
            WHERE("id = #{id}");
        }}.toString();
    }
}
