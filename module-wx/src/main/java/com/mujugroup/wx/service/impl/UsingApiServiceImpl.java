package com.mujugroup.wx.service.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lveqia.cloud.common.util.AESUtil;
import com.lveqia.cloud.common.util.DateUtil;
import com.lveqia.cloud.common.util.StringUtil;
import com.lveqia.cloud.common.cache.ILocalCache;
import com.lveqia.cloud.common.exception.TokenException;
import com.mujugroup.wx.bean.QueryBean;
import com.mujugroup.wx.bean.UnlockBean;
import com.mujugroup.wx.bean.UptimeBean;
import com.mujugroup.wx.bean.UsingBean;
import com.mujugroup.wx.model.WxGoods;
import com.mujugroup.wx.model.WxRelation;
import com.mujugroup.wx.model.WxUptime;
import com.mujugroup.wx.model.WxUsing;
import com.mujugroup.wx.service.*;
import com.mujugroup.wx.service.feign.ModuleCoreService;
import com.mujugroup.wx.service.feign.ModuleLockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;


@RefreshScope
@Service("usingApiService")
public class UsingApiServiceImpl implements UsingApiService {

    private final Logger logger = LoggerFactory.getLogger(UsingApiServiceImpl.class);

    private final static String SPLIT = "!@!";
    private final SessionService sessionService;
    private final WxUsingService wxUsingService;
    private final WxGoodsService wxGoodsService;
    private final ModuleCoreService moduleCoreService;
    private final ModuleLockService moduleLockService;
    private final RedisTemplate redisTemplate;

    @Resource(name = "uptimeCache")
    private ILocalCache<String, WxUptime> uptimeCache;

    @Autowired
    public UsingApiServiceImpl(SessionService sessionService, WxUsingService wxUsingService
            , WxGoodsService wxGoodsService, RedisTemplate redisTemplate, ModuleCoreService moduleCoreService
            , ModuleLockService moduleLockService) {
        this.redisTemplate = redisTemplate;
        this.wxUsingService = wxUsingService;
        this.sessionService = sessionService;
        this.wxGoodsService = wxGoodsService;
        this.moduleCoreService = moduleCoreService;
        this.moduleLockService = moduleLockService;

    }

