package com.mujugroup.data.service.impl;

import com.github.wxiaoqi.merge.annonation.MergeResult;
import com.lveqia.cloud.common.DateUtil;
import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.util.Constant;
import com.mujugroup.data.bean.StaActive;
import com.mujugroup.data.bean.StaUsage;
import com.mujugroup.data.bean.StaUsageRate;
import com.mujugroup.data.service.StatisticsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("statisticsService")
public class StatisticsServiceImpl implements StatisticsService {

    /**
     * 指定时间间隔以及粒度  获取日期结果集
     * @param startTime 开始时间戳
     * @param stopTime  结束时间戳
     * @param grain     粒度 类型(1:日 2:周 3:月) 默认日
     */
    @Override
    public List<String> getRefDate(int startTime, int stopTime, int grain) throws ParamException {
        int morning = (int) DateUtil.getTimesMorning();
        if(startTime > morning || stopTime > morning || startTime == stopTime)
            throw new ParamException("开始结束时间戳不能相等且不能大于当天凌晨");
        String refDate, curDate = getRefString(morning, grain);
        List<String> list = new ArrayList<>();
        int count = 0;
        while (startTime < stopTime) {
            if(count++ > 50) throw new ParamException("指定时间范围太大，所查结果大于50，无法查询");
            refDate = getRefString(startTime, grain);
            if(!refDate.equals(curDate))list.add(refDate);
            switch (grain){
                case 1:startTime += Constant.TIMESTAMP_DAYS_1; break;
                case 2:startTime += Constant.TIMESTAMP_DAYS_7; break;
                case 3:startTime += Constant.TIMESTAMP_DAYS_1*DateUtil.getDay(refDate);break;
            }
        }
        return list;
    }

    /**
     * 根据时间戳以及粒度获取字符串时间格式
     */
    private static String getRefString(int timestamp, int grain) throws ParamException {
        switch (grain){
            case 1: return DateUtil.timestampToDays(timestamp);
            case 2: return DateUtil.timestampToWeek(timestamp);
            case 3: return DateUtil.timestampToMonth(timestamp);
        }
        throw new ParamException("粒度类型只能为(1:日 2:周 3:月)");
    }

    /**
     * 根据粒度以及Key，获取结束时间戳
     */
    private long getEndTimestamp(int grain, String refDate) {
        switch (grain){
            case 1 : return DateUtil.toTimestamp(refDate, DateUtil.TYPE_DATE_08) + Constant.TIMESTAMP_DAYS_1;
            case 2 : return DateUtil.toTimestamp(refDate.substring(9), DateUtil.TYPE_DATE_08);
            case 3 : return DateUtil.getTimesEndMonth(refDate);
        }
        return 0;
    }


    @Override
    @MergeResult
    public List<StaUsage> getUsage(int aid, int hid, int oid, int grain, int startTime, int stopTime) throws ParamException {
        List<StaUsage> list = new ArrayList<>();
        List<String> refDate = getRefDate(startTime, stopTime, grain);
        for (String key:refDate){
            list.add(new StaUsage(key, aid, hid, oid));
        }
        return list;
    }

    @Override
    @MergeResult
    public List<StaActive> getActive(int aid, int hid, int oid, int grain, int startTime, int stopTime)
            throws ParamException {
        List<StaActive> list = new ArrayList<>();
        List<String> refDate = getRefDate(startTime, stopTime, grain);
        for (String key:refDate){
            list.add(new StaActive(key, aid, hid, oid, getEndTimestamp(grain, key)));
        }
        return list;
    }

    @Override
    @MergeResult
    public List<StaUsageRate> getUsageRate(int aid, int hid, int oid, int grain, int startTime, int stopTime)
            throws ParamException {
        List<StaUsageRate> list = new ArrayList<>();
        List<String> refDate = getRefDate(startTime, stopTime, grain);
        for (String key:refDate){
            list.add(new StaUsageRate(key, aid, hid, oid));
        }
        return list;
    }
}
