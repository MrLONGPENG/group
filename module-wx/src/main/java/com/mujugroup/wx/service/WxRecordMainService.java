package com.mujugroup.wx.service;

import com.mujugroup.wx.model.WxRecordMain;

/**
 * @author leolaurel
 */
public interface WxRecordMainService {
    boolean insert(WxRecordMain wxRecordMain);

    WxRecordMain findMainRecordByNo(String orderNo);

    boolean update(WxRecordMain wxRecordMain);

}
