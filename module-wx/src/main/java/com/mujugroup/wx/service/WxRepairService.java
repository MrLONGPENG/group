package com.mujugroup.wx.service;

public interface WxRepairService {

    boolean report(String sessionThirdKey, String did, String cause, String describe, String images);
}
