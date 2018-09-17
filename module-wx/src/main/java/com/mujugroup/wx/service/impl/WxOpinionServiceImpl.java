package com.mujugroup.wx.service.impl;

import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.wx.mapper.WxOpinionMapper;
import com.mujugroup.wx.model.WxOpinion;
import com.mujugroup.wx.service.SessionService;
import com.mujugroup.wx.service.WxOpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author leolaurel
 */
@Service("wxOpinionService")
public class WxOpinionServiceImpl implements WxOpinionService {


    private final WxOpinionMapper wxOpinionMapper;
    private final SessionService sessionService;

    @Autowired
    public WxOpinionServiceImpl(WxOpinionMapper wxOpinionMapper, SessionService sessionService) {
        this.wxOpinionMapper = wxOpinionMapper;
        this.sessionService = sessionService;
    }

    @Override
    public void feedback(String sessionThirdKey, String content, String did) {
        WxOpinion wxOpinion = new WxOpinion();
        wxOpinion.setContent(content);
        wxOpinion.setReadStatus(WxOpinion.TYPE_WAITING);
        wxOpinion.setOpenId(sessionService.getOpenId(sessionThirdKey));
        if(StringUtil.isNumeric(did)) wxOpinion.setDid(Long.parseLong(did));
        wxOpinionMapper.insert(wxOpinion);
    }
}