    /**
     *  检查是否使用
     */
    @Override
    public UsingBean checkUsing(String sessionThirdKey, String did) {
        UsingBean usingBean = new UsingBean();
            try{
                String realDid;
                long time = System.currentTimeMillis()/1000;
                String openId = sessionService.getOpenId(sessionThirdKey);
                WxUsing wxUsing =  wxUsingService.findUsingByOpenId(openId, time);
                if(wxUsing==null && StringUtil.isEmpty(did)){ // 用户未支付，未扫描
                    usingBean.setType(4);
                    usingBean.setInfo("用户未支付，未扫描");
                    return usingBean;
                }else if(wxUsing==null){          // 用户未支付，扫描进入
                    realDid = did;
                    wxUsing = wxUsingService.findUsingByDid(realDid, time, false);
                }else{                            // 用户已支付
                    realDid = StringUtil.autoFillDid(wxUsing.getDid());
                    if(did!=null && !realDid.equals(did)) usingBean.setMismatch(true);
                }
                usingBean.setDid(realDid);
                String result = moduleCoreService.deviceQuery(realDid);
                if(result == null){
                    usingBean.setType(5);
                    usingBean.setInfo("服务器异常,请稍后尝试!");
                    return usingBean;
                }
                JsonObject returnData = new JsonParser().parse(result).getAsJsonObject();
                if(returnData.get("code").getAsInt() == 200 && returnData.has("data")){
                    JsonObject data = returnData.getAsJsonObject("data");
                    if(wxUsing!=null && !openId.equals(wxUsing.getOpenId())) {
                        usingBean.setType(1);
                        usingBean.setInfo("该设备已经被他人使用，请联系客户");
                    }else if(wxUsing!=null && wxUsing.getUsing()){  // 使用中,计算时间
                        usingBean.setType(2);
                        usingBean.setPayTime(wxUsing.getPayTime());
                        usingBean.setEndTime(wxUsing.getEndTime());
                    }else { // 这里需要去获取医院信息
                        usingBean.setType(0);
                    }
                    usingBean.setPay(wxUsing!=null);
                    String aid = data.get("agentId").getAsString();
                    String hid = data.get("hospitalId").getAsString();
                    String oid = data.get("departmentId").getAsString();
                    usingBean.setCode(generateCode(openId, realDid, aid, hid, oid));
                    usingBean.setHospitalBed(data.get("hospitalBed").getAsString());
                    usingBean.setHospital(data.getAsJsonObject("hospital").get("name").getAsString());
                    usingBean.setAddress(data.getAsJsonObject("hospital").get("address").getAsString());
                    usingBean.setDepartment(data.getAsJsonObject("department").get("name").getAsString());
                    // 根据医院ID设置开关锁时间
                    usingBean.setWxUptime(uptimeCache.get(StringUtil.toLink(WxRelation.TYPE_UPTIME, aid, hid, oid))
                            , uptimeCache.get(StringUtil.toLink(WxRelation.TYPE_MIDDAY, aid, hid, oid)));
                }else{
                    usingBean.setType(3);
                    usingBean.setInfo("设备未激活或非法设备");
                }
            }catch (Exception e){
                logger.info("远程调用异常",e);
                usingBean.setType(5);
                usingBean.setInfo("服务器异常,请稍后尝试!");
            }

        return usingBean;
    }
    /**
     *  开锁逻辑
     */
    @Override
    public UnlockBean unlock(String did, String[] arr) {
        UnlockBean unlockBean = new UnlockBean();
        int currTime =  DateUtil.getTimesNoDate();
        WxUptime midday = uptimeCache.get(StringUtil.toLink(WxRelation.TYPE_MIDDAY, arr[2], arr[3], arr[4]));
        boolean isMidday = midday != null && currTime > midday.getStartTime() && currTime < midday.getStopTime();
        if(!isMidday){
            WxUptime wxUptime = uptimeCache.get(StringUtil.toLink(WxRelation.TYPE_UPTIME, arr[2], arr[3], arr[4]));
            if(wxUptime != null && currTime > wxUptime.getStopTime() && currTime < wxUptime.getStartTime()){
                unlockBean.setPayState(3);
                unlockBean.setInfo(String.format("上午%s到下午%s无法开锁，如有任何问题可联系客服"
                        , wxUptime.getStopDesc(), wxUptime.getStartDesc()));
                return unlockBean;
            }
        }
        boolean isFree = false;
        WxUsing wxUsing = wxUsingService.findUsingByDid(did, System.currentTimeMillis()/1000, false);
        if(isMidday && wxUsing ==null){
            logger.debug("午休时间开锁，先查询午休是否免费，再查询昨天是否有支付，若有则免费开锁");
            List<WxGoods> goodsList = wxGoodsService.findListExcludeType(WxGoods.EXCLUDE_NIGHT, arr[2], arr[3], arr[4]);
            isFree = goodsList.stream().anyMatch(g -> g.getType() == WxGoods.TYPE_MIDDAY && g.getPrice() == 0);
            wxUsing = wxUsingService.findUsingByDid(did, DateUtil.getTimesMorning(), false);
            if(wxUsing==null && !isFree){
                logger.debug("昨天未使用，且午休收费状态");
                unlockBean.setPayState(1);
                unlockBean.setGoods(goodsList);
                return unlockBean;
            }else if(wxUsing!=null){
                logger.debug("昨天已使用，午休时间开锁，则需要更新订单到期时");
                wxUsing.setEndTime(DateUtil.getTimesMorning() + midday.getStopTime());
                wxUsingService.update(wxUsing);
            }
        }
        if(wxUsing==null && !isFree) {
            unlockBean.setPayState(1);
            unlockBean.setGoods(wxGoodsService.findListExcludeType(WxGoods.EXCLUDE_MIDDAY, arr[2], arr[3], arr[4]));
        }else if(!isMidday && !arr[0].equals(wxUsing.getOpenId())){
            unlockBean.setPayState(3);
            unlockBean.setInfo("该设备已被别人使用");
        }else{
            if(thirdUnlock(arr[1])){
                unlockBean.setPayState(2);
            }else{
                unlockBean.setPayState(4);
                unlockBean.setInfo("远程调用开锁失败");
            }
        }
        return unlockBean;
    }

