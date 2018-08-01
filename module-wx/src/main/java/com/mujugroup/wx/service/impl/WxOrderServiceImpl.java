package com.mujugroup.wx.service.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lveqia.cloud.common.DateUtil;
import com.lveqia.cloud.common.util.DBMap;
import com.lveqia.cloud.common.StringUtil;
import com.mujugroup.wx.bean.OrderBean;
import com.mujugroup.wx.mapper.WxOrderMapper;
import com.mujugroup.wx.model.WxGoods;
import com.mujugroup.wx.model.WxOrder;
import com.mujugroup.wx.service.SessionService;
import com.mujugroup.wx.service.WxOrderService;
import com.mujugroup.wx.service.feign.ModuleCoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("wxOrderService")
public class WxOrderServiceImpl implements WxOrderService {


    private final WxOrderMapper wxOrderMapper;
    private final SessionService sessionService;
    private final ModuleCoreService moduleCoreService;
    @Autowired
    public WxOrderServiceImpl(WxOrderMapper wxOrderMapper, SessionService sessionService
            , ModuleCoreService moduleCoreService) {
        this.wxOrderMapper = wxOrderMapper;
        this.sessionService = sessionService;
        this.moduleCoreService = moduleCoreService;
    }

    @Override
    public WxOrder addOrder(String did, String openId, String aid, String hid, String oid, String orderNo, WxGoods wxGoods) {
        WxOrder wxOrder = new WxOrder();
        wxOrder.setDid(Long.parseLong(did));
        wxOrder.setOpenId(openId);
        wxOrder.setTradeNo(orderNo);
        wxOrder.setAid(Integer.parseInt(aid));
        wxOrder.setHid(Integer.parseInt(hid));
        wxOrder.setOid(Integer.parseInt(oid));
        wxOrder.setGid(wxGoods.getId());
        wxOrder.setPayPrice(wxGoods.getPrice());
        wxOrder.setPayStatus(WxOrder.TYPE_PAY_WAITING);
        return wxOrderMapper.insert(wxOrder)? wxOrder:null;
    }

    @Override
    public WxOrder findLastOrderByDid(String did) {
        return wxOrderMapper.findLastOrderByDid(did);
    }

    @Override
    public WxOrder findOrderByNo(String orderNo) {
        return wxOrderMapper.findByNo(orderNo);
    }

    @Override
    public void update(WxOrder wxOrder) {
        wxOrderMapper.update(wxOrder);
    }

    @Override
    public List<WxOrder> listSelfOrder(String sessionThirdKey) {
        String openId = sessionService.getOpenId(sessionThirdKey);
        return  wxOrderMapper.findListBySelf(openId, WxOrder.TYPE_PAY_SUCCESS);
    }

    @Override
    public OrderBean details(String sessionThirdKey, String tradeNo) {
        WxOrder wxOrder = findOrderByNo(tradeNo);
        if(wxOrder!=null){
            OrderBean orderBean = new OrderBean(wxOrder);
            String did = StringUtil.autoFillDid(wxOrder.getDid());
            String result = moduleCoreService.deviceQuery(did);
            if(result!=null){
                JsonObject returnData = new JsonParser().parse(result).getAsJsonObject();
                if(returnData.get("code").getAsInt() == 200 && returnData.has("data")) {
                    JsonObject data = returnData.getAsJsonObject("data");
                    orderBean.setHospitalBed(data.get("hospitalBed").getAsString());
                    orderBean.setHospital(data.getAsJsonObject("hospital").get("name").getAsString());
                    orderBean.setAddress(data.getAsJsonObject("hospital").get("address").getAsString());
                    orderBean.setDepartment(data.getAsJsonObject("department").get("name").getAsString());
                }
            }else{
                orderBean.setAddress("服务器异常，无法取到实时数据");
            }
            return orderBean;
        }
        return null;
    }

    @Override
    public List<WxOrder> findListAll() {
        return wxOrderMapper.findListAll();
    }


    @Override
    public List<DBMap> getPayCountByAid(String aid) {
        return wxOrderMapper.getPayCountByAid(aid, DateUtil.getLastDay());
    }

    @Override
    public List<DBMap> getPayCountByHid(String aid, String hid) {
        return wxOrderMapper.getPayCountByHid(aid, hid, DateUtil.getLastDay());
    }


}
