package com.mujugroup.data.service.impl;

import com.github.wxiaoqi.merge.annonation.MergeResult;
import com.google.gson.JsonObject;
import com.lveqia.cloud.common.util.DateUtil;
import com.lveqia.cloud.common.util.StringUtil;
import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.config.Constant;
import com.mujugroup.data.objeck.bo.ExcelBO;
import com.mujugroup.data.objeck.bo.sta.StaActive;
import com.mujugroup.data.objeck.bo.sta.StaProfit;
import com.mujugroup.data.objeck.bo.sta.StaUsage;
import com.mujugroup.data.objeck.bo.sta.StaUsageRate;
import com.mujugroup.data.service.StaBOService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service("staBOService")
public class StaBOServiceImpl implements StaBOService {


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
     * 根据粒度以及Key，获取结束时间戳, 同时确保生成的结束时间小于等于传入的结束时间
     */
    private long getEndTimestamp(int grain, String refDate, int stopTime) {
        long result = 0;
        switch (grain){
            case 1 : result = DateUtil.toTimestamp(refDate, DateUtil.TYPE_DATE_08) + Constant.TIMESTAMP_DAYS_1;break;
            case 2 : result = DateUtil.toTimestamp(refDate.substring(9), DateUtil.TYPE_DATE_08);break;
            case 3 : result = DateUtil.getTimesEndMonth(refDate);break;
        }
        if(result > stopTime || result == 0) result = stopTime;
        return result;
    }


    @Override
    @MergeResult
    public List<StaUsage> getUsage(String[] ids, int grain, int startTime, int stopTime) throws ParamException {
        List<StaUsage> list = new ArrayList<>();
        List<String> refDate = getRefDate(startTime, stopTime, grain);
        for (String key:refDate){
            list.add(new StaUsage(key, ids));
        }
        return list;
    }

    @Override
    @MergeResult
    public List<StaProfit> getProfit(String[] ids, int grain, int startTime, int stopTime) throws ParamException {
        List<StaProfit> list = new ArrayList<>();
        List<String> refDate = getRefDate(startTime, stopTime, grain);
        for (String key:refDate){
            list.add(new StaProfit(key, ids));
        }
        return list;
    }

    @Override
    @MergeResult
    public List<StaActive> getActive(String[] ids, int grain, int startTime, int stopTime) throws ParamException {
        List<StaActive> list = new ArrayList<>();
        List<String> refDate = getRefDate(startTime, stopTime, grain);
        for (String key:refDate){
            list.add(new StaActive(key, ids, getEndTimestamp(grain, key, stopTime)));
        }
        return list;
    }

    @Override
    @MergeResult
    public List<StaUsageRate> getUsageRate(String[] ids, int grain, int startTime, int stopTime) throws ParamException {
        List<StaUsageRate> list = new ArrayList<>();
        List<String> refDate = getRefDate(startTime, stopTime, grain);
        for (String key:refDate){
            list.add(new StaUsageRate(key, ids));
        }
        return list;
    }


    @Override
    @MergeResult
    public List<ExcelBO> getExcelBO(JsonObject info, int grain, int startTime, int stopTime)
            throws ParamException {
        List<ExcelBO> list = new ArrayList<>();
        List<String> refDate = getRefDate(startTime, stopTime, grain);
        for (String key:refDate){
            list.add(new ExcelBO(key, info, getEndTimestamp(grain, key, stopTime)));
        }
        return list;
    }

}
