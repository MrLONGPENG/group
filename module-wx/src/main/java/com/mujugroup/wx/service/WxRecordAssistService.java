package com.mujugroup.wx.service;

import com.mujugroup.wx.model.WxRecordAssist;

/**
 * @author leolaurel
 */
public interface WxRecordAssistService {
    boolean insert(WxRecordAssist wxRecordAssist);

    boolean update(WxRecordAssist wxRecordAssist);
}
