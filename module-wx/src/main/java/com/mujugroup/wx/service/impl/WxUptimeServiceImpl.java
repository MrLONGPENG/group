package com.mujugroup.wx.service.impl;

import com.lveqia.cloud.common.DateUtil;
import com.lveqia.cloud.common.ResultUtil;
import com.lveqia.cloud.common.StringUtil;
import com.mujugroup.wx.exception.ParamException;
import com.mujugroup.wx.mapper.WxRelationMapper;
import com.mujugroup.wx.mapper.WxUptimeMapper;
import com.mujugroup.wx.model.WxRelation;
import com.mujugroup.wx.model.WxUptime;
import com.mujugroup.wx.service.WxUptimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * @author leolaurel
 */
@Service("wxUptimeService")
public class WxUptimeServiceImpl implements WxUptimeService {

    private final Logger logger = LoggerFactory.getLogger(WxUptimeServiceImpl.class);
    private final WxUptimeMapper wxUptimeMapper;
    private final WxRelationMapper wxRelationMapper;

    @Autowired
    public WxUptimeServiceImpl(WxUptimeMapper wxUptimeMapper, WxRelationMapper wxRelationMapper) {
        this.wxUptimeMapper = wxUptimeMapper;
        this.wxRelationMapper = wxRelationMapper;
    }

    @Override
    public WxUptime findListByHospital(Integer hid) {
        List<WxUptime> list = wxUptimeMapper.findListByRelation(WxRelation.KEY_HOSPITAL, hid);
        if (list == null || list.size() ==0) {
            return  getDefaultWxUptime();
        }
        return list.get(0);
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


    @Override
    public Long getEndTimeByHid(Integer hid) {
        return DateUtil.getTimesNight() + findListByHospital(hid).getStopTime();
    }

    @Override
    @Transactional
    public boolean update(int key, int kid, String startDesc, String stopDesc, String explain)
            throws NumberFormatException, ParamException {
        int startTime = DateUtil.getTimesNoDate(startDesc);
        int stopTime = DateUtil.getTimesNoDate(stopDesc);
        if(startTime <= stopTime) throw new ParamException(ResultUtil.CODE_REQUEST_FORMAT
                , "开始时间不能小于等于结束时间，否则无法跨天计算");
        // 若为默认数据，那么KID没有其他意义，置默认值
        if(key == WxRelation.KEY_DEFAULT) kid = WxRelation.KID_DEFAULT;
        List<WxUptime> list =  wxUptimeMapper.findListByRelation(key, kid);
        if(list !=null && list.size()>0){
            if(list.size() >1) logger.warn("当前规则有多个时间设置，需要处理！！");
            for (WxUptime wxUptime:list) {
                wxUptime.setStartTime(startTime);wxUptime.setStopTime(stopTime);
                wxUptime.setStartDesc(startDesc); wxUptime.setStopDesc(stopDesc);
                if(!StringUtil.isEmpty(explain)) wxUptime.setExplain(explain);
                wxUptimeMapper.update(wxUptime);
            }
        }else { // 插入新值
            WxUptime wxUptime = new WxUptime();
            wxUptime.setStartTime(startTime);wxUptime.setStopTime(stopTime);
            wxUptime.setStartDesc(startDesc); wxUptime.setStopDesc(stopDesc);
            if(!StringUtil.isEmpty(explain)) wxUptime.setExplain(explain);
            wxUptimeMapper.insert(wxUptime);
            WxRelation relation = new WxRelation();
            relation.setKey(key);  relation.setKid(kid);
            relation.setRid(wxUptime.getId());relation.setType(WxRelation.TYPE_UPTIME);
            wxRelationMapper.insert(relation);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean delete(int key, int kid) throws ParamException {
        if(key == WxRelation.KEY_DEFAULT) throw new ParamException(
                ResultUtil.CODE_REQUEST_FORMAT, "默认数据无法删除");
        List<WxUptime> list =  wxUptimeMapper.findListByRelation(key, kid);
        if(list !=null && list.size()>0){
            list.stream().mapToInt(WxUptime::getId).forEach(wxUptimeMapper::deleteById);
        }
        wxRelationMapper.deleteByType(WxRelation.TYPE_UPTIME, key, kid);
        return true;
    }
}
