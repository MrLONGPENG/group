package com.mujugroup.lock.service.task;

import com.lveqia.cloud.common.objeck.to.InfoTo;
import com.lveqia.cloud.common.objeck.to.PayInfoTo;
import com.lveqia.cloud.common.objeck.to.UptimeTo;
import com.lveqia.cloud.common.util.DateUtil;
import com.mujugroup.lock.mapper.LockRecordMapper;
import com.mujugroup.lock.model.LockFail;
import com.mujugroup.lock.model.LockRecord;
import com.mujugroup.lock.service.LockFailService;
import com.mujugroup.lock.service.feign.ModuleCoreService;
import com.mujugroup.lock.service.feign.ModuleWxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class FailTask {
    private final Logger logger = LoggerFactory.getLogger(FailTask.class);
    private final ModuleCoreService moduleCoreService;
    private final LockRecordMapper lockRecordMapper;
    private final ModuleWxService moduleWxService;
    private final LockFailService lockFailService;
    //每次从数据库获取的数据记录条数
    private static final int LIMIT_COUNT = 5;
    //时间间隔(单位：毫秒)（当前间隔为:30分钟）
    public static final int TIME_SPAN = 1800000;
    //电量下降值
    private static final int DOWN_NUM = 20;
    //信号量波动值
    private static final int CSQ_NUM = 5;
    //界定低电量的标准值
    private static final int LOW_POWER = 20;

    @Autowired
    public FailTask(ModuleCoreService moduleCoreService, LockRecordMapper lockRecordMapper, ModuleWxService moduleWxService, LockFailService lockFailService) {
        this.moduleCoreService = moduleCoreService;
        this.lockRecordMapper = lockRecordMapper;
        this.moduleWxService = moduleWxService;
        this.lockFailService = lockFailService;
    }

    //每分钟执行一次
    @Scheduled(cron = "0 0/1 * * * *")
    public void onCron() {
        long start = System.currentTimeMillis();
        getLockRecord(1, 5);
        logger.debug("FailTask date: {}", System.currentTimeMillis() - start);

    }

    private void getLockRecord(int pageNum, int pageSize) {
        //远程获取所有激活设备集合
        List<InfoTo> infoTos = moduleCoreService.getActivateInfoTo(pageNum, pageSize);
        for (InfoTo info : infoTos) {
            checkFail(info);
        }
        if (infoTos.size() == pageSize) {
            getLockRecord(pageNum + 1, pageSize);
        }
    }

    private void checkFail(InfoTo info) {
        List<LockRecord> recordList = lockRecordMapper.findByDid(info.getDid(), LIMIT_COUNT);
        if(recordList.size() == 0) return;
        if (isOffline(recordList.get(0))) {
            addSignal(info, recordList.get(0));
            return;
        }
        checkPowerNoElectric(info, recordList);//无法充电异常
        checkPowerDown(info, recordList);//电量下降异常
        checkLockWithOrder(info, recordList);//订单状态下开关锁异常
        checkSignalWave(info, recordList);//信号波动异常
        checkLockNoCsq(info, recordList.get(0));//低信号
        checkPowerLow(info, recordList);//低电量
    }


    /**
     * 无法充电异常
     *
     * @param info
     * @param list
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
            LockFail lockFail = lockFailService.getFailInfoByDid(info.getDid(), LockFail.FailType.TYPE_POWER
                    , LockFail.FE_PW_CHARGE);
            if (lockFail == null) {
                lockFail = new LockFail();
                lockFailService.getModel(lockFail, Integer.parseInt(info.getAid()), Integer.parseInt(info.getHid()), Integer.parseInt(info.getOid())
                        , list.get(0).getDid(), LockFail.FailType.TYPE_POWER
                        , LockFail.FE_PW_CHARGE, list.get(0).getLastRefresh(), list.get(0).getLockId());
                lockFailService.insert(lockFail);
            } else if (!info.getAid().equals(lockFail.getAid()) || !info.getHid().equals(lockFail.getHid())
                    || !info.getOid().equals(lockFail.getOid())) {
                // update
                lockFailService.modifyModel(lockFail, info.getAid(), info.getHid(), info.getOid(), new Date());
            }
        }
    }

    /**
     * 判断不相等，避免空指针
     */
    private boolean isNoEqual(Integer i, int j) {
        return i!=null && i!=j;
    }

    /**
     * 订单状态下开关锁异常
     */
    private void checkLockWithOrder(InfoTo info, List<LockRecord> list) {
        if (list.size() < 3) return;
        //当前设备处于开锁状态
        if (list.get(0).getLockStatus()!= null && list.get(0).getLockStatus().equals(2)) {
            //远程获取微信端订单明细
            PayInfoTo payInfoTo = moduleWxService.getPayInfoByDid(info.getDid());
            //获得使用时间
            UptimeTo uptimeTo = moduleWxService.getUptimeTo(Integer.parseInt(info.getAid()), Integer.parseInt(info.getHid()), Integer.parseInt(info.getOid()));

            long seconds = list.get(0).getLastRefresh().getTime() / 1000 - DateUtil.getTimesMorning();
            //当前设备使用时间不在运行时间之内
            if ((seconds < uptimeTo.getNoonStartTime() && seconds > uptimeTo.getStopTime())
                    || (seconds > uptimeTo.getNoonStopTime() && seconds < uptimeTo.getStartTime())) {
                //拥有订单明细(非使用时段开锁)
                if (payInfoTo != null && DateUtil.getTimesMorning() < payInfoTo.getEndTime()) {
                    if (list.get(0).getLastRefresh().getTime() / 1000 > payInfoTo.getEndTime()) {
                        //获得非使用时间异常开锁数据
                        LockFail lockFailUsing = lockFailService.getFailInfoByDid(info.getDid(), LockFail.FailType.TYPE_SWITCH
                                , LockFail.FE_SW_USING);
                        if (lockFailUsing == null) {
                            lockFailUsing = new LockFail();
                            lockFailService.getModel(lockFailUsing, Integer.parseInt(info.getAid()), Integer.parseInt(info.getHid()), Integer.parseInt(info.getOid())
                                    , list.get(0).getDid(), LockFail.FailType.TYPE_SWITCH
                                    , LockFail.FE_SW_USING, list.get(0).getLastRefresh(), list.get(0).getLockId());
                            lockFailService.insert(lockFailUsing);
                        } else if (!info.getAid().equals(lockFailUsing.getAid()) || !info.getHid().equals(lockFailUsing.getHid())
                                || !info.getOid().equals(lockFailUsing.getOid())) {
                            lockFailService.modifyModel(lockFailUsing, info.getAid(), info.getHid(), info.getOid(), new Date());
                        }
                    } else {
                        //获得超时未关锁的异常数据
                        LockFail lockFailTimeOut = lockFailService.getFailInfoByDid(info.getDid(), LockFail.FailType.TYPE_SWITCH
                                , LockFail.FE_SW_TIMEOUT);
                        if (lockFailTimeOut == null) {
                            lockFailTimeOut = new LockFail();
                            lockFailService.getModel(lockFailTimeOut, Integer.parseInt(info.getAid()), Integer.parseInt(info.getHid()), Integer.parseInt(info.getOid())
                                    , list.get(0).getDid(), LockFail.FailType.TYPE_SWITCH
                                    , LockFail.FE_SW_TIMEOUT, list.get(0).getLastRefresh(), list.get(0).getLockId());
                            lockFailService.insert(lockFailTimeOut);
                        } else if (!info.getAid().equals(lockFailTimeOut.getAid()) || !info.getHid().equals(lockFailTimeOut.getHid())
                                || !info.getOid().equals(lockFailTimeOut.getOid())) {
                            lockFailService.modifyModel(lockFailTimeOut, info.getAid(), info.getHid(), info.getOid(), new Date());
                        }
                    }
                } else { //无订单开锁
                    addNoOrderFail(info, list.get(0));

                }
            } else {
                // 正常时间无订单开锁
                if (payInfoTo == null || payInfoTo.getEndTime() < list.get(0).getLastRefresh().getTime() / 1000) {
                    addNoOrderFail(info, list.get(0));
                }

            }
        }
    }

    private void addNoOrderFail(InfoTo info, LockRecord lockRecord) {
        //获得无订单异常开锁数据
        LockFail lockFailOrder = lockFailService.getFailInfoByDid(info.getDid(), LockFail.FailType.TYPE_SWITCH
                , LockFail.FE_SW_ORDER);
        if (lockFailOrder == null) {
            lockFailOrder = new LockFail();
            lockFailService.getModel(lockFailOrder, Integer.parseInt(info.getAid()), Integer.parseInt(info.getHid()), Integer.parseInt(info.getOid())
                    , lockRecord.getDid(), LockFail.FailType.TYPE_SWITCH
                    , LockFail.FE_SW_ORDER, lockRecord.getLastRefresh(), lockRecord.getLockId());
            lockFailService.insert(lockFailOrder);
        } else if (!info.getAid().equals(lockFailOrder.getAid()) || !info.getHid().equals(lockFailOrder.getHid())
                || !info.getOid().equals(lockFailOrder.getOid())) {
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
            boolean result = (recordList.get(i).getLastRefresh().getTime() - recordList.get(i - 1).getLastRefresh().getTime() < TIME_SPAN)
                    && recordList.get(i).getBatteryStat()!=null && recordList.get(i - 1).getBatteryStat()!=null
                    && Math.abs(recordList.get(i).getBatteryStat() - recordList.get(i - 1).getBatteryStat()) > DOWN_NUM;
            hasError &= result;
        }
        if (hasError) {
            LockFail lockFailPowerDown = lockFailService.getFailInfoByDid(info.getDid(), LockFail.FailType.TYPE_POWER
                    , LockFail.FE_PW_DOWN);
            if (lockFailPowerDown == null) {
                lockFailPowerDown = new LockFail();
                lockFailService.getModel(lockFailPowerDown, Integer.parseInt(info.getAid()), Integer.parseInt(info.getHid())
                        , Integer.parseInt(info.getOid()), recordList.get(0).getDid(), LockFail.FailType.TYPE_POWER
                        , LockFail.FE_PW_DOWN, recordList.get(0).getLastRefresh(), recordList.get(0).getLockId());
                lockFailService.insert(lockFailPowerDown);
            } else if (!info.getAid().equals(lockFailPowerDown.getAid()) || !info.getHid().equals(lockFailPowerDown.getHid())
                    || !info.getOid().equals(lockFailPowerDown.getOid())) {
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
            if (recordList.get(i).getLastRefresh().getTime() - recordList.get(i - 1).getLastRefresh().getTime() < TIME_SPAN) {
                //设备信号量不为0
                if (isNoEqual(recordList.get(i).getCsq(),0)) {
                    //当前索引的设备信号量与其相邻设备信号量差值大于20,则表明信号波动异常
                    hasError &= Math.abs(recordList.get(i).getCsq() - recordList.get(i - 1).getCsq()) > CSQ_NUM;
                }else {
                    hasError = false;
                }
            }
        }
        if (hasError) {
            LockFail lockFailSignal = lockFailService.getFailInfoByDid(info.getDid(), LockFail.FailType.TYPE_SIGNAL
                    , LockFail.FE_SG_WAVE);
            if (lockFailSignal == null) {
                lockFailSignal = new LockFail();
                lockFailService.getModel(lockFailSignal, Integer.parseInt(info.getAid()), Integer.parseInt(info.getHid())
                        , Integer.parseInt(info.getOid()) , recordList.get(0).getDid(), LockFail.FailType.TYPE_SIGNAL
                        , LockFail.FE_SG_WAVE, recordList.get(0).getLastRefresh(), recordList.get(0).getLockId());
                lockFailService.insert(lockFailSignal);
            } else if (!info.getAid().equals(lockFailSignal.getAid()) || !info.getHid().equals(lockFailSignal.getHid())
                    || !info.getOid().equals(lockFailSignal.getOid())) {
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
            LockFail lockNoSignal = lockFailService.getFailInfoByDid(info.getDid(), LockFail.FailType.TYPE_SIGNAL
                    , LockFail.FE_SG_LOW);
            if (lockNoSignal == null) {
                lockNoSignal = new LockFail();
                lockFailService.getModel(lockNoSignal, Integer.parseInt(info.getAid()), Integer.parseInt(info.getHid()), Integer.parseInt(info.getOid())
                        , lockRecord.getDid(), LockFail.FailType.TYPE_SIGNAL
                        , LockFail.FE_SG_LOW, lockRecord.getLastRefresh(), lockRecord.getLockId());
                lockFailService.insert(lockNoSignal);
            } else if (!info.getAid().equals(lockNoSignal.getAid()) || !info.getHid().equals(lockNoSignal.getHid())
                    || !info.getOid().equals(lockNoSignal.getOid())) {
                lockFailService.modifyModel(lockNoSignal, info.getAid(), info.getHid(), info.getOid(), new Date());
            }
        }
    }

    /**
     * 低电量异常
     *
     * @param info
     * @param list
     */
    private void checkPowerLow(InfoTo info, List<LockRecord> list) {
        if (list.size() < 3) return;
        if (list.get(0).getBatteryStat()!=null && list.get(0).getBatteryStat() < LOW_POWER) {
            LockFail lockLowPower = lockFailService.getFailInfoByDid(info.getDid(), LockFail.FailType.TYPE_POWER , LockFail.FE_PW_LOW);
            if (lockLowPower == null) {
                lockLowPower = new LockFail();
                lockFailService.getModel(lockLowPower, Integer.parseInt(info.getAid()), Integer.parseInt(info.getHid()), Integer.parseInt(info.getOid())
                        , list.get(0).getDid(), LockFail.FailType.TYPE_POWER
                        , LockFail.FE_PW_LOW, list.get(0).getLastRefresh(), list.get(0).getLockId());
                lockFailService.insert(lockLowPower);
            } else if (!info.getAid().equals(lockLowPower.getAid()) || !info.getHid().equals(lockLowPower.getHid())
                    || !info.getOid().equals(lockLowPower.getOid())) {
                lockFailService.modifyModel(lockLowPower, info.getAid(), info.getHid(), info.getOid(), new Date());
            }
        }
    }

    //判断是否离线
    private boolean isOffline(LockRecord lockRecord) {
        //获取最近一次的上传时间与当前时间的时间差
        return new Date().getTime() - lockRecord.getLastRefresh().getTime() > TIME_SPAN;
    }

    //添加离线记录
    private void addSignal(InfoTo info, LockRecord lockRecord) {
        LockFail lockFail = lockFailService.getFailInfoByDid(info.getDid(),LockFail.FailType.TYPE_SIGNAL
                , LockFail.FE_SG_OFFLINE);
        if (lockFail == null) {
            lockFail = new LockFail();
            lockFailService.getModel(lockFail, Integer.parseInt(info.getAid()), Integer.parseInt(info.getHid()), Integer.parseInt(info.getOid())
                    , lockRecord.getDid(), LockFail.FailType.TYPE_SIGNAL
                    , LockFail.FE_SG_OFFLINE, lockRecord.getLastRefresh(), lockRecord.getLockId());
            lockFailService.insert(lockFail);
        }else if (!info.getAid().equals(lockFail.getAid()) || !info.getHid().equals(lockFail.getHid())
                || !info.getOid().equals(lockFail.getOid())) {
            lockFailService.modifyModel(lockFail, info.getAid(), info.getHid(), info.getOid(), new Date());
        }
    }
}


