package com.mujugroup.wx.service;

import com.mujugroup.wx.model.WxDeposit;

/**
 * @author leolaurel
 */
public interface WxDepositService {
    boolean insert(WxDeposit wxDeposit);

    boolean update(WxDeposit wxDeposit);
}
