package com.mujugroup.data.service;


import com.google.gson.JsonObject;
import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.exception.ParamException;
import com.mujugroup.data.objeck.bo.ProfitBO;
import com.mujugroup.data.objeck.vo.ExcelVO;
import com.mujugroup.data.objeck.vo.ProfitVO;

import java.util.List;

public interface StaVOService {

    List<?> getStaVOList(String action, String ids, int aid, int hid, int oid, int grain
            , int start, int stop)throws BaseException;

    ProfitVO getProfitVO(ProfitBO profitBO);

    List<ExcelVO> getExcelVO(JsonObject info, int grain, int startTime, int stopTime) throws ParamException;
}
