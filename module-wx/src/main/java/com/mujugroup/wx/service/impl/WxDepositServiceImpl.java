package com.mujugroup.wx.service.impl;

import com.mujugroup.wx.mapper.WxDepositMapper;
import com.mujugroup.wx.model.WxDeposit;
import com.mujugroup.wx.service.WxDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author leolaurel
 */
@Service("wxDepositService")
public class WxDepositServiceImpl implements WxDepositService {


    private final WxDepositMapper wxDepositMapper;

    @Autowired
    public WxDepositServiceImpl(WxDepositMapper wxDepositMapper) {
        this.wxDepositMapper = wxDepositMapper;
    }

    @Override
    public boolean insert(WxDeposit wxDeposit) {
        return wxDepositMapper.insert(wxDeposit);
    }

    @Override
    public WxDeposit getFinishDeposit(String openId) {
        return wxDepositMapper.getFinishDeposit(openId);
    }

    @Override
    public boolean update(WxDeposit wxDeposit) {
        return wxDepositMapper.update(wxDeposit);
    }
}
