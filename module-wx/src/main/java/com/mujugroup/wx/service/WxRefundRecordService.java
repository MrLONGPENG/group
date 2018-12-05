package com.mujugroup.wx.service;

import com.mujugroup.wx.model.WxRefundRecord;

/**
 * @author leolaurel
 */
public interface WxRefundRecordService {
    boolean insert(WxRefundRecord wxRefundRecord);

    WxRefundRecord bindWxRefundRecord(String openId, String tradeNo, String refundNo, int refundCount
            , int refundPrice, int totalPrice, int refundStatus, int refundType, String desc);
}
