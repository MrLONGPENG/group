package com.mujugroup.wx.config;

import com.lveqia.cloud.common.DateUtil;
import com.lveqia.cloud.common.cache.GuavaCache;
import com.lveqia.cloud.common.cache.ILocalCache;
import com.mujugroup.wx.model.WxGoods;
import com.mujugroup.wx.model.WxRelation;
import com.mujugroup.wx.model.WxUptime;
import com.mujugroup.wx.service.WxGoodsService;
import com.mujugroup.wx.service.WxUptimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.concurrent.ExecutionException;


/**
 * 本地缓存：aid;hid;oid;gid;tid -> 订单结束时间
 * @author leolaurel
 */
@Component(value = "endTimeCache")
public class EndTimeCache extends GuavaCache<String, Long> implements ILocalCache<String, Long> {

    private final WxUptimeService wxUptimeService;
    private final WxGoodsService wxGoodsService;

    @Autowired
    public EndTimeCache(WxUptimeService wxUptimeService, WxGoodsService wxGoodsService) {
        this.wxUptimeService = wxUptimeService;
        this.wxGoodsService = wxGoodsService;
        setThreadSize(3);
        setMaximumSize(10);
        setExpireAfterWriteDuration(60);
        setRefreshAfterWriteDuration(1);
    }

    @Override
    protected Long loadData(String key) {
        String[] keys = key.split(";");
        if(keys.length< 4) return DateUtil.getTimesNight();
        WxGoods wxGoods = wxGoodsService.findById(Integer.parseInt(keys[3]));
        long endTime = DateUtil.getTimesMorning();
        if(wxGoods.getType() == WxGoods.TYPE_MIDDAY){
            WxUptime midday = wxUptimeService.find(WxRelation.TYPE_MIDDAY, keys[0], keys[1], keys[2]);
            endTime += midday.getStopTime();
        }else if(wxGoods.getType() == WxGoods.TYPE_NIGHT){
            WxUptime wxUptime = wxUptimeService.find(WxRelation.TYPE_UPTIME, keys[0], keys[1], keys[2]);
            if(DateUtil.getTimesNoDate() < wxUptime.getStartTime()){ // 当前时分秒小于开锁时间，即第二天上午购买
                endTime -= 24*60*60; // 若隔天，减少一天
                logger.debug("第二天上午购买  到期时间:{}", new Date(endTime *1000));
            }
            endTime += (wxUptime.getStopTime() + wxGoods.getDays() * 24 * 60 * 60);
            logger.debug("购买天数{} 到期时间:{}", wxGoods.getDays(), new Date(endTime *1000));
        }
        return endTime;
    }

    @Override
    public Long get(String key){
        try {
            return getValue(key);
        } catch (ExecutionException e) {
            logger.error("key={}获取结束时间，可能是数据库中无该记录。", key ,e);
        }
        return (long)DateUtil.getTimesNoDate();
    }
}
