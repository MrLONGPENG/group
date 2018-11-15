package com.mujugroup.lock.service;


import com.lveqia.cloud.common.exception.ParamException;
import com.mujugroup.lock.model.LockSwitch;
import com.mujugroup.lock.objeck.vo.unlock.SwitchVo;

import java.util.List;

/**
 * @author leolaurel
 */
public interface LockSwitchService {

    boolean add(LockSwitch lockSwitch);

    List<LockSwitch> getLockStatusList(String did,String bid,String startTime,String endTime) throws ParamException;

    List<SwitchVo> convert(List<LockSwitch> lockSwitchList);
}
