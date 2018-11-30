package com.mujugroup.lock.service;

import com.lveqia.cloud.common.objeck.to.LockTo;

import java.util.List;

public interface FeignService {

    String unlock(String did);

    LockTo getLockInfo(String did);

    String getLockStatus(String did);

    List<String> getFailNameByDid(String did);


}
