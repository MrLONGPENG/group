package com.mujugroup.data.service;

import com.github.pagehelper.PageInfo;
import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.objeck.to.PageTo;
import com.lveqia.cloud.common.objeck.to.SelectTo;
import com.mujugroup.core.objeck.vo.SelectVo;
import com.mujugroup.data.objeck.bo.ListBo;
import com.mujugroup.data.objeck.vo.DeviceVo;

import java.util.List;

public interface DeviceService {
    DeviceVo getDeviceDetailByDid(String did) throws ParamException;

    List<ListBo> getUsageRate(int hid,  List<SelectVo> list) throws BaseException;

    PageInfo<SelectVo> getSelectVo(int uid, int hid, int pageNum, int pageSize);
}
