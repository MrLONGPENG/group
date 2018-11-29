package com.mujugroup.wx.service;


import com.lveqia.cloud.common.exception.BaseException;

import java.util.Map;

public interface PayApiService {

    Map<String, String> requestPay(String sessionThirdKey, String code, String goods,String ip)
            throws BaseException;

    String completePay(String notifyXml) throws Exception;

    Map<String, String> refund(int index, String orderNo, Long totalFee, Long refundFee, String refundDesc, String refundAccount);
}
