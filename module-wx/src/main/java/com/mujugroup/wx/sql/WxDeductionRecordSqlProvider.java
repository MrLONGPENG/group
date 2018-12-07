package com.mujugroup.wx.sql;

import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.wx.model.WxDeductionRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * 扣费记录表,SQL语句组装类
 * 类名:WxDeductionRecordSqlProvider
 *
 * @author leolaurel
 * 创建时间:20181203
 */
public class WxDeductionRecordSqlProvider {

    public String insert(WxDeductionRecord wxDeductionRecord) {
        return new SQL() {{
            INSERT_INTO("t_wx_deduction_record");
            if (wxDeductionRecord.getId() != null) VALUES("`id`", "#{id}");
            if (wxDeductionRecord.getOpenId() != null) VALUES("`open_id`", "#{openId}");
            if (wxDeductionRecord.getTradeNo() != null) VALUES("`trade_no`", "#{tradeNo}");
            if (wxDeductionRecord.getDid() != null) VALUES("`did`", "#{did}");
            if (wxDeductionRecord.getExplain() != null) VALUES("`explain`", "#{explain}");
            if (wxDeductionRecord.getDay() != null) VALUES("`day`", "#{day}");
            if (wxDeductionRecord.getForfeit() != null) VALUES("`forfeit`", "#{forfeit}");
            if (wxDeductionRecord.getTimeout() != null) VALUES("`timeout`", "#{timeout}");
            if (wxDeductionRecord.getType() != null) VALUES("`type`", "#{type}");
            if (wxDeductionRecord.getCrtTime() != null) VALUES("`crtTime`", "#{crtTime}");
        }}.toString();
    }


    public String update(WxDeductionRecord wxDeductionRecord) {
        return new SQL() {{
            UPDATE("t_wx_deduction_record");
            if (wxDeductionRecord.getOpenId() != null) SET("`open_id` = #{openId}");
            if (wxDeductionRecord.getTradeNo() != null) SET("`trade_no` = #{tradeNo}");
            if (wxDeductionRecord.getDid() != null) SET("`did` = #{did}");
            if (wxDeductionRecord.getExplain() != null) SET("`explain` = #{explain}");
            if (wxDeductionRecord.getDay() != null) SET("`day` = #{day}");
            if (wxDeductionRecord.getForfeit() != null) SET("`forfeit` = #{forfeit}");
            if (wxDeductionRecord.getTimeout() != null) SET("`timeout` = #{timeout}");
            if (wxDeductionRecord.getType() != null) SET("`type` = #{type}");
            if (wxDeductionRecord.getCrtTime() != null) SET("`crtTime` = #{crtTime}");
            WHERE("id = #{id}");
        }}.toString();
    }

    public String getDeductionRecordList(@Param(value = "openId") String openId) {
        return new SQL() {{
            SELECT("r.*");
            FROM("t_wx_deposit t,t_wx_deduction_record r");
            WHERE("t.open_id=r.open_id AND t.status&3 AND t.crtTime<r.crtTime");
            if (!StringUtil.isEmpty(openId)) {
                AND().WHERE("t.open_id= #{openId}");
            }

        }}.toString();
    }
}
