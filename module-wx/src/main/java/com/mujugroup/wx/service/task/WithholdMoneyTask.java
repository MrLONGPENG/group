package com.mujugroup.wx.service.task;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lveqia.cloud.common.objeck.to.DataTo;
import com.lveqia.cloud.common.objeck.to.OrderTo;
import com.lveqia.cloud.common.util.DateUtil;
import com.mujugroup.wx.model.WxDeductionRecord;
import com.mujugroup.wx.service.WxDeductionRecordService;
import com.mujugroup.wx.service.WxOrderService;
import com.mujugroup.wx.service.feign.ModuleLockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class WithholdMoneyTask {
    private final ModuleLockService moduleLockService;
    private final WxOrderService wxOrderService;
    private final WxDeductionRecordService wxDeductionRecordService;
    private final int TIME_OUT_HALFHOUR = 1800;//超时半小时
    private final int TIME_OUT_THREEHOUR = 60 * 60 * 3;//超时三小时
    private final int TIME_OUT_ONEDAY = 12 * 60 * 60;//超时十二小时
    private final int HALFHOUR_MONEY = 10;//不超过半小时扣费金额
    private final int GTHALFHOUR_LTTHREEHOUR_MONEY = 20;//大于半小时不超过三小时扣费金额
    private final int GTTHREEHOUR_LTONEDAY_MONEY = 40;//大于三小时不超过一天扣费金额
    private final Logger logger = LoggerFactory.getLogger(WithholdMoneyTask.class);
    private final int TIME_SPAN = 70 * 60 * 1000;//运行时间间隔为十分钟

    @Autowired

    public WithholdMoneyTask(ModuleLockService moduleLockService, WxOrderService wxOrderService
            , WxDeductionRecordService wxDeductionRecordService) {
        this.moduleLockService = moduleLockService;
        this.wxOrderService = wxOrderService;
        this.wxDeductionRecordService = wxDeductionRecordService;
    }

    @Scheduled(cron = "0 0/1 * * * *")//每十分钟执行一次
    public void onCron() {
        getList(1, 5);
    }

    /**
     * 获取超时记录
     */
    private void getList(int pageNum, int pageSize) {
        //获取超时记录的did,lastrefresh
        List<DataTo> list = moduleLockService.getFailTimeoutRecordList(pageNum, pageSize);
        if (list == null) return;
        PageInfo pageInfo = PageInfo.of(list);
        for (DataTo dataTo : list) {
            //根据did,lastrefresh获取第一条开关锁记录
            DataTo model = moduleLockService.getRecordByDidAndLastRefresh(dataTo.getDid(), dataTo.getDate().getTime());
            if (model != null && model.getStatus() == 2) {
                logger.warn("当前数据有误,查询的did为{},lastRefresh为{}", dataTo.getDid(), dataTo.getDate());
                return;
            }
            OrderTo orderTo = wxOrderService.getOrderByCondition(dataTo.getDid());
            if (orderTo != null ) {
                int endTime = DateUtil.getTimesNoDate(new Date(orderTo.getEndTime() * 1000));
                if (model != null && model.getStatus() == 1) {//关锁状态
                    int closeTime = DateUtil.getTimesNoDate(model.getDate());
                    if (endTime < closeTime && (System.currentTimeMillis()-model.getDate().getTime()) < TIME_SPAN) {
                        updateOrInsert(dataTo.getDid(), orderTo.getOpenId(), orderTo.getTradeNo(), closeTime-endTime);
                    }
                } else { // 无开关锁记录
                    updateOrInsert(dataTo.getDid(), orderTo.getOpenId(), orderTo.getTradeNo(), DateUtil.getTimesNoDate()- endTime);
                }

            } else {
                logger.warn("无此订单,查询的did为{}", dataTo.getDid());
            }
        }

        if (pageInfo.getPages() > pageNum) {
            getList(pageNum + 1, pageSize);
        }
    }

    private void updateOrInsert(long did, String openId, String tradeNo, int timeout) {
        String date = DateUtil.dateToString(new Date(),DateUtil.TYPE_DATE_10);
        WxDeductionRecord wxDeductionRecord = wxDeductionRecordService.isExist(date, openId, did);
        if (wxDeductionRecord == null) {
            wxDeductionRecord = new WxDeductionRecord();
            wxDeductionRecord.setDid(did);
            wxDeductionRecord.setOpenId(openId);
            wxDeductionRecord.setExplain("超时未关锁");
            wxDeductionRecord.setDay(date);
            wxDeductionRecord.setType(WxDeductionRecord.TIME_OUT_DEDUCTION);//设置扣费类型为超时扣费
            wxDeductionRecord.setTradeNo(tradeNo);
            wxDeductionRecord.setTimeout(timeout);
            wxDeductionRecord.setForfeit(getDeductionPrice(timeout));//扣费金额
            wxDeductionRecord.setCrtTime(new Date());
            wxDeductionRecordService.insert(wxDeductionRecord);

        } else {
            WxDeductionRecord wxUpdate = new WxDeductionRecord();
            wxUpdate.setId(wxDeductionRecord.getId());
            wxUpdate.setTimeout(timeout);
            wxUpdate.setForfeit(getDeductionPrice(timeout));//扣费金额
            wxDeductionRecordService.update(wxUpdate);
        }

    }

    private int getDeductionPrice(int timeout) {
        if (timeout < TIME_OUT_HALFHOUR) {
            return HALFHOUR_MONEY;
        } else if (timeout < TIME_OUT_THREEHOUR) {
            return GTHALFHOUR_LTTHREEHOUR_MONEY;
        } else {
            return GTTHREEHOUR_LTONEDAY_MONEY;
        }
    }
}
