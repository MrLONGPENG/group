package com.mujugroup.lock.service.task;

import com.lveqia.cloud.common.objeck.to.InfoTo;
import com.lveqia.cloud.common.objeck.to.PayInfoTo;
import com.lveqia.cloud.common.objeck.to.UptimeTo;
import com.lveqia.cloud.common.util.DateUtil;
import com.mujugroup.lock.model.LockFail;
import com.mujugroup.lock.model.LockRecord;
import com.mujugroup.lock.model.LockSwitch;
import com.mujugroup.lock.service.LockFailService;
import com.mujugroup.lock.service.LockRecordService;
import com.mujugroup.lock.service.LockSwitchService;
import com.mujugroup.lock.service.feign.ModuleCoreService;
import com.mujugroup.lock.service.feign.ModuleWxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class FailTask {
    private static final String TASK_NAME = "Scheduled_Lock_FailTask";
    private final Logger logger = LoggerFactory.getLogger(FailTask.class);
    private final Map<String, UptimeTo> uptimeMap = new HashMap<>();
    private final LockFailService lockFailService;
    private final LockSwitchService lockSwitchService;
    private final LockRecordService lockRecordService;
    private final ModuleWxService moduleWxService;
    private final ModuleCoreService moduleCoreService;
    private final StringRedisTemplate stringRedisTemplate;

    //每次从数据库获取的数据记录条数
    private static final int LIMIT_COUNT = 5;
    //时间间隔(单位：毫秒)（当前间隔为:30分钟）
    private static final int TIME_SPAN = 1800000;
    //电量下降值
    private static final int DOWN_NUM = 20;
    //信号量波动值
    private static final int CSQ_NUM = 5;
    //界定低电量的标准值
    private static final int LOW_POWER = 20;

    @Autowired
    public FailTask(LockFailService lockFailService, LockSwitchService lockSwitchService
            , LockRecordService lockRecordService, ModuleWxService moduleWxService
            , ModuleCoreService moduleCoreService, StringRedisTemplate stringRedisTemplate){
        this.lockFailService = lockFailService;
        this.lockSwitchService = lockSwitchService;
        this.lockRecordService = lockRecordService;
        this.moduleWxService = moduleWxService;
        this.moduleCoreService = moduleCoreService;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    //每分钟执行一次
    @Scheduled(cron = "0 0/10 * * * *")
    public void onCron() {
        uptimeMap.clear();
        try {
            Thread.sleep(new Random().nextInt(1000) + 300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(stringRedisTemplate.boundValueOps(TASK_NAME).get()!=null){
            logger.info("已经存在任务，避免重复执行->{}", new Date());
            return;
        }
        logger.info("执行任务->{}->{}", TASK_NAME, new Date());
        stringRedisTemplate.boundValueOps(TASK_NAME).set(TASK_NAME, 5, TimeUnit.MINUTES);
        long start = System.currentTimeMillis();
        getLockRecord(1, 5);
        logger.debug("FailTask date: {}", System.currentTimeMillis() - start);

    }

    private void getLockRecord(int pageNum, int pageSize) {
        //远程获取所有激活设备集合
        List<InfoTo> infoTos = moduleCoreService.getActivateInfoTo(pageNum, pageSize);
        for (InfoTo info : infoTos) {
            if(info.isIllegal()) return; // 未激活设备直接返回
            checkFail(info);
        }
        if (infoTos.size() == pageSize) {
            getLockRecord(pageNum + 1, pageSize);
        }
    }

    private void checkFail(InfoTo info) {
        List<LockRecord> recordList = lockRecordService.findByDid(info.getDid(), LIMIT_COUNT);
        if(recordList.size() == 0) return;
        if (isOffline(recordList.get(0))) {
            addSignal(info, recordList.get(0));
            return;
        }
        checkPowerDown(info, recordList);//电量下降异常
        checkSignalWave(info, recordList);//信号波动异常
        checkPowerNoElectric(info, recordList);//无法充电异常
        checkLockWithOrder(info, recordList.get(0));//订单状态下开关锁异常
        checkLockNoCsq(info, recordList.get(0));//低信号
        checkPowerLow(info, recordList.get(0));//低电量
    }


    /**
     * 无法充电异常
     *
     */
    private void checkPowerNoElectric(InfoTo info, List<LockRecord> list) {
        if (list.size() < 3) return;
        //获取所有无法充电的异常数据
        boolean hasError = true;
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).getLastRefresh().getTime() - list.get(i - 1).getLastRefresh().getTime() < TIME_SPAN) {
                //设备充电电流不为0且当前设备剩余电量不为百分之百(设备正在充电中)
                if (isNoEqual(list.get(i).getElectric(),0) && isNoEqual(list.get(i).getBatteryStat(),100)) {
                    //当前索引的设备剩余电量与最后一个设备剩余电量相同,则充电异常(无法充电)
                    hasError &= list.get(i).getBatteryStat().equals(list.get(0).getBatteryStat());
                }else {
                    hasError = false;
                }
            }
        }
        if (hasError) {
            LockFail lockFail = lockFailService.getFailInfoByDid(info.getDid(), LockFail.ErrorType.TYPE_POWER_CHARGE);
            if (lockFail == null) {
                lockFail = new LockFail();
                lockFailService.getModel(lockFail, LockFail.ErrorType.TYPE_POWER_CHARGE, info, list.get(0));
                lockFailService.insert(lockFail);
            } else if (isChange(info, lockFail)) {
                // update
                lockFailService.modifyModel(lockFail, info.getAid(), info.getHid(), info.getOid(), new Date());
            }
        }
    }


    /**
     * 订单状态下开关锁异常
     */
    private void checkLockWithOrder(InfoTo info, LockRecord record) {
        //当前设备处于开锁状态
        if (record.getLockStatus()!= null && record.getLockStatus().equals(2)) {
            //远程获取微信端订单明细
            PayInfoTo payInfoTo = moduleWxService.getPayInfoByDid(info.getDid(), PayInfoTo.TYPE_NIGHT);
            //获得使用时间
            UptimeTo uptimeTo = getUptimeTo(info);
            if(uptimeTo == null) return;
            long seconds = DateUtil.getTimesNoDate(record.getLastRefresh());
            boolean isNoonTime = uptimeTo.isNoonTime(seconds);
            boolean isUsingTime = uptimeTo.isUsingTime(seconds);
            //当前设备使用时间不在运行时间之内
            if(!isNoonTime && !isUsingTime){
                //拥有订单明细(非使用时段开锁)
                if (payInfoTo != null && DateUtil.getTimesMorning() < payInfoTo.getEndTime()) {
                    LockSwitch lockSwitch = lockSwitchService.getLastOpenRecord(record.getDid());
                    long openTime = DateUtil.getTimesNoDate(lockSwitch.getLocalTime());
                    if (uptimeTo.isNoonTime(openTime) || uptimeTo.isUsingTime(openTime)) {
                        //获得超时未关锁的异常数据
                        LockFail lockFailTimeOut = lockFailService.getFailInfoByDid(info.getDid(), LockFail.ErrorType.TYPE_SWITCH_TIMEOUT);
                        if (lockFailTimeOut == null) {
                            lockFailTimeOut = new LockFail();
                            lockFailService.getModel(lockFailTimeOut, LockFail.ErrorType.TYPE_SWITCH_TIMEOUT, info, record);
                            lockFailService.insert(lockFailTimeOut);
                        } else if (isChange(info, lockFailTimeOut)) {
                            lockFailService.modifyModel(lockFailTimeOut, info.getAid(), info.getHid(), info.getOid(), new Date());
                        }
                    } else {
                        //获得非使用时间异常开锁数据
                        LockFail lockFailUsing = lockFailService.getFailInfoByDid(info.getDid(), LockFail.ErrorType.TYPE_SWITCH_USING);
                        if (lockFailUsing == null) {
                            lockFailUsing = new LockFail();
                            lockFailService.getModel(lockFailUsing, LockFail.ErrorType.TYPE_SWITCH_USING, info, record);
                            lockFailService.insert(lockFailUsing);
                        } else if (isChange(info, lockFailUsing)) {
                            lockFailService.modifyModel(lockFailUsing, info.getAid(), info.getHid(), info.getOid(), new Date());
                        }

                    }
                } else { //无订单开锁
                    addNoOrderFail(info, record);

                }
            } else if(isUsingTime){  // 非午休运行时间无订单开锁
                if (payInfoTo == null || payInfoTo.getEndTime() < record.getLastRefresh().getTime() / 1000) {
                    addNoOrderFail(info, record);
                }
            }
        }
    }

    /**
     * 缓存运行时间（生命周期单次运行）
     */
    private UptimeTo getUptimeTo(InfoTo info) {
        String key = info.getAid() + "-" + info.getHid() + "-" + info.getOid();
        if (uptimeMap.containsKey(key)) return uptimeMap.get(key);
        UptimeTo uptimeTo = moduleWxService.getUptimeTo(info.getAid(), info.getHid(), info.getOid());
        uptimeMap.put(key, uptimeTo);
        return uptimeTo;
    }

    private void addNoOrderFail(InfoTo info, LockRecord lockRecord) {
        //获得无订单异常开锁数据
        LockFail lockFailOrder = lockFailService.getFailInfoByDid(info.getDid(), LockFail.ErrorType.TYPE_SWITCH_ORDER);
        if (lockFailOrder == null) {
            lockFailOrder = new LockFail();
            lockFailService.getModel(lockFailOrder, LockFail.ErrorType.TYPE_SWITCH_ORDER, info, lockRecord);
            lockFailService.insert(lockFailOrder);
        } else if (isChange(info, lockFailOrder)) {
            lockFailService.modifyModel(lockFailOrder, info.getAid(), info.getHid(), info.getOid(), new Date());
        }
    }

    /**
     * 电量下降异常
     *
     * @param info
     * @param recordList
     */
    private void checkPowerDown(InfoTo info, List<LockRecord> recordList) {
        if (recordList.size() < 3) return;
        boolean hasError = true;
        for (int i = 1; i < recordList.size(); i++) {
            //如果相邻数据记录的时间差小于30分钟,且相邻数据的剩余电量的差值大于20，则认为是电量下降异常
            boolean result = (recordList.get(i).getCrtTime().getTime() - recordList.get(i - 1).getCrtTime().getTime() < TIME_SPAN)
                    && recordList.get(i).getBatteryStat()!=null && recordList.get(i - 1).getBatteryStat()!=null
                    && Math.abs(recordList.get(i).getBatteryStat() - recordList.get(i - 1).getBatteryStat()) > DOWN_NUM;
            hasError &= result;
        }
        if (hasError) {
            LockFail lockFailPowerDown = lockFailService.getFailInfoByDid(info.getDid(), LockFail.ErrorType.TYPE_POWER_DOWN);
            if (lockFailPowerDown == null) {
                lockFailPowerDown = new LockFail();
                lockFailService.getModel(lockFailPowerDown, LockFail.ErrorType.TYPE_POWER_DOWN, info, recordList.get(0));
                lockFailService.insert(lockFailPowerDown);
            } else if (isChange(info, lockFailPowerDown)) {
                // update
                lockFailService.modifyModel(lockFailPowerDown, info.getAid(), info.getHid(), info.getOid(), new Date());
            }
        }
    }

    /**
     * 信号波动异常
     */
    private void checkSignalWave(InfoTo info, List<LockRecord> recordList) {
        if (recordList.size() < 3) return;
        boolean hasError = true;
        for (int i = 1; i < recordList.size(); i++) {
            if (recordList.get(i).getCrtTime().getTime() - recordList.get(i - 1).getCrtTime().getTime() < TIME_SPAN) {
                //设备信号量不为0
                if (isNoEqual(recordList.get(i).getCsq(),0) && recordList.get(i - 1).getCsq()!= null) {
                    //当前索引的设备信号量与其相邻设备信号量差值大于20,则表明信号波动异常
                    hasError &= Math.abs(recordList.get(i).getCsq() - recordList.get(i - 1).getCsq()) > CSQ_NUM;
                }else {
                    hasError = false;
                }
            }
        }
        if (hasError) {
            LockFail lockFailSignal = lockFailService.getFailInfoByDid(info.getDid(), LockFail.ErrorType.TYPE_SIGNAL_WAVE);
            if (lockFailSignal == null) {
                lockFailSignal = new LockFail();
                lockFailService.getModel(lockFailSignal, LockFail.ErrorType.TYPE_SIGNAL_WAVE, info, recordList.get(0));
                lockFailService.insert(lockFailSignal);
            } else if (isChange(info, lockFailSignal)) {
                // update
                lockFailService.modifyModel(lockFailSignal, info.getAid(), info.getHid(), info.getOid(), new Date());
            }
        }
    }



    /**
     * 低信号
     */
    private void checkLockNoCsq(InfoTo info, LockRecord lockRecord) {
        if (lockRecord!=null && lockRecord.getCsq()<= CSQ_NUM && lockRecord.getCsq()> 0) {
            LockFail lockNoSignal = lockFailService.getFailInfoByDid(info.getDid(), LockFail.ErrorType.TYPE_SIGNAL_LOW);
            if (lockNoSignal == null) {
                lockNoSignal = new LockFail();
                lockFailService.getModel(lockNoSignal, LockFail.ErrorType.TYPE_SIGNAL_LOW, info, lockRecord);
                lockFailService.insert(lockNoSignal);
            } else if (isChange(info, lockNoSignal)) {
                lockFailService.modifyModel(lockNoSignal, info.getAid(), info.getHid(), info.getOid(), new Date());
            }
        }
    }

    /**
     * 低电量异常
     *
     * @param info
     * @param lockRecord
     */
    private void checkPowerLow(InfoTo info, LockRecord lockRecord) {
        if (lockRecord.getBatteryStat()!=null && lockRecord.getBatteryStat() < LOW_POWER) {
            LockFail lockLowPower = lockFailService.getFailInfoByDid(info.getDid(), LockFail.ErrorType.TYPE_POWER_LOW);
            if (lockLowPower == null) {
                lockLowPower = new LockFail();
                lockFailService.getModel(lockLowPower, LockFail.ErrorType.TYPE_POWER_LOW, info, lockRecord);
                lockFailService.insert(lockLowPower);
            } else if (isChange(info, lockLowPower)) {
                lockFailService.modifyModel(lockLowPower, info.getAid(), info.getHid(), info.getOid(), new Date());
            }
        }
    }

    //判断是否离线
    private boolean isOffline(LockRecord lockRecord) {
        //获取最近一次的上传时间与当前时间的时间差
        return new Date().getTime() - lockRecord.getCrtTime().getTime() > TIME_SPAN;
    }

    //添加离线记录
    private void addSignal(InfoTo info, LockRecord lockRecord) {
        LockFail lockFail = lockFailService.getFailInfoByDid(info.getDid(),LockFail.ErrorType.TYPE_SIGNAL_OFFLINE);
        if (lockFail == null) {
            lockFail = new LockFail();
            lockFailService.getModel(lockFail, LockFail.ErrorType.TYPE_SIGNAL_OFFLINE, info, lockRecord);
            lockFailService.insert(lockFail);
        }else if (isChange(info, lockFail)) {
            lockFailService.modifyModel(lockFail, info.getAid(), info.getHid(), info.getOid(), new Date());
        }
    }


    /**
     * 判断不相等，避免空指针
     */
    private boolean isNoEqual(Integer i, int j) {
        return i!=null && i!=j;
    }

    /**
     * 代理商/医院/科室 关系改变
     */
    private boolean isChange(InfoTo info, LockFail fail) {
        return !info.getAid().equals(String.valueOf(fail.getAid()))
                || !info.getHid().equals(String.valueOf(fail.getHid()))
                || !info.getOid().equals(String.valueOf(fail.getOid()));
    }
}


