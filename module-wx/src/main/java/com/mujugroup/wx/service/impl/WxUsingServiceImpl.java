package com.mujugroup.wx.service.impl;

import com.mujugroup.wx.mapper.WxUsingMapper;
import com.mujugroup.wx.model.WxUsing;
import com.mujugroup.wx.service.WxUsingService;
import com.mujugroup.wx.service.feign.ModuleLockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service("wxUsingService")
public class WxUsingServiceImpl implements WxUsingService {

    private final WxUsingMapper wxUsingMapper;
    private final ModuleLockService moduleLockService;
    private final Logger logger = LoggerFactory.getLogger(WxUsingServiceImpl.class);

    @Autowired
    public WxUsingServiceImpl(WxUsingMapper wxUsingMapper, ModuleLockService moduleLockService) {
        this.wxUsingMapper = wxUsingMapper;
        this.moduleLockService = moduleLockService;
    }

    @Override
    public WxUsing insert(WxUsing wxUsing) {
        return wxUsingMapper.insert(wxUsing) ? wxUsing : null;
    }

    @Override
    public WxUsing update(WxUsing wxUsing) {
        return wxUsingMapper.update(wxUsing) ? wxUsing : null;
    }


    /**
     * 当WxUsing中状态与指定不同时候，更新状态
     */
    @Override
    public  WxUsing updateUsingStatus(WxUsing wxUsing, String status) {
        logger.info("设备{}同步锁状态中, status:{}, using:{}", wxUsing.getDid(), status, wxUsing.getUsing());
        if(!wxUsing.getUsing() && "2".equals(status)){
            logger.info("updateUsingStatus 开锁");
            wxUsing.setUsing(true);
            wxUsing.setUnlockTime(new Date());
            wxUsingMapper.update(wxUsing);
        }else if(wxUsing.getUsing() && "1".equals(status)){
            logger.info("updateUsingStatus 关锁");
            wxUsing.setUsing(false);
            wxUsingMapper.update(wxUsing);
        }else{
            logger.info("updateUsingStatus 无状态变化");
        }
        return wxUsing;
    }
    /**
     * 通过OpenID查询使用情况信息
     * @param time 指定时间需要小于订单结束时间
     */
    @Override
    public WxUsing findUsingByOpenId(String openId, long time) {
        List<WxUsing> list = wxUsingMapper.findUsingByOpenId(openId, time);
        if(list == null || list.size()==0 ) return null;
        if(list.size()>1) logger.error("错误:通过OpenId找到的使用情况数量大于1");
        return list.get(0);
    }
    /**
     * 通过DID查询使用情况信息
     * @param time 指定时间需要小于订单结束时间
     * @param isSync 是否远程调用同步锁状态
     */
    @Override
    public WxUsing findUsingByDid(String did, long time, boolean isSync) {
        List<WxUsing> list = wxUsingMapper.findUsingByDid(did, time);
        if(list == null || list.size()==0 ) return null;
        if(list.size()>1) logger.error("错误:通过Did找到的使用情况数量大于1");
        WxUsing wxUsing = list.get(0);
        if(wxUsing!=null && isSync) updateUsingStatus(wxUsing, moduleLockService.getStatus(did));
        return wxUsing;
    }



    /**
     * 通过BID查询使用情况信息
     * @param time 指定时间需要小于订单结束时间
     */
    @Override
    public WxUsing findUsingByBid(String bid, long time) {
        return findUsingByDid(moduleLockService.bidToDid(bid), time, false);
    }


    @Override
    public boolean deleteByDid(String did, long time) {
        return wxUsingMapper.deleteByDid(did, time);
    }

    @Override
    public int getCountByUsingDid(String did, long time) {
        return wxUsingMapper.getCountByUsingDid(did,time);
    }


}
