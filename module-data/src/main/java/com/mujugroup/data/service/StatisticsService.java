package com.mujugroup.data.service;


import com.lveqia.cloud.common.exception.ParamException;
import com.mujugroup.data.objeck.bo.ExcelBO;
import com.mujugroup.data.objeck.vo.StaProfit;
import com.mujugroup.data.objeck.vo.StaUsage;
import com.mujugroup.data.objeck.vo.StaActive;
import com.mujugroup.data.objeck.vo.StaUsageRate;
import com.mujugroup.data.utils.ExcelData;

import java.util.List;

public interface StatisticsService {

    List<String> getRefDate(int startTime, int stopTime
            , int grain) throws ParamException;

    List<StaUsage>  getUsage(int aid, int hid, int oid, int grain
            , int startTime, int stopTime) throws ParamException;

    List<StaProfit> getProfit(int aid, int hid, int oid, int grain
            , int startTime, int stopTime) throws ParamException;

    List<StaActive> getActive(int aid, int hid, int oid, int grain
            , int startTime, int stopTime) throws ParamException;

    List<StaUsageRate> getUsageRate(int aid, int hid, int oid, int grain
            , int startTime, int stopTime) throws ParamException;

    List<ExcelBO> getExcelBO(String name, int aid, int hid, int grain, int startTime
            , int stopTime) throws ParamException;


}
