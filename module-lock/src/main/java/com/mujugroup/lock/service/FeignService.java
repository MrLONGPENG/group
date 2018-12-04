package com.mujugroup.lock.service;


import com.github.pagehelper.PageInfo;
import com.lveqia.cloud.common.objeck.DBMap;
import com.lveqia.cloud.common.objeck.to.DataTo;
import com.lveqia.cloud.common.objeck.to.LockTo;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface FeignService {

    String unlock(String did);

    String beep(String did);

    LockTo getLockInfo(String did);

    String getLockStatus(String did);

    List<String> getFailNameByDid(String did);

    List<DataTo> getFailRecordList();

    DataTo getRecordByDidAndLastRefresh(long did, long lastRefresh);
}
