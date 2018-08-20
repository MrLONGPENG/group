package com.mujugroup.data.service;


import com.mujugroup.data.bean.ActiveBean;

import java.util.List;

public interface StatisticsService {

    List<ActiveBean> activeStatistics(int aid, int hid, int oid, int grain, int startTime, int stopTime);
}
