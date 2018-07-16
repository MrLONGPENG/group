package com.mujugroup.wx.service.impl;

import com.mujugroup.wx.mapper.WxUsingMapper;
import com.mujugroup.wx.model.WxUsing;
import com.mujugroup.wx.service.WxUsingService;
import com.mujugroup.wx.service.feign.ModuleLockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public WxUsing findUsingByOpenId(String openId, long time) {
        List<WxUsing> list = wxUsingMapper.findUsingByOpenId(openId, time);
        if(list == null || list.size()==0 ) return null;
        if(list.size()>1) logger.error("错误:通过OpenId找到的使用情况数量大于1");
        return list.get(0);
    }

    @Override
    public WxUsing findUsingByDid(String did, long time, boolean isSync) {
        List<WxUsing> list = wxUsingMapper.findUsingByDid(did, time);
        if(list == null || list.size()==0 ) return null;
        if(list.size()>1) logger.error("错误:通过Did找到的使用情况数量大于1");
        WxUsing wxUsing = list.get(0);
        if(wxUsing!=null && isSync){
            String status = moduleLockService.getStatus(did);
            logger.info(String.format("设备%1$s同步锁状态中, status:%2$s, using:%3$b", did, status, wxUsing.getUsing()));
            if(!wxUsing.getUsing() && "2".equals(status)){
                wxUsing.setUsing(true);
                wxUsingMapper.update(wxUsing);
            }else if(wxUsing.getUsing() && "1".equals(status)){
                wxUsing.setUsing(false);
                wxUsingMapper.update(wxUsing);
            }
        }
        return wxUsing;
    }

    @Override
    public WxUsing findUsingByBid(String bid, long time) {
        List<WxUsing> list =  wxUsingMapper.findUsingByDid(moduleLockService.bidToDid(bid), time);
        if(list == null || list.size()==0 ) return null;
        if(list.size()>1) logger.error("错误:通过Bid找到的使用情况数量大于1");
        return list.get(0);
    }

    @Override
    public boolean deleteByDid(String did, long time) {
        return wxUsingMapper.deleteByDid(did, time);
    }


}
