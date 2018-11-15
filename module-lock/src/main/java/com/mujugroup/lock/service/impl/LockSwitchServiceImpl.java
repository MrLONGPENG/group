package com.mujugroup.lock.service.impl;

import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.lock.mapper.LockSwitchMapper;
import com.mujugroup.lock.model.LockSwitch;
import com.mujugroup.lock.objeck.vo.unlock.SwitchVo;
import com.mujugroup.lock.service.LockSwitchService;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author leolaurel
 */
@Service("lockSwitchService")
public class LockSwitchServiceImpl implements LockSwitchService {

    private final MapperFactory mapperFactory;
    private final LockSwitchMapper lockSwitchMapper;


    @Autowired
    public LockSwitchServiceImpl(MapperFactory mapperFactory, LockSwitchMapper lockSwitchMapper) {
        this.mapperFactory = mapperFactory;
        this.lockSwitchMapper = lockSwitchMapper;

    }

    @Override
    public boolean add(LockSwitch lockSwitch) {
        return lockSwitchMapper.insert(lockSwitch);
    }

    @Override
    public List<LockSwitch> getLockStatusList(String did, String bid) {
        return lockSwitchMapper.getLockStatusList(did,bid);
    }

    @Override
    public List<SwitchVo> convert(List<LockSwitch> lockSwitchList) {
        mapperFactory.classMap(LockSwitch.class, SwitchVo.class)
                .field("lockId", "bid")
                .fieldMap("lockStatus").converter("statusTypeConvert").add()
                .fieldMap("localTime").converter("dateConvertStr").add()
                .fieldMap("receiveTime").converter("dateConvertStr").add()
                .byDefault().register();
        return mapperFactory.getMapperFacade().mapAsList(lockSwitchList, SwitchVo.class);
    }

}
