package com.mujugroup.data.service;


import com.mujugroup.data.bean.OverviewInfo;

public interface OverviewService {

    OverviewInfo info(int aid, long timestamp);
}
