package com.mujugroup.data.service;


import com.lveqia.cloud.common.exception.BaseException;
import com.mujugroup.data.objeck.bo.ProfitBo;
import com.mujugroup.data.objeck.bo.UsageBo;

public interface OverviewService {

    UsageBo usage(String uid, String aid, long timestamp) throws BaseException;

    ProfitBo profit(String uid, String aid, long timestamp) throws BaseException;
}
