package com.mujugroup.lock.service;


import com.mujugroup.lock.model.LockInfo;
import com.mujugroup.lock.objeck.vo.info.ListVo;

import java.util.List;

public interface LockInfoService {

    LockInfo getLockInfoByBid(String bid);

    LockInfo insert(LockInfo lockInfo);

    LockInfo update(LockInfo lockInfo);

    List<ListVo> getInfoList(ListVo listVo);
}
