package com.mujugroup.wx.service.impl;

import com.mujugroup.wx.mapper.WxRepairMapper;
import com.mujugroup.wx.model.WxRepair;
import com.mujugroup.wx.service.SessionService;
import com.mujugroup.wx.service.WxImagesService;
import com.mujugroup.wx.service.WxRepairService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service("wxRepairService")
public class WxRepairServiceImpl implements WxRepairService {

    private final Logger logger = LoggerFactory.getLogger(WxRepairServiceImpl.class);
    private final WxRepairMapper wxRepairMapper;
    private final SessionService sessionService;
    private final WxImagesService wxImagesService;

    @Autowired
    public WxRepairServiceImpl(WxRepairMapper wxRepairMapper, SessionService sessionService
            , WxImagesService wxImagesService) {
        this.wxRepairMapper = wxRepairMapper;
        this.sessionService = sessionService;
        this.wxImagesService = wxImagesService;
    }


    @Override
    public boolean report(String sessionThirdKey, String did, String cause, String describe, String images) {
        try{
            String openId= sessionService.getOpenId(sessionThirdKey);
            WxRepair wxRepair = new WxRepair();
            wxRepair.setOpenId(openId);
            wxRepair.setDid(Long.parseLong(did));
            wxRepair.setFaultCause(cause);
            wxRepair.setFaultDescribe(describe);
            wxRepair.setCrtTime(new Date());
            wxRepair.setRepairStatus(WxRepair.TYPE_REPAIR_WAIT);
            boolean isOk = wxRepairMapper.insert(wxRepair);
            if(images!=null && isOk) {
                String[] array = images.split(";");
                for (String image :array){
                    wxImagesService.insertRepairImage(wxRepair.getId(), image);
                }
            }
            return isOk;
        }catch (Exception e){
            logger.warn("wx-report-error ",e);
        }
        return false;
    }
}
