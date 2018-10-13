package com.mujugroup.wx.service.impl;

import com.lveqia.cloud.common.util.DateUtil;
import com.lveqia.cloud.common.util.StringUtil;
import com.lveqia.cloud.common.exception.ParamException;
import com.mujugroup.wx.mapper.WxRelationMapper;
import com.mujugroup.wx.mapper.WxUptimeMapper;
import com.mujugroup.wx.model.WxRelation;
import com.mujugroup.wx.model.WxUptime;
import com.mujugroup.wx.objeck.vo.UptimeVo;
import com.mujugroup.wx.service.WxUptimeService;
import com.mujugroup.wx.service.feign.ModuleCoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author leolaurel
 */
@Service("wxUptimeService")
public class WxUptimeServiceImpl implements WxUptimeService {

    private final Logger logger = LoggerFactory.getLogger(WxUptimeServiceImpl.class);
    private final WxUptimeMapper wxUptimeMapper;
    private final WxRelationMapper wxRelationMapper;
    private ModuleCoreService moduleCoreService;


    @Autowired
    public WxUptimeServiceImpl(WxUptimeMapper wxUptimeMapper, WxRelationMapper wxRelationMapper, ModuleCoreService moduleCoreService) {
        this.wxUptimeMapper = wxUptimeMapper;
        this.wxRelationMapper = wxRelationMapper;
        this.moduleCoreService = moduleCoreService;
    }

    @Override
    public WxUptime find(int type, String aid, String hid, String oid) {
        return find(type, Integer.parseInt(aid), Integer.parseInt(hid), Integer.parseInt(oid));
    }

    @Override
    public WxUptime find(int type, int aid, int hid, int oid) {
        WxUptime wxUptime = findByXid(new int[]{0, aid, hid, oid}, type);
        if (wxUptime == null) {
            return getDefaultWxUptime(type);
        }
        return wxUptime;
    }

    @Override
    public WxUptime findByXid(int[] ints, int type) {
        WxUptime wxUptime = null;
        for (int i = ints.length - 1; i > -1; i--) {
            wxUptime = query(type, i, ints[i]);
            if (wxUptime != null) break;
        }
        return wxUptime;
    }

