package com.mujugroup.data.service;


import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.exception.ParamException;

import java.util.List;

public interface StaVOService {

    List<?> getStaVOList(String action, String ids, int aid, int hid, int oid, int grain, int start, int stop)
            throws ParamException, BaseException;
}