    @Override
    public UptimeBean uptime(String[] arr) {
        return new UptimeBean(uptimeCache.get(StringUtil.toLink(WxRelation.TYPE_UPTIME, arr[2], arr[3], arr[4]))
                , uptimeCache.get(StringUtil.toLink(WxRelation.TYPE_MIDDAY, arr[2], arr[3], arr[4])));
    }


    /**
     * 调用第三方开锁
     * @param did 锁ID
     */
    public boolean thirdUnlock(String did) {
        logger.info("调用开锁-->{}", did);
        String result = moduleLockService.deviceUnlock(did);
        if(result!=null){
            JsonObject json = new JsonParser().parse(result).getAsJsonObject();
            return json.has("code") && json.get("code").getAsInt() == 200;
        }

        return false;
    }


    /**
     * 生成新的Code
     * @param openId 微信开发ID
     * @param did    设备业务ID
     * @param aid    代理商ID
     * @param hid    医院ID
     * @param oid    科室ID
     */
    @Override
    public String generateCode(String openId, String did, String aid, String hid, String oid) {
        String code = did + SPLIT + aid + SPLIT + hid + SPLIT + oid + SPLIT + StringUtil.getRandomString(2);
        try {
            return AESUtil.aesEncrypt(code, openId);
        } catch (Exception e) {
            logger.warn("Generate Code error");
        }
        return null;
    }

    /**
     *  解析验证Code, 0 openId 1 did 2 aid 3 hid 4 0id
     */
    @Override
    public String[] parseCode(String sessionThirdKey, String code) {
        String openId = sessionService.getOpenId(sessionThirdKey);
        try {
            String content = AESUtil.aesDecrypt(code, openId);
            String[] arr = (openId+ SPLIT + content).split(SPLIT);
            if(arr.length >= 4) return arr;
        } catch (Exception e) {
            logger.warn("Parse Code error");
        }
        return null;
    }

    @Override
    public void notify(String bid, Integer lockStatus) {
        WxUsing wxUsing = wxUsingService.findUsingByBid(bid, System.currentTimeMillis()/1000);
        if(wxUsing!=null){
            wxUsingService.updateUsingStatus(wxUsing, String.valueOf(lockStatus));
        }else{
            logger.info("notify 此设备未使用");
        }
    }

    @Override
    public QueryBean query(String sessionThirdKey, String did, String code, boolean isSync) throws TokenException {
        String[] arr = parseCode(sessionThirdKey, code);
        if (arr == null) throw new TokenException();
        QueryBean queryBean = new QueryBean();
        WxUsing wxUsing = wxUsingService.findUsingByDid(did, System.currentTimeMillis()/1000
                , getCount(sessionThirdKey)%3==2 || isSync);
        if(wxUsing!=null) {
            queryBean.setWxUsing(wxUsing);
        }else{
            queryBean.setDid(did);
            queryBean.setUsing("2".equals(moduleLockService.getStatus(did)));
            int currTime =  DateUtil.getTimesNoDate();
            WxUptime midday = uptimeCache.get(StringUtil.toLink(WxRelation.TYPE_MIDDAY, arr[2], arr[3], arr[4]));
            if(midday != null && currTime > midday.getStartTime() && currTime < midday.getStopTime()){
                queryBean.setMidday(true);
                queryBean.setPayTime(DateUtil.getTimesMorning() + midday.getStartTime());
                queryBean.setEndTime(DateUtil.getTimesMorning() + midday.getStopTime());
            }
        }
        return queryBean;
    }

    /**
     * 根据sessionThirdKey 缓存计数
     */
    private long getCount(String sessionThirdKey) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(sessionThirdKey
                , redisTemplate.getRequiredConnectionFactory());
        entityIdCounter.expire(2, TimeUnit.MINUTES);
        return entityIdCounter.getAndIncrement();
    }
}
