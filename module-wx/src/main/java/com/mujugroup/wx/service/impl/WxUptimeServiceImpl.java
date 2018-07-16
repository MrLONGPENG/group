package com.mujugroup.wx.service.impl;

import com.lveqia.cloud.common.DateUtil;
import com.mujugroup.wx.mapper.WxUptimeMapper;
import com.mujugroup.wx.model.WxRelation;
import com.mujugroup.wx.model.WxUptime;
import com.mujugroup.wx.service.WxUptimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author leolaurel
 */
@Service("wxUptimeService")
public class WxUptimeServiceImpl implements WxUptimeService {

    private final Logger logger = LoggerFactory.getLogger(WxUptimeServiceImpl.class);
    private final WxUptimeMapper wxUptimeMapper;

    @Autowired
    public WxUptimeServiceImpl(WxUptimeMapper wxUptimeMapper) {
        this.wxUptimeMapper = wxUptimeMapper;
    }


    @Override
    public WxUptime findListByHospital(Integer hid) {
        List<WxUptime> list = wxUptimeMapper.findListByRelation(WxRelation.KEY_HOSPITAL, hid);
        if (list == null || list.size() ==0) {
            return  getDefaultWxUptime();
        }
        return list.get(0);
    }

    @Override
    public Long getEndTimeByHid(Integer hid) {
        return DateUtil.getTimesNight() + findListByHospital(hid).getStopTime();
    }

    /**
     * 获取默认数据
     */
    @Override
    public WxUptime getDefaultWxUptime() {
        logger.info("未找到医院指定开关锁时间，采用默认开关锁时间");
        List<WxUptime> list = wxUptimeMapper.findListByRelation(WxRelation.KEY_DEFAULT, WxRelation.KID_DEFAULT);
        if(list== null || list.size() ==0) {
            WxUptime wxUptime = new WxUptime();
            wxUptime.setId(0);
            wxUptime.setStartDesc("18:00");
            wxUptime.setStartTime(18*60*60);
            wxUptime.setStopDesc("6:00");
            wxUptime.setStopTime(6*60*60);
            wxUptime.setExplain("默认数据(代码生成)");
            return wxUptime;
        }
        return list.get(0);
    }
}
