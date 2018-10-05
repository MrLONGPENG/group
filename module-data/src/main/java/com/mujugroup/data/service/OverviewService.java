package com.mujugroup.data.service;


import com.lveqia.cloud.common.exception.BaseException;
import com.mujugroup.data.objeck.bo.ProfitBO;
import com.mujugroup.data.objeck.bo.UsageBO;

public interface OverviewService {

    UsageBO usage(String uid, String aid, long timestamp) throws BaseException;

    ProfitBO profit(String uid, String aid, long timestamp) throws BaseException;
}
