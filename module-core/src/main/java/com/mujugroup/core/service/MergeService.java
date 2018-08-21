package com.mujugroup.core.service;


import java.util.Map;

public interface MergeService {

    Map<String, String> getHidMapByAid(String param);

    Map<String, String> getOidMapByHid(String param);

    Map<String, String> getActiveCount(String param);

    Map<String, String> getTotalActiveCount(String param);
}
