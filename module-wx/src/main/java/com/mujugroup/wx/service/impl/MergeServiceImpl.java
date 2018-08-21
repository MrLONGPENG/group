package com.mujugroup.wx.service.impl;

import com.lveqia.cloud.common.StringUtil;
import com.lveqia.cloud.common.util.Constant;
import com.lveqia.cloud.common.util.DBMap;
import com.mujugroup.wx.mapper.WxOrderMapper;
import com.mujugroup.wx.mapper.WxUserMapper;
import com.mujugroup.wx.model.WxOrder;
import com.mujugroup.wx.service.MergeService;
import com.mujugroup.wx.service.WxOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("mergeService")
public class MergeServiceImpl implements MergeService {

    private final WxUserMapper wxUserMapper;
    private final WxOrderMapper wxOrderMapper;
    private final WxOrderService wxOrderService;
    private final Logger logger = LoggerFactory.getLogger(MergeServiceImpl.class);

    public MergeServiceImpl(WxUserMapper wxUserMapper, WxOrderMapper wxOrderMapper, WxOrderService wxOrderService) {
        this.wxUserMapper = wxUserMapper;
        this.wxOrderMapper = wxOrderMapper;
        this.wxOrderService = wxOrderService;
    }



    /**
     * 查询最近24小时的支付数据
     */
    @RequestMapping(value = "/getPayCount", method = RequestMethod.POST)
    public Map<String, String> getPayCount(String param) {
        logger.warn("order-getPayCount[{}]", param);
        String[] ids = param.split(Constant.SIGN_COMMA);
        HashMap<String, String> hashMap =  new HashMap<>();
        if(ids.length==1){
            List<DBMap> list = wxOrderService.getPayCountByAid(ids[0]);
            list.forEach(dbMap -> dbMap.addTo(hashMap));
        }else if(ids.length == 2){
            List<DBMap> list = wxOrderService.getPayCountByHid(ids[0], ids[1]);
            list.forEach(dbMap -> dbMap.addTo(hashMap));
        }
        logger.debug(hashMap.toString());
        return hashMap;
    }

    @RequestMapping(value = "/getPaymentInfo", method = RequestMethod.POST)
    public Map<String, String> getPaymentInfo(String param) {
        HashMap<String, String> hashMap =  new HashMap<>();
        String[] array = param.split(Constant.SIGN_SEMICOLON);
        WxOrder wxOrder;
        StringBuilder sb;
        for (String did: array) {
            wxOrder = wxOrderService.findLastOrderByDid(did);
            if(wxOrder!=null){
                // DID;订单号;支付金额(分);支付时间;到期时间(秒)
                sb = new StringBuilder(StringUtil.autoFillDid(wxOrder.getDid()));
                sb.append(Constant.SIGN_SEMICOLON).append(wxOrder.getTradeNo());
                sb.append(Constant.SIGN_SEMICOLON).append(wxOrder.getPayPrice());
                sb.append(Constant.SIGN_SEMICOLON).append(wxOrder.getPayTime());
                sb.append(Constant.SIGN_SEMICOLON).append(wxOrder.getEndTime());
                hashMap.put(did, new String(sb));
            }
        }
        return hashMap;
    }

    /**
     * 根据条件获取全部的用户
     * @param param 代理商ID,时间戳(截至时间)
     */
    @Override
    public Map<String, String> getTotalUserCount(String param) {
        logger.debug("getTotalUserCount->{}", param);
        String[] params = param.split(Constant.SIGN_COMMA);
        Map<String,String> map = new HashMap<>();
        map.put(Constant.DIGIT_ZERO, wxUserMapper.getTotalUserCount(Constant.DIGIT_ZERO, params[1]));
        return map;
    }

    /**
     * 根据条件获取昨天的使用情况
     * @param param 代理商ID,时间戳(截至时间)
     */
    @Override
    public Map<String, String> getYesterdayUsageCount(String param) {
        logger.debug("getYesterdayUsageCount->{}", param);
        String[] params = param.split(Constant.SIGN_COMMA);
        Map<String,String> map = new HashMap<>();
        long start = Long.parseLong(params[1]) - Constant.TIMESTAMP_DAYS_1;
        map.put(Constant.DIGIT_ZERO, wxOrderMapper.getYesterdayUsageCount(params[0], String.valueOf(start), params[1]));
        return map;
    }


}
