package com.mujugroup.data.service.impl;

import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.objeck.to.InfoTo;
import com.lveqia.cloud.common.objeck.to.LockTo;
import com.lveqia.cloud.common.objeck.to.PayInfoTo;
import com.lveqia.cloud.common.objeck.vo.InfoVo;
import com.lveqia.cloud.common.objeck.vo.LockVo;
import com.lveqia.cloud.common.objeck.vo.PayInfoVo;
import com.lveqia.cloud.common.util.DateUtil;
import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.data.objeck.vo.DeviceVo;
import com.mujugroup.data.service.DeviceServcie;
import com.mujugroup.data.service.feign.ModuleCoreService;
import com.mujugroup.data.service.feign.ModuleLockService;
import com.mujugroup.data.service.feign.ModuleWxService;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;

@Service(value = "deviceServcie")
public class DeviceServcieImpl implements DeviceServcie {
    private final ModuleCoreService moduleCoreService;
    private final ModuleLockService moduleLockService;
    private final ModuleWxService moduleWxService;
    private final MapperFactory mapperFactory;

    @Autowired
    public DeviceServcieImpl(ModuleCoreService moduleCoreService, ModuleLockService moduleLockService, ModuleWxService moduleWxService, MapperFactory mapperFactory) {
        this.moduleCoreService = moduleCoreService;
        this.moduleLockService = moduleLockService;
        this.moduleWxService = moduleWxService;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public DeviceVo getDeviceDetailByDid(String did) throws ParamException {
        DeviceVo deviceVo = new DeviceVo();
        InfoTo infoTo = moduleCoreService.getDeviceInfo(did, "");
        if (infoTo == null) throw new ParamException("该设备不存在!");
        LockTo lockTo = moduleLockService.getLockInfo(infoTo.getBid());
        lockTo.setDictName(moduleLockService.getFailNameByDid(did));
        PayInfoTo payInfoTo = moduleWxService.getPayInfoByDid(did);
        deviceVo.setInfoVo(infoToToVo(infoTo, InfoVo.class));
        deviceVo.setLockVo(lockToToVo(lockTo, LockVo.class));
        deviceVo.setPayInfoVo(payInfoToVo(payInfoTo, PayInfoVo.class));
        return deviceVo;
    }

    /*
    to对象转Vo
     */
    private InfoVo infoToToVo(Object obj, Class<?> toType) {
        mapperFactory.classMap(toType, InfoVo.class).byDefault().register();
        return mapperFactory.getMapperFacade().map(obj, InfoVo.class);
    }

    private LockVo lockToToVo(LockTo lockTo, Class<?> toType) {
        LockVo lockVo = new LockVo();
        lockVo.setLockStatus(lockTo.getLockStatus() == 1 ? "关锁" : "开锁");
        //电量百分数
        lockVo.setBatteryStat(StringUtil.Percent(lockTo.getBatteryStat().doubleValue(), 100.0));
        lockVo.setCsq(lockTo.getCsq() == 0 ? "无信号" : "有信号");
        //日期转换
        lockVo.setLastRefresh(DateUtil.dateConvert(lockTo.getLastRefresh()));
        lockVo.setElectric(lockTo.getElectric() == 0 ? "未充电" : "充电中");
        return lockVo;

    }


    private PayInfoVo payInfoToVo(PayInfoTo payInfoTo, Class<?> toType) {
        PayInfoVo payInfoVo = new PayInfoVo();
        payInfoVo.setStartTime(DateUtil.dateConvert(payInfoTo.getStartTime()));
        payInfoVo.setEndTime(DateUtil.timestampToString(payInfoTo.getEndTime(),"yyyy年MM月dd日 HH点mm分ss秒"));
        String source = payInfoTo.getOrderType() == 1 ? "晚休" : payInfoTo.getOrderType() == 2 ? "午休" : "未知";
        payInfoVo.setOrderType(source);
        return payInfoVo;
    }

}
