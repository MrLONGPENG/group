package com.mujugroup.wx.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.lveqia.cloud.common.DateUtil;
import com.lveqia.cloud.common.ResultUtil;
import com.lveqia.cloud.common.StringUtil;
import com.mujugroup.wx.exception.ParamException;
import com.mujugroup.wx.mapper.WxRelationMapper;
import com.mujugroup.wx.mapper.WxUptimeMapper;
import com.mujugroup.wx.model.WxGoods;
import com.mujugroup.wx.model.WxRelation;
import com.mujugroup.wx.model.WxUptime;
import com.mujugroup.wx.service.WxGoodsService;
import com.mujugroup.wx.service.WxUptimeService;
import io.micrometer.core.lang.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author leolaurel
 */
@Service("wxUptimeService")
public class WxUptimeServiceImpl implements WxUptimeService {

    private final Logger logger = LoggerFactory.getLogger(WxUptimeServiceImpl.class);
    private final WxUptimeMapper wxUptimeMapper;
    private final WxRelationMapper wxRelationMapper;
    private final WxGoodsService wxGoodsService;
    private ListeningExecutorService backgroundRefreshPools;
    private LoadingCache<String, Long> caches;
    @Autowired
    public WxUptimeServiceImpl(WxUptimeMapper wxUptimeMapper, WxRelationMapper wxRelationMapper
            , WxGoodsService wxGoodsService) {
        this.wxUptimeMapper = wxUptimeMapper;
        this.wxRelationMapper = wxRelationMapper;
        this.wxGoodsService = wxGoodsService;
        this.backgroundRefreshPools = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(3));
        this.caches = CacheBuilder.newBuilder()
                .maximumSize(100).refreshAfterWrite(30, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Long>() {
                    @Override
                    public Long load(@NonNull String key) {
                        logger.debug("首次读取缓存: " + key);
                        return getEndTimeByKey(key.split(";"));
                    }
                    @Override
                    public ListenableFuture<Long> reload(final String key, Long oldValue){
                        return backgroundRefreshPools.submit(() -> {
                            logger.debug("异步刷新缓存: " + key);
                            return getEndTimeByKey(key.split(";"));
                        });
                    }
                });
    }

    @Override
    public WxUptime query(int key, int kid) {
        List<WxUptime> list = wxUptimeMapper.findListByRelation(key, kid);
        if (list == null || list.size() ==0) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public WxUptime findListByHospital(Integer hid) {
        WxUptime wxUptime= query(WxRelation.KEY_HOSPITAL, hid);
        if (wxUptime == null) {
            return getDefaultWxUptime();
        }
        return wxUptime;
    }

    /**
     * 获取默认数据
     */
    @Override
    public WxUptime getDefaultWxUptime() {
        logger.debug("未找到医院指定开关锁时间，采用默认开关锁时间");
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
    public Long getEndTimeByKey(String key) throws ExecutionException {
        return caches.get(key);
    }

    @Override
    public Long getEndTimeByKey(String[] keys) {
        if(keys.length< 4) return DateUtil.getTimesNight();
        WxUptime wxUptime = findListByHospital(Integer.parseInt(keys[1]));
        long endTime =  DateUtil.getTimesNight() + wxUptime.getStopTime();
        if(endTime - System.currentTimeMillis()/1000 > 24*60*60){
            endTime -= 24*60*60; // 若隔天，减少一天
            logger.debug("第二天上午购买  到期时间:{}", new Date(endTime *1000));
        }
        WxGoods wxGoods = wxGoodsService.findById(Integer.parseInt(keys[3]));
        if(wxGoods.getDays()> 1){
            endTime += (wxGoods.getDays()-1)*24*60*60;
            logger.debug("购买天数{} 到期时间:{}", wxGoods.getDays(), new Date(endTime *1000));
        }
        return endTime;
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
