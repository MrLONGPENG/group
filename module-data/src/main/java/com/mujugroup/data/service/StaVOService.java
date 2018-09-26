package com.mujugroup.data.service;


import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.exception.ParamException;
import com.mujugroup.data.objeck.bo.ProfitBO;
import com.mujugroup.data.objeck.vo.ProfitVO;

import java.util.List;

public interface StaVOService {

    List<?> getStaVOList(String action, String ids, int aid, int hid, int oid, int grain, int start, int stop)
            throws ParamException, BaseException;

    ProfitVO getProfitVO(ProfitBO profitBO);
}