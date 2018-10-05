package com.mujugroup.data.service;


import com.google.gson.JsonObject;
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

    List<StaUsage>  getUsage(String[] ids, int grain, int startTime, int stopTime) throws ParamException;

    List<StaProfit> getProfit(String[] ids, int grain, int startTime, int stopTime) throws ParamException;

    List<StaActive> getActive(String[] ids, int grain, int startTime, int stopTime) throws ParamException;

    List<StaUsageRate> getUsageRate(String[] ids, int grain, int startTime, int stopTime) throws ParamException;

    List<ExcelBO> getExcelBO(JsonObject info, int grain, int startTime, int stopTime) throws ParamException;



}
