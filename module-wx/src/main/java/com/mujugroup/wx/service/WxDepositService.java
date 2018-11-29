package com.mujugroup.wx.service;

import com.lveqia.cloud.common.exception.BaseException;
import com.mujugroup.wx.model.WxDeposit;
import com.mujugroup.wx.objeck.vo.deposit.InfoListVo;

import java.util.List;

/**
 * @author leolaurel
 */
public interface WxDepositService {
    boolean insert(WxDeposit wxDeposit);

    boolean update(WxDeposit wxDeposit);

    WxDeposit getFinishDeposit(String openId);

    WxDeposit getDepositInfo(String sessionThirdKey, String code) throws BaseException;

    boolean modifyStatus(String sessionThirdKey, String code, long id) throws BaseException;

    List<InfoListVo> getInfoList();
}
