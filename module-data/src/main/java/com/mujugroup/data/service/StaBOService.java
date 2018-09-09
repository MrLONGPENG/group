package com.mujugroup.data.service;


import com.lveqia.cloud.common.exception.ParamException;
import com.mujugroup.data.objeck.bo.ExcelBO;
import com.mujugroup.data.objeck.bo.sta.StaActive;
import com.mujugroup.data.objeck.bo.sta.StaProfit;
import com.mujugroup.data.objeck.bo.sta.StaUsage;
import com.mujugroup.data.objeck.bo.sta.StaUsageRate;

import java.util.List;

public interface StaBOService {

    List<String> getRefDate(int startTime, int stopTime
            , int grain) throws ParamException;

    List<StaUsage>  getUsage(String ids, int aid, int hid, int oid, int grain
            , int startTime, int stopTime) throws ParamException;

    List<StaProfit> getProfit(String ids, int aid, int hid, int oid, int grain
            , int startTime, int stopTime) throws ParamException;

    List<StaActive> getActive(String ids, int aid, int hid, int oid, int grain
            , int startTime, int stopTime) throws ParamException;

    List<StaUsageRate> getUsageRate(String ids, int aid, int hid, int oid, int grain
            , int startTime, int stopTime) throws ParamException;

    List<ExcelBO> getExcelBO(String name, int aid, int hid, int grain, int startTime
            , int stopTime) throws ParamException;



}
