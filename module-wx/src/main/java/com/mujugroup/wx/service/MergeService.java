package com.mujugroup.wx.service;


import java.util.Map;

public interface MergeService {

    Map<String, String> getPayCount(String param);

    Map<String, String> getPaymentInfo(String param);

    Map<String, String> getUserCount(String param);

    Map<String, String> getUsageCount(String param);



}
