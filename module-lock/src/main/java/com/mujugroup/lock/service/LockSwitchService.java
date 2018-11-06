package com.mujugroup.lock.service;


import com.lveqia.cloud.common.exception.ParamException;
import com.mujugroup.lock.model.LockSwitch;

import java.util.List;

/**
 * @author leolaurel
 */
public interface LockSwitchService {
    boolean add(LockSwitch lockSwitch);

    List<LockSwitch> getLockStatusList(String did,String bid) throws ParamException;
}
