package com.mujugroup.data.service;


import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.exception.DataException;
import com.lveqia.cloud.common.exception.ParamException;
import com.mujugroup.data.objeck.bo.ProfitBO;
import com.mujugroup.data.objeck.vo.ExcelVO;
import com.mujugroup.data.objeck.vo.ProfitVO;

import java.util.List;

public interface StaVOService {


    ProfitVO getProfitVO(ProfitBO profitBO);

    List<?> getStaVOList(String action, String uid, int pid, int cid, String aid, String hid, String oid, int grain
            , int start, int stop)throws BaseException;

    List<ExcelVO> getExcelVO(String uid, String hid, int grain, int startTime, int stopTime) throws  BaseException;

    String[] checkIds(String uid, String aid, String hid, String oid) throws DataException;
}
