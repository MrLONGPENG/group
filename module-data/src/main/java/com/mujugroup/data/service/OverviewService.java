package com.mujugroup.data.service;


import com.mujugroup.data.objeck.vo.OverviewInfo;

public interface OverviewService {
    OverviewInfo info(int aid, long timestamp);
}
