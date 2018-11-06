package com.mujugroup.data.service.impl;

import com.github.wxiaoqi.merge.annonation.MergeResult;
import com.lveqia.cloud.common.config.CoreConfig;
import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.exception.DataException;
import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.util.DateUtil;
import com.lveqia.cloud.common.config.Constant;
import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.data.objeck.bo.ProfitBo;
import com.mujugroup.data.objeck.bo.UsageBo;
import com.mujugroup.data.service.OverviewService;
import com.mujugroup.data.service.feign.ModuleCoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("overviewService")
public class OverviewServiceImpl implements OverviewService {

    private final ModuleCoreService moduleCoreService;

    @Autowired
    public OverviewServiceImpl(ModuleCoreService moduleCoreService) {
        this.moduleCoreService = moduleCoreService;
    }

    @Override
    @MergeResult
    public UsageBo usage(String uid, String aid, long timestamp) throws BaseException {
        long delay = checkTimestampAndAddDelay(timestamp);
        return new UsageBo(checkUserData(uid, aid), DateUtil.timestampToDays(delay - Constant.TIMESTAMP_DAYS_1), delay);
    }



    @Override
    @MergeResult
    public ProfitBo profit(String uid, String aid, long timestamp) throws BaseException {
        return new ProfitBo(checkUserData(uid, aid), checkTimestampAndAddDelay(timestamp));
    }


    /**
     * 通过用户ID查询代理商实际数据
     */
    private String checkUserData(String uid, String aid) throws ParamException, DataException {
        if(!StringUtil.isNumeric(aid)) throw new ParamException("代理商ID必须为数字!");
        if(Constant.DIGIT_ZERO.equals(aid)){
            Map<String, String> map = moduleCoreService.getAuthData(uid);
            if(map.size() ==0 ) throw new DataException("当前用户无数据权限，请联系管理员！");
            if(map.containsKey(CoreConfig.AUTH_DATA_HOSPITAL) || map.containsKey(CoreConfig.AUTH_DATA_DEPARTMENT)){
                throw new DataException("数据权限不全为代理商，无法查询");
            }
            if(map.containsKey(CoreConfig.AUTH_DATA_AGENT)) aid = map.get(CoreConfig.AUTH_DATA_AGENT);
        }
        return aid;
    }


    /**
     * 检查时间戳，去除凌晨之后的时间，默认今日凌晨时间戳且加上延时时间
     */
    private long checkTimestampAndAddDelay(long timestamp) {
        // 请确保时间戳，小于当日凌晨，以便可以缓存数据
        long morning = DateUtil.getTimesMorning();
        if(timestamp == 0 || timestamp > morning ) {
            timestamp = morning;
        }else { // 采用东八区（北京时间）计算
            long offset = timestamp % Constant.TIMESTAMP_DAYS_1;
            if(offset > 0) timestamp -= offset + Constant.TIMESTAMP_HOUR_8;
        }
        return timestamp + Constant.TIMESTAMP_DELAY;
    }



}
