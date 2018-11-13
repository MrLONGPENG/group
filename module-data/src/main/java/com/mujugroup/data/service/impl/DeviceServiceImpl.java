package com.mujugroup.data.service.impl;

import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.objeck.to.InfoTo;
import com.lveqia.cloud.common.objeck.to.LockTo;
import com.lveqia.cloud.common.objeck.to.PayInfoTo;
import com.mujugroup.data.objeck.vo.DeviceVo;
import com.mujugroup.data.service.DeviceService;
import com.mujugroup.data.service.feign.ModuleCoreService;
import com.mujugroup.data.service.feign.ModuleLockService;
import com.mujugroup.data.service.feign.ModuleWxService;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(value = "deviceService")
public class DeviceServiceImpl implements DeviceService {
    private final ModuleCoreService moduleCoreService;
    private final ModuleLockService moduleLockService;
    private final ModuleWxService moduleWxService;
    private final MapperFactory mapperFactory;

    @Autowired
    public DeviceServiceImpl(ModuleCoreService moduleCoreService, ModuleLockService moduleLockService
            , ModuleWxService moduleWxService, MapperFactory mapperFactory) {
        this.moduleCoreService = moduleCoreService;
        this.moduleLockService = moduleLockService;
        this.moduleWxService = moduleWxService;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public DeviceVo getDeviceDetailByDid(String did) throws ParamException {
        DeviceVo deviceVo = new DeviceVo();
        InfoTo infoTo = moduleCoreService.getDeviceInfo(did, "");
        if (infoTo == null || infoTo.isIllegal()) throw new ParamException("该设备不存在!");
        addToDeviceVo(deviceVo, infoTo, InfoTo.class);

        LockTo lockTo = moduleLockService.getLockInfo(did);
        addToDeviceVo(deviceVo, lockTo, LockTo.class);

        PayInfoTo payInfoTo = moduleWxService.getPayInfoByDid(did);
        addToDeviceVo(deviceVo, payInfoTo, PayInfoTo.class);
        return deviceVo;
    }

    /*
    to对象转Vo
     */
    private void addToDeviceVo(DeviceVo deviceVo, Object obj, Class<?> toType) {
        if(obj == null) return;
        ClassMapBuilder<?, DeviceVo> temp = mapperFactory.classMap(toType, DeviceVo.class);
        if(obj instanceof LockTo){
            temp.fieldMap("lastRefresh").converter("dateConvert").add();
            temp.fieldMap("lockStatus").converter("lockStatusConvert").add();
            temp.fieldMap("electric").converter("electricConvert").add();
            temp.fieldMap("batteryStat", "battery").converter("batteryConvert").add();
        }else if(obj instanceof PayInfoTo){
            temp.fieldMap("orderType").converter("orderTypeConvert").add();
            temp.fieldMap("payTime").converter("timestampConvert").add();
            temp.fieldMap("endTime").converter("timestampConvert").add();
        }
        temp.mapNulls(false); // NULL值不映射
        temp.byDefault().register();
        mapperFactory.getMapperFacade().map(obj, deviceVo);
    }

}
