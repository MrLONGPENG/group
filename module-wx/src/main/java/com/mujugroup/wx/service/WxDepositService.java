package com.mujugroup.wx.service;

import com.lveqia.cloud.common.exception.BaseException;
import com.mujugroup.wx.model.WxDeposit;
import com.mujugroup.wx.objeck.vo.deposit.InfoListVo;
import com.mujugroup.wx.objeck.vo.deposit.PutVo;
import java.util.List;

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

    WxDeposit getRefundingWxDepositById(Long id);

    boolean modifyRecordStatus(PutVo infoVo) throws BaseException;
}
