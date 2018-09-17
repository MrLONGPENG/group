package com.mujugroup.data.service.impl;

import com.github.wxiaoqi.merge.annonation.MergeResult;
import com.lveqia.cloud.common.util.DateUtil;
import com.lveqia.cloud.common.config.Constant;
import com.mujugroup.data.objeck.bo.ProfitBO;
import com.mujugroup.data.objeck.bo.UsageBO;
import com.mujugroup.data.service.OverviewService;
import org.springframework.stereotype.Service;


@Service("overviewService")
public class OverviewServiceImpl implements OverviewService {

    @Override
    @MergeResult
    public UsageBO usage(int aid, long timestamp) {
        return new UsageBO(aid, checkTimestamp(timestamp));
    }

    @Override
    @MergeResult
    public ProfitBO profit(int aid, long timestamp) {
        return new ProfitBO(aid, checkTimestamp(timestamp));
    }
    /**
     * 检查时间戳，去除凌晨之后的时间，默认今日凌晨时间戳
     */
    private long checkTimestamp(long timestamp) {
        // 请确保时间戳，小于当日凌晨，以便可以缓存数据
        long morning = DateUtil.getTimesMorning();
        if(timestamp == 0 || timestamp > morning ) {
            timestamp = morning;
        }else { // 采用东八区（北京时间）计算
            long offset = timestamp % Constant.TIMESTAMP_DAYS_1;
            if(offset > 0) timestamp -= offset + Constant.TIMESTAMP_HOUR_8;
        }
        return timestamp;
    }



}
