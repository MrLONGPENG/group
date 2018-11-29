package com.mujugroup.wx.service.impl;

import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.exception.ParamException;
import com.mujugroup.wx.mapper.WxDepositMapper;
import com.mujugroup.wx.model.WxDeposit;
import com.mujugroup.wx.objeck.vo.deposit.InfoListVo;
import com.mujugroup.wx.objeck.vo.deposit.PutVo;
import com.mujugroup.wx.service.UsingApiService;
import com.mujugroup.wx.service.WxDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author leolaurel
 */
@Service("wxDepositService")
public class WxDepositServiceImpl implements WxDepositService {


    private final WxDepositMapper wxDepositMapper;
    private final UsingApiService usingApiService;

    @Autowired
    public WxDepositServiceImpl(WxDepositMapper wxDepositMapper, UsingApiService usingApiService) {
        this.wxDepositMapper = wxDepositMapper;
        this.usingApiService = usingApiService;
    }

    @Override
    public boolean insert(WxDeposit wxDeposit) {
        return wxDepositMapper.insert(wxDeposit);
    }


    @Override
    public WxDeposit getDepositInfo(String sessionThirdKey, String code) throws BaseException {
        String openId = usingApiService.parseCode(sessionThirdKey, code)[0];
        WxDeposit wxDeposit= wxDepositMapper.getFinishDeposit(openId);
        return wxDeposit;
    }

    @Override
    public List<InfoListVo> getInfoList() {
        return wxDepositMapper.getInfoList();
    }

    @Override
    public boolean modifyStatus(String sessionThirdKey, String code, long id) throws BaseException {
        String openId = usingApiService.parseCode(sessionThirdKey, code)[0];
        WxDeposit wxDeposit = wxDepositMapper.findDepositById(openId, id);
        if (wxDeposit == null) throw new ParamException("该用户暂未缴纳押金");
        wxDeposit.setStatus(WxDeposit.REFUNDING_MONEY);//设置当前押金状态为退款中
        wxDeposit.setUpdTime(new Date());
        return wxDepositMapper.update(wxDeposit);
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
