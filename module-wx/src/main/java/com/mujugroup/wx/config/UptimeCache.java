package com.mujugroup.wx.config;

import com.lveqia.cloud.common.cache.GuavaCache;
import com.lveqia.cloud.common.cache.ILocalCache;
import com.mujugroup.wx.model.WxUptime;
import com.mujugroup.wx.service.WxUptimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.concurrent.ExecutionException;


/**
 * 本地缓存：aid;hid;oid;gid;tid -> 订单结束时间
 * @author leolaurel
 */
@Component(value = "uptimeCache")
public class UptimeCache extends GuavaCache<String, WxUptime> implements ILocalCache<String, WxUptime> {

    private final WxUptimeService wxUptimeService;

    @Autowired
    public UptimeCache(WxUptimeService wxUptimeService) {
        this.wxUptimeService = wxUptimeService;
        setThreadSize(3);
        setMaximumSize(100);
        setExpireAfterWriteDuration(60);
        setRefreshAfterWriteDuration(1);
    }

    @Override
    protected WxUptime loadData(String key) {
        String[] keys = key.split(";");
        return wxUptimeService.find(Integer.parseInt(keys[0]), keys[1], keys[2], keys[3]);
    }

    @Override
    public WxUptime get(String key){
        try {
            return getValue(key);
        } catch (ExecutionException e) {
            logger.error("key={}获取Uptime对象，可能是数据库中无该记录。", key ,e);
        }
        return null;
    }
}
