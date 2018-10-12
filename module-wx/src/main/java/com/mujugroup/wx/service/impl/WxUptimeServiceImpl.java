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
            WxRelation relation = new WxRelation();
            relation.setKey(key);
            relation.setKid(kid);
            relation.setRid(wxUptime.getId());
            relation.setType(type);
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
        WxUptime wxEveUptime = query(WxRelation.TYPE_UPTIME, WxRelation.KEY_HOSPITAL, hid);
        UptimeVo wxEveUptimeVo = new UptimeVo();
        if (wxEveUptime != null) {
            //设置Kid
            wxEveUptimeVo.setKid(hid);
            //设置当前类型为医院类型
            wxEveUptimeVo.setKey(WxRelation.KEY_HOSPITAL);
            //设置自定义运行时间ID
            wxEveUptimeVo.setEveId(wxEveUptime.getId());
            //设置自定义运行时间的解释说明
            wxEveUptimeVo.setExplain(wxEveUptime.getExplain());
            //当前时间类型为自定义时间类型
            wxEveUptimeVo.setTimeType(1);
            wxEveUptimeVo.setEveningTime(wxEveUptime.getStartDesc() + "-" + wxEveUptime.getStopDesc());
        } else {
            //设置Kid
            wxEveUptimeVo.setKid(aid);
            //设置当前类型为代理商类型
            wxEveUptimeVo.setKey(WxRelation.KEY_AGENT);
            wxEveUptime = findByXid(new int[]{0, aid}, WxRelation.TYPE_UPTIME);
            //设置默认运行时间ID
            wxEveUptimeVo.setEveId(wxEveUptime.getId());
            //设置默认运行时间的解释说明
            wxEveUptimeVo.setExplain(wxEveUptime.getExplain());
            //默认时间类型
            wxEveUptimeVo.setTimeType(0);
            wxEveUptimeVo.setEveningTime(wxEveUptime.getStartDesc() + "-" + wxEveUptime.getStopDesc());
        }
        //获取午休时间
        WxUptime wxNoonUptime = query(WxRelation.TYPE_MIDDAY, WxRelation.KEY_HOSPITAL, hid);
        if (wxNoonUptime != null) {
            //设置Kid
            wxEveUptimeVo.setKid(hid);
            //设置当前类型为医院类型
            wxEveUptimeVo.setKey(WxRelation.KEY_HOSPITAL);
            //设置自定义午休类型ID
            wxEveUptimeVo.setNoonId(wxNoonUptime.getId());
            //设置自定义午休类型的解释说明
            wxEveUptimeVo.setExplain(wxNoonUptime.getExplain());
            //当前休息类型午休时间
            wxEveUptimeVo.setRestType(1);
            wxEveUptimeVo.setNoonTime(wxNoonUptime.getStartDesc() + "-" + wxNoonUptime.getStopDesc());
        } else {
            wxNoonUptime = findByXid(new int[]{0, aid}, WxRelation.TYPE_MIDDAY);
            //设置Kid
            wxEveUptimeVo.setKid(aid);
            //设置当前类型为代理商类型
            wxEveUptimeVo.setKey(WxRelation.KEY_AGENT);
            //设置默认午休类型的ID
            wxEveUptimeVo.setNoonId(wxNoonUptime.getId());
            //设置默认午休类型的解释说明
            wxEveUptimeVo.setExplain(wxNoonUptime.getExplain());
            //当前休息类型为午休类型
            wxEveUptimeVo.setRestType(0);
            wxEveUptimeVo.setNoonTime(wxNoonUptime.getStartDesc() + "-" + wxNoonUptime.getStopDesc());
        }
        List<UptimeVo> wxUptimeVos = new ArrayList<>();
        //跨服务调用,获取当前医院下的所有科室
        Set<Integer> departmentIdList = moduleCoreService.findOidByHid(Integer.toString(hid));
        for (Integer item : departmentIdList) {
            wxUptimeVos.add(getDepartmentTime(item, wxEveUptime, wxNoonUptime));
        }
        wxEveUptimeVo.setChildren(wxUptimeVos);
        return wxEveUptimeVo;
    }

    @Override
    public boolean insert(WxUptime wxUptime) {
        return wxUptimeMapper.insert(wxUptime);
    }

    /*@Override
    @Transactional
    public boolean modify(String useTime, String explain, int restType, int eveId, int noonId) throws ParamException {
        boolean result = true;
        if (restType != WxRelation.TYPE_UPTIME && restType != WxRelation.TYPE_MIDDAY)
            throw new ParamException("Type只能为2:运行时间 3:午休时间");
        //当前休息类型为运行类型
        if (restType == WxRelation.TYPE_UPTIME) {
            WxUptime wxUpEveTime = bindModel(useTime, explain, eveId);
            if (wxUpEveTime.getStartTime() <= wxUpEveTime.getStopTime()) {
                throw new ParamException("开始时间不能小于等于结束时间，否则无法跨天计算");
            }
            result &= wxUptimeMapper.update(wxUpEveTime);
        } else {
            //当前休息类型为午休类型
            WxUptime wxUpNoonTime = bindModel(useTime, explain, noonId);
            if (wxUpNoonTime.getStartTime() > wxUpNoonTime.getStopTime()) {
                throw new ParamException("午休时间开始时间不能大于结束时间");
            }
            result &= wxUptimeMapper.update(wxUpNoonTime);
        }
        return result == true ? true : false;
    }*/

    private WxUptime bindModel(String strTime, String explain, int id) {
        WxUptime wxUptime = new WxUptime();
        wxUptime.setId(id);
        String startDesc = strTime.split("-")[0];
        String stopDesc = strTime.split("-")[1];
        int startTime = DateUtil.getTimesNoDate(startDesc);
        int stopTime = DateUtil.getTimesNoDate(stopDesc);
        wxUptime.setStartTime(startTime);
        wxUptime.setStopTime(stopTime);
        wxUptime.setStartDesc(startDesc);
        wxUptime.setStopDesc(stopDesc);
        wxUptime.setExplain(explain);
        return wxUptime;
    }
    @Override
    @Transactional
    public boolean insertOrModify(int timeType,String useTime,String restTime, String explain, Integer key, int restType, int eveId, int noonId, int kid) throws ParamException {
        boolean result = true;
        if(timeType ==1){
            // 修改使用时间
            WxUptime wxUpEveTime = bindModel(useTime, explain, eveId);
            if (wxUpEveTime.getStartTime() <= wxUpEveTime.getStopTime()) {
                throw new ParamException("开始时间不能小于等于结束时间，否则无法跨天计算");
            }
            result &= wxUptimeMapper.update(wxUpEveTime);
        }else{
            // 添加使用时间
            String startDesc = useTime.split("-")[0];
            String stopDesc = useTime.split("-")[1];
            int startTime = DateUtil.getTimesNoDate(startDesc);
            int stopTime = DateUtil.getTimesNoDate(stopDesc);
            WxUptime wxUptime = new WxUptime();
            wxUptime.setStartTime(startTime);
            wxUptime.setStopTime(stopTime);
            wxUptime.setStartDesc(startDesc);
            wxUptime.setStopDesc(stopDesc);
            wxUptime.setExplain(explain);
            result &= wxUptimeMapper.insert(wxUptime);
            WxRelation relation = new WxRelation();
            relation.setKey(key);
            relation.setRid(wxUptime.getId());
            relation.setKid(kid);
            relation.setType(WxRelation.TYPE_UPTIME);
            result &= wxRelationMapper.insert(relation);
        }
        if(restType ==1){
            // 修改午休时间
            //当前休息类型为午休类型
            WxUptime wxUpNoonTime = bindModel(restTime, explain, noonId);
            if (wxUpNoonTime.getStartTime() > wxUpNoonTime.getStopTime()) {
                throw new ParamException("午休时间开始时间不能大于结束时间");
            }
            result &= wxUptimeMapper.update(wxUpNoonTime);
        }else{
            // 添加午休时间
            WxUptime wxUptime = new WxUptime();
            String startDesc = restTime.split("-")[0];
            String stopDesc = restTime.split("-")[1];
            int startTime = DateUtil.getTimesNoDate(startDesc);
            int stopTime = DateUtil.getTimesNoDate(stopDesc);
            wxUptime.setExplain(explain);
            wxUptime.setStartTime(startTime);
            wxUptime.setStopTime(stopTime);
            wxUptime.setStartDesc(startDesc);
            wxUptime.setStopDesc(stopDesc);
            result &= wxUptimeMapper.insert(wxUptime);
            WxRelation relation = new WxRelation();
            relation.setKey(key);
            relation.setRid(wxUptime.getId());
            relation.setKid(kid);
            relation.setType(WxRelation.TYPE_MIDDAY);
            result &= wxRelationMapper.insert(relation);
        }
        return  result;
    }

    private UptimeVo getDepartmentTime(Integer id, WxUptime eveUptime, WxUptime noonUptime) {
        WxUptime wxUptimeEve = query(WxRelation.TYPE_UPTIME, WxRelation.KEY_DEPARTMENT, id);
        WxUptime wxUptimeNoon = query(WxRelation.TYPE_MIDDAY, WxRelation.KEY_DEPARTMENT, id);
        UptimeVo wxUptimeVo = new UptimeVo();
        //设置kid
        wxUptimeVo.setKid(id);
        //设置当前类型为科室
        wxUptimeVo.setKey(WxRelation.KEY_DEPARTMENT);
        if (wxUptimeEve == null) {
            //设置默认运行时间类型的ID
            wxUptimeVo.setEveId(eveUptime.getId());
            //设置默认运行时间类型的解释说明
            wxUptimeVo.setExplain(eveUptime.getExplain());
            //运行时间类型为默认时间类型的运行时间
            wxUptimeVo.setTimeType(0);
            wxUptimeVo.setEveningTime(eveUptime.getStartDesc() + "-" + eveUptime.getStopDesc());
        } else {
            //设置自定义运行时间类型ID
            wxUptimeVo.setEveId(wxUptimeEve.getId());
            //设置自定义运行时间的解释说明
            wxUptimeVo.setExplain(wxUptimeEve.getExplain());
            wxUptimeVo.setTimeType(1);
            wxUptimeVo.setEveningTime(wxUptimeEve.getStartDesc() + "-" + wxUptimeEve.getStopDesc());
        }
        if (wxUptimeNoon == null) {
            wxUptimeVo.setNoonId(noonUptime.getId());
            wxUptimeVo.setExplain(noonUptime.getExplain());
            //当前休息类型为午休时间
            wxUptimeVo.setRestType(0);
            wxUptimeVo.setNoonTime(noonUptime.getStartDesc() + "-" + noonUptime.getStopDesc());
        } else {
            wxUptimeVo.setNoonId(wxUptimeNoon.getId());
            wxUptimeVo.setExplain(wxUptimeNoon.getExplain());
            wxUptimeVo.setRestType(1);
            wxUptimeVo.setNoonTime(wxUptimeNoon.getStartDesc() + "-" + wxUptimeNoon.getStopDesc());
        }
        return wxUptimeVo;
    }
}