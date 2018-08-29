package com.mujugroup.data.service;


import com.mujugroup.data.objeck.bo.ProfitBO;
import com.mujugroup.data.objeck.bo.UsageBO;

public interface OverviewService {

    UsageBO usage(int aid, long timestamp);

    ProfitBO profit(int aid, long timestamp);
}
