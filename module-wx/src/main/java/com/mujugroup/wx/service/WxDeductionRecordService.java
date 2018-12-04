package com.mujugroup.wx.service;

import com.mujugroup.wx.model.WxDeductionRecord;

import java.util.Date;
import java.util.List;

/**
 * @author leolaurel
 */
public interface WxDeductionRecordService {
    boolean insert(WxDeductionRecord wxDeductionRecord);

    WxDeductionRecord isExist(String date, String openId, long did);

    boolean update(WxDeductionRecord wxDeductionRecord);

    List<WxDeductionRecord> getDeductionRecordList(String sessionThirdKey , String tradeNo);
}
