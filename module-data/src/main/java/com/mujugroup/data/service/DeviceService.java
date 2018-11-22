package com.mujugroup.data.service;

import com.github.pagehelper.PageInfo;
import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.objeck.to.InfoTo;
import com.lveqia.cloud.common.objeck.to.SelectTo;
import com.mujugroup.data.objeck.bo.ListBo;
import com.mujugroup.data.objeck.bo.device.InfoBo;
import com.mujugroup.data.objeck.vo.DeviceVo;
import com.mujugroup.data.objeck.vo.device.InfoVo;

import java.util.List;

public interface DeviceService {
    DeviceVo getDeviceDetailByDid(String did) throws ParamException;

    List<ListBo> getUsageRate(int hid, List<SelectTo> list) throws BaseException;

    PageInfo<SelectTo> getSelectVo(int uid, int hid, int pageNum, int pageSize);

    List<InfoBo> getInfoById(PageInfo<InfoTo> list) throws ParamException;

    PageInfo<InfoTo> infoVoList(String id, int pageNum, int pageSize);

    List<InfoVo> boToVo(List<InfoBo> infoBoList);

}
