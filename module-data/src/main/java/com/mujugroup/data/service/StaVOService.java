package com.mujugroup.data.service;


import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.exception.DataException;
import com.mujugroup.data.objeck.bo.ProfitBo;
import com.mujugroup.data.objeck.vo.ExcelVo;
import com.mujugroup.data.objeck.vo.ProfitVo;

import java.util.List;

public interface StaVOService {


    ProfitVo getProfitVO(ProfitBo profitBO);

    List<?> getStaVOList(String action, String uid, int pid, int cid, String aid, String hid, String oid, int grain
            , int start, int stop)throws BaseException;

    List<ExcelVo> getExcelVO(String uid, String hid, int grain, int startTime, int stopTime) throws  BaseException;

    String[] checkIds(String uid, String aid, String hid, String oid) throws DataException;
}
