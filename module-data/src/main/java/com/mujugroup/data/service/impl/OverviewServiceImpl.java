package com.mujugroup.data.service.impl;

import com.github.wxiaoqi.merge.annonation.MergeResult;
import com.lveqia.cloud.common.StringUtil;
import com.lveqia.cloud.common.util.Constant;
import com.mujugroup.data.bean.OverviewInfo;
import com.mujugroup.data.service.OverviewService;
import org.springframework.stereotype.Service;


@Service("overviewService")
public class OverviewServiceImpl implements OverviewService {

    @Override
    @MergeResult
    public OverviewInfo info(int aid, long timestamp) {
        // 请确保时间戳，小于当日凌晨，以便可以缓存数据
        return new OverviewInfo(Constant.DIGIT_ZERO, StringUtil.toParams(aid, timestamp), Constant.DIGIT_ZERO);
    }
}
