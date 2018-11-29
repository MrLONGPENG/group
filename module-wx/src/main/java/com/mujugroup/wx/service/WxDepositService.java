package com.mujugroup.wx.service;

import com.mujugroup.wx.model.WxDeposit;

import java.util.List;

/**
 * @author leolaurel
 */
public interface WxDepositService {
    boolean insert(WxDeposit wxDeposit);

    boolean update(WxDeposit wxDeposit);

    WxDeposit getFinishDeposit(String openId);
}
