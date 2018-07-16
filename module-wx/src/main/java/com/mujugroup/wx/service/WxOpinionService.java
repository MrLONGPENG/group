package com.mujugroup.wx.service;



/**
 * @author leolaurel
 */
public interface WxOpinionService {

    void feedback(String sessionThirdKey, String content, String did);
}
