package com.mujugroup.lock.service;


import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.objeck.DBMap;
import com.lveqia.cloud.common.objeck.to.DataTo;
import com.mujugroup.lock.model.LockSwitch;
import com.mujugroup.lock.objeck.vo.unlock.ListVo;
import com.mujugroup.lock.objeck.vo.unlock.SwitchVo;

import java.util.Date;
import java.util.List;

/**
 * @author leolaurel
 */
public interface LockSwitchService {

    boolean add(LockSwitch lockSwitch);

    List<LockSwitch> getLockStatusList(ListVo listVo) throws ParamException;

    List<SwitchVo> convert(List<LockSwitch> lockSwitchList);

    LockSwitch getLastOpenRecord(Long did);

    DataTo getRecordByDidAndLastRefresh(long did, long lastRefresh);
}
