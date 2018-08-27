package com.mujugroup.data.service.impl;

import com.github.wxiaoqi.merge.annonation.MergeResult;
import com.lveqia.cloud.common.DateUtil;
import com.lveqia.cloud.common.util.Constant;
import com.mujugroup.data.objeck.vo.OverviewInfo;
import com.mujugroup.data.service.OverviewService;
import org.springframework.stereotype.Service;


@Service("overviewService")
public class OverviewServiceImpl implements OverviewService {

    @Override
    @MergeResult
    public OverviewInfo info(int aid, long timestamp) {
        // 请确保时间戳，小于当日凌晨，以便可以缓存数据
        long morning = DateUtil.getTimesMorning();
        if(timestamp == 0 || timestamp > morning ) {
            timestamp = morning;
        }else { // 采用东八区（北京时间）计算
            long offset = timestamp % Constant.TIMESTAMP_DAYS_1;
            if(offset > 0) timestamp -= offset + Constant.TIMESTAMP_HOUR_8;
        }
        return new OverviewInfo(aid, timestamp);
    }
}
