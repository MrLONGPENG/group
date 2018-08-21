package com.mujugroup.wx.service;


import java.util.Map;

public interface MergeService {

    Map<String,String> getPayCount(String param);

    Map<String,String> getPaymentInfo(String param);

    Map<String, String> getTotalUserCount(String param);

    Map<String,String> getYesterdayUsageCount(String param);



}
