package com.mujugroup.wx.service;

import com.lveqia.cloud.common.exception.BaseException;
import com.mujugroup.wx.model.WxDeposit;
import com.mujugroup.wx.objeck.vo.deposit.InfoListVo;
import com.mujugroup.wx.objeck.vo.deposit.PutVo;
import java.util.List;
import java.util.Map;

/**
 * @author leolaurel
 */
public interface WxDepositService {
    boolean insert(WxDeposit wxDeposit);

    boolean update(WxDeposit wxDeposit);

    WxDeposit getFinishDeposit(String openId);

    WxDeposit getDepositInfo(String sessionThirdKey);

    boolean modifyStatus(String sessionThirdKey, long id) throws BaseException;

    List<InfoListVo> getInfoList();

    Map<String, String> modifyRecordStatus(PutVo infoVo) throws BaseException;
}
