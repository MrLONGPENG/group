package com.mujugroup.wx.service;

import com.mujugroup.wx.model.WxRecordAssist;
import com.mujugroup.wx.model.WxRecordMain;

import java.util.List;

/**
 * @author leolaurel
 */
public interface WxRecordMainService {
    boolean insert(WxRecordMain wxRecordMain);

    WxRecordMain findMainRecordByNo(String orderNo);

    boolean update(WxRecordMain wxRecordMain);

    void insertRecord(WxRecordMain wxRecordMain, List<WxRecordAssist> wxRecordAssists);
}
