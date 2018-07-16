package com.mujugroup.wx.service;


import java.util.Map;

public interface PayApiService {

    Map<String, String> requestPay(String sessionThirdKey, String did, String code, String goods,String ip);

    String completePay(String notifyXml) throws Exception;
}
