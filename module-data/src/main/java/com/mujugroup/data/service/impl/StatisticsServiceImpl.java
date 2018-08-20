package com.mujugroup.data.service.impl;

import com.github.wxiaoqi.merge.annonation.MergeResult;
import com.lveqia.cloud.common.DateUtil;
import com.mujugroup.data.bean.ActiveBean;
import com.mujugroup.data.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("statisticsService")
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    public StatisticsServiceImpl() {

    }

    @Override
    @MergeResult
    public List<ActiveBean> activeStatistics(int aid, int hid, int oid, int grain, int startTime, int stopTime) {
        List<ActiveBean> list = new ArrayList<>();
        List<String> refDate = getRefDate(startTime, stopTime, grain);
        for (String key:refDate){
            list.add(new ActiveBean(key));
        }
        return list;
    }

    private List<String> getRefDate(int startTime, int stopTime, int grain) {
        String refDate;
        List<String> list = new ArrayList<>();
        while (startTime < stopTime) {
            switch (grain){
                case 1:
                    list.add(DateUtil.timestampToDays(startTime));
                    startTime += 24*60*60;
                    break;
                case 2:
                    list.add(DateUtil.timestampToWeek(startTime));
                    startTime += 7*24*60*60;
                    break;
                case 3:
                    list.add(refDate = DateUtil.timestampToMonth(startTime));
                    startTime += 24*60*60*DateUtil.getDay(refDate);
                    break;
            }
        }
        return list;
    }
}
