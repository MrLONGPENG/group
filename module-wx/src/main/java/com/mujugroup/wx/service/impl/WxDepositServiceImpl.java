package com.mujugroup.wx.service.impl;

import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.exception.ParamException;
import com.mujugroup.wx.mapper.WxDepositMapper;
import com.mujugroup.wx.model.WxDeposit;
import com.mujugroup.wx.model.WxOrder;
import com.mujugroup.wx.model.WxRecordMain;
import com.mujugroup.wx.objeck.vo.deposit.InfoListVo;
import com.mujugroup.wx.objeck.vo.deposit.PutVo;
import com.mujugroup.wx.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author leolaurel
 */
@Service("wxDepositService")
public class WxDepositServiceImpl implements WxDepositService {


    private final WxDepositMapper wxDepositMapper;
    private final WxRecordMainService wxRecordMainService;
    private final SessionService sessionService;
    private final WxOrderService wxOrderService;

    @Autowired
    public WxDepositServiceImpl(WxDepositMapper wxDepositMapper, WxRecordMainService wxRecordMainService, SessionService sessionService, WxOrderService wxOrderService) {
        this.wxDepositMapper = wxDepositMapper;
        this.wxRecordMainService = wxRecordMainService;
        this.sessionService = sessionService;
        this.wxOrderService = wxOrderService;
    }

    @Override
    public boolean insert(WxDeposit wxDeposit) {
        return wxDepositMapper.insert(wxDeposit);
    }


    @Override
    public WxDeposit getDepositInfo(String sessionThirdKey) {
        String openId = sessionService.getOpenId(sessionThirdKey);
        return wxDepositMapper.getFinishDeposit(openId);
    }

    @Transactional
    @Override
    /**
     * 修改押金表,支付主表中的相关状态
     */
    //TODO 此处暂时进行全部一次性退款,后期再进行版本迭代以支持多次退款
    public boolean modifyRecordStatus(PutVo infoVo) throws BaseException {
        WxDeposit wxDeposit = wxDepositMapper.getRefundingWxDepositById(infoVo.getId());
        WxOrder order = wxOrderService.getOrderByOpenidAndTradeNo(wxDeposit.getOpenId(), WxOrder.TYPE_PAY_SUCCESS, wxDeposit.getTradeNo());
        if (order == null) throw new ParamException("无此订单记录!");
        if (order.getEndTime() > System.currentTimeMillis() / 1000) throw new ParamException("当前订单仍在有效期内，暂无法退款");
        if (wxDeposit == null) throw new ParamException("该记录不存在,请重新选择!");
        WxRecordMain wxRecordMain = wxRecordMainService.getFinishPayRecordByNo(wxDeposit.getTradeNo(), wxDeposit.getOpenId());
        if (wxRecordMain == null) throw new ParamException("该记录不存在!");
        if (wxRecordMain.getRefundCount() > 10) throw new ParamException("当前退款次数已超过10次,无法进行退款操作");
        if (wxDeposit.getDeposit() == 0) throw new ParamException("您的押金为0元,请先缴纳押金!");
        wxRecordMain.setRefundPrice(wxDeposit.getDeposit());//设置支付记录主表的退款金额
        wxRecordMain.setTotalPrice(wxRecordMain.getTotalPrice() - wxRecordMain.getRefundPrice());//设置总金额
        wxRecordMain.setRefundCount(wxRecordMain.getRefundCount() + 1);//设置当前退款次数
        wxDeposit.setStatus(WxDeposit.PASS_AUDIT);//设置当前押金表的状态为审核通过
        wxDeposit.setUpdTime(new Date());
        wxDeposit.setDeposit(WxDeposit.NO_MONEY);//设置当前押金金额为0
        boolean isTrue = wxRecordMainService.update(wxRecordMain);
        isTrue &= wxDepositMapper.update(wxDeposit);
        return isTrue;
    }

    @Override
    public WxDeposit getRefundingWxDepositById(Long id) {
        return wxDepositMapper.getRefundingWxDepositById(id);
    }

    @Override
    public List<InfoListVo> getInfoList() {
        return wxDepositMapper.getInfoList();
    }

    @Override
    public boolean modifyStatus(String sessionThirdKey, long id) throws BaseException {
        String openId = sessionService.getOpenId(sessionThirdKey);
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