    public WxUptime query(int type, int key, int kid) {
        if (key == WxRelation.KEY_DEFAULT) kid = WxRelation.KID_DEFAULT;
        List<WxUptime> list = wxUptimeMapper.findListByRelation(type, key, kid);
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 获取默认数据
     *
     * @param type
     */
    @Override
    public WxUptime getDefaultWxUptime(int type) {
        logger.debug("采用默认时间Type:{}", type);
        WxUptime wxUptime = new WxUptime();
        if (type == WxRelation.TYPE_MIDDAY) {
            wxUptime.setStartDesc("12:00");
            wxUptime.setStartTime(12 * 60 * 60);
            wxUptime.setStopDesc("13:00");
            wxUptime.setStopTime(13 * 60 * 60);
            wxUptime.setExplain("默认午休数据(代码生成)");
        } else {
            wxUptime.setStartDesc("18:00");
            wxUptime.setStartTime(18 * 60 * 60);
            wxUptime.setStopDesc("6:00");
            wxUptime.setStopTime(6 * 60 * 60);
            wxUptime.setExplain("默认普通数据(代码生成)");
        }
        return wxUptime;

    }

    @Override
    @Transactional
    public boolean update(int type, int key, int kid, String startDesc, String stopDesc, String explain)
            throws NumberFormatException, ParamException {
        int startTime = DateUtil.getTimesNoDate(startDesc);
        int stopTime = DateUtil.getTimesNoDate(stopDesc);
        if (type == WxRelation.TYPE_UPTIME && startTime <= stopTime)
            throw new ParamException("开始时间不能小于等于结束时间，否则无法跨天计算");
        if (type == WxRelation.TYPE_MIDDAY && startTime > stopTime)
            throw new ParamException("午休时间开始时间不能大于结束时间");
        if (type != WxRelation.TYPE_UPTIME && type != WxRelation.TYPE_MIDDAY)
            throw new ParamException("Type只能为2:运行时间 3:午休时间");
        // 若为默认数据，那么KID没有其他意义，置默认值
        if (key == WxRelation.KEY_DEFAULT) kid = WxRelation.KID_DEFAULT;
        List<WxUptime> list = wxUptimeMapper.findListByRelation(type, key, kid);
        if (list != null && list.size() > 0) {
            if (list.size() > 1) logger.warn("当前规则有多个时间设置，需要处理！！");
            for (WxUptime wxUptime : list) {
                wxUptime.setStartTime(startTime);
                wxUptime.setStopTime(stopTime);
                wxUptime.setStartDesc(startDesc);
                wxUptime.setStopDesc(stopDesc);
                if (!StringUtil.isEmpty(explain)) wxUptime.setExplain(explain);
                wxUptimeMapper.update(wxUptime);
            }
        } else { // 插入新值
            WxUptime wxUptime = new WxUptime();
            wxUptime.setStartTime(startTime);
            wxUptime.setStopTime(stopTime);
            wxUptime.setStartDesc(startDesc);
            wxUptime.setStopDesc(stopDesc);
            if (!StringUtil.isEmpty(explain)) wxUptime.setExplain(explain);
            wxUptimeMapper.insert(wxUptime);
            WxRelation relation = bindRelation(key, kid, wxUptime.getId(), type);
            wxRelationMapper.insert(relation);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean delete(int type, int key, int kid) throws ParamException {
        if (key == WxRelation.KEY_DEFAULT) throw new ParamException("默认数据无法删除");
        if (type != WxRelation.TYPE_UPTIME && type != WxRelation.TYPE_MIDDAY)
            throw new ParamException("Type只能为2:运行时间 3:午休时间");
        List<WxUptime> list = wxUptimeMapper.findListByRelation(type, key, kid);
        if (list != null && list.size() > 0) {
            list.stream().mapToInt(WxUptime::getId).forEach(wxUptimeMapper::deleteById);
        }
        wxRelationMapper.deleteByType(type, key, kid);
        return true;
    }

    @Override
    public UptimeVo getWXUptimeVo(int aid, int hid) {
        WxUptime eveUptime = query(WxRelation.TYPE_UPTIME, WxRelation.KEY_HOSPITAL, hid);
        UptimeVo hidUptimeVo = new UptimeVo(hid, WxRelation.KEY_HOSPITAL);
        if (eveUptime != null) {
            hidUptimeVo.setEveInfo(eveUptime.getId(),1 , eveUptime.toString(), eveUptime.getExplain());
        } else {
            eveUptime = findByXid(new int[]{0, aid}, WxRelation.TYPE_UPTIME);
            hidUptimeVo.setEveInfo(eveUptime.getId(),0 , eveUptime.toString(), eveUptime.getExplain());
        }
        //获取午休时间
        WxUptime noonUptime = query(WxRelation.TYPE_MIDDAY, WxRelation.KEY_HOSPITAL, hid);
        if (noonUptime != null) {
            hidUptimeVo.setNoonInfo(noonUptime.getId(),1, noonUptime.toString(), noonUptime.getExplain());
        } else {
            noonUptime = findByXid(new int[]{0, aid}, WxRelation.TYPE_MIDDAY);
            hidUptimeVo.setNoonInfo(noonUptime.getId(),0, noonUptime.toString(), noonUptime.getExplain());
        }
        List<UptimeVo> wxUptimeVos = new ArrayList<>();
        //跨服务调用,获取当前医院下的所有科室
        Set<Integer> departmentIdList = moduleCoreService.findOidByHid(Integer.toString(hid));
        for (Integer item : departmentIdList) {
            wxUptimeVos.add(getDepartmentTime(item, eveUptime, noonUptime));
        }
        hidUptimeVo.setChildren(wxUptimeVos);
        return hidUptimeVo;
    }

    @Override
    public boolean insert(WxUptime wxUptime) {
        return wxUptimeMapper.insert(wxUptime);
    }


    @Override
    @Transactional
    public boolean insertOrModify(String eveTime, String noonTime, int eveType, int noonType, String eveExplain
            , String noonExplain, int eveId, int noonId, int key, int kid) throws ParamException {
        boolean result;
        if (eveType == 1 && eveId <= 0) throw new ParamException("自定义晚休时间ID不能小于等于0");
        if (noonType == 1 && noonId <= 0)  throw new ParamException("自定义午休时间ID不能小于等于0");
        WxUptime eveUptime = query(WxRelation.TYPE_UPTIME, key, kid);
        if(eveType ==1){// 修改晚休时间, 先判断是否存在自定义
            if(eveUptime == null || eveUptime.getId() != eveId) throw new ParamException("请确认eveId是否正确");
            WxUptime wxUpEveTime = bindModel(WxRelation.TYPE_UPTIME, eveTime, eveExplain, eveId);
            result = wxUptimeMapper.update(wxUpEveTime);
        }else{ // 添加使用时间
            if(eveUptime != null) throw new ParamException("请确认eveType是否正确, 当前存在定义晚休类型");
            WxUptime wxUptime = bindModel(WxRelation.TYPE_UPTIME, eveTime, eveExplain, 0);
            result = wxUptimeMapper.insert(wxUptime);
            WxRelation relation = bindRelation(key, kid, wxUptime.getId(), WxRelation.TYPE_UPTIME);
            result &= wxRelationMapper.insert(relation);
        }
        WxUptime noonUptime = query(WxRelation.TYPE_MIDDAY, key, kid);
        if(noonType ==1){ // 修改午休时间，先判断是否存在自定义
            if(noonUptime == null || noonUptime.getId() != noonId) throw new ParamException("请确认noonId是否正确");
            WxUptime wxUpNoonTime = bindModel(WxRelation.TYPE_MIDDAY, noonTime, noonExplain, noonId);
            result &= wxUptimeMapper.update(wxUpNoonTime);
        }else{   // 添加午休时间
            if(noonUptime != null) throw new ParamException("请确认noonType是否正确, 当前存在定义午休类型");
            WxUptime wxUptime = bindModel(WxRelation.TYPE_MIDDAY, noonTime, noonExplain, 0);
            result &= wxUptimeMapper.insert(wxUptime);
            WxRelation relation = bindRelation(key, kid, wxUptime.getId(), WxRelation.TYPE_MIDDAY);
            result &= wxRelationMapper.insert(relation);
        }
        return  result;
    }

    /**o
     * 获取科室VO对象
     */
    private UptimeVo getDepartmentTime(Integer oid, WxUptime eveUptime, WxUptime noonUptime) {
        WxUptime wxUptimeEve = query(WxRelation.TYPE_UPTIME, WxRelation.KEY_DEPARTMENT, oid);
        WxUptime wxUptimeNoon = query(WxRelation.TYPE_MIDDAY, WxRelation.KEY_DEPARTMENT, oid);
        UptimeVo wxUptimeVo = new UptimeVo(oid, WxRelation.KEY_DEPARTMENT);
        if (wxUptimeEve == null) { //设置默认运行时间类型的ID
            wxUptimeVo.setEveInfo(eveUptime.getId(), 0 , eveUptime.toString(), eveUptime.getExplain());
        } else { //设置自定义运行时间类型ID
            wxUptimeVo.setEveInfo(wxUptimeEve.getId(), 1 , wxUptimeEve.toString(), wxUptimeEve.getExplain());
        }
        if (wxUptimeNoon == null) {
            wxUptimeVo.setNoonInfo(noonUptime.getId(),0, noonUptime.toString(), noonUptime.getExplain());
        } else {
            wxUptimeVo.setNoonInfo(wxUptimeNoon.getId(),1, wxUptimeNoon.toString(), wxUptimeNoon.getExplain());
        }
        return wxUptimeVo;
    }
    private WxUptime bindModel(int type, String strTime, String explain, int id) throws ParamException {
        WxUptime wxUptime = new WxUptime();
        if(id > 0) wxUptime.setId(id);
        String startDesc = strTime.split("-")[0];
        String stopDesc = strTime.split("-")[1];
        int startTime = DateUtil.getTimesNoDate(startDesc);
        int stopTime = DateUtil.getTimesNoDate(stopDesc);
        if (type == WxRelation.TYPE_UPTIME && startTime <= stopTime) {
            throw new ParamException("开始时间不能小于等于结束时间，否则无法跨天计算");
        }
        if (type == WxRelation.TYPE_MIDDAY && startTime > stopTime) {
            throw new ParamException("午休时间开始时间不能大于结束时间");
        }
        wxUptime.setStartTime(startTime);
        wxUptime.setStopTime(stopTime);
        wxUptime.setStartDesc(startDesc);
        wxUptime.setStopDesc(stopDesc);
        wxUptime.setExplain(explain);
        return wxUptime;
    }


    private WxRelation bindRelation(int key, int kid, int rid, int type) {
        WxRelation relation = new WxRelation();
        relation.setKey(key);
        relation.setRid(rid);
        relation.setKid(kid);
        relation.setType(type);
        return relation;
    }


}