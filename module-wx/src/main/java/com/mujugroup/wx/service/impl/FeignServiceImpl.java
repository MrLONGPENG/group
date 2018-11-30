package com.mujugroup.wx.service.impl;

import com.github.pagehelper.Constant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lveqia.cloud.common.objeck.to.*;
import com.mujugroup.wx.model.WxOrder;
import com.mujugroup.wx.model.WxRelation;
import com.mujugroup.wx.model.WxUptime;
import com.mujugroup.wx.model.WxUsing;
import com.mujugroup.wx.service.*;
import ma.glasnost.orika.MapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("feignService")
public class FeignServiceImpl implements FeignService {

    private final MapperFactory mapperFactory;
    private final WxOrderService wxOrderService;
    private final WxUsingService wxUsingService;
    private final WxUptimeService wxUptimeService;
    private final UsingApiService usingApiService;

    private final Logger logger = LoggerFactory.getLogger(FeignServiceImpl.class);


    public FeignServiceImpl(MapperFactory mapperFactory, WxOrderService wxOrderService
            , WxUsingService wxUsingService, WxUptimeService wxUptimeService, UsingApiService usingApiService) {
        this.mapperFactory = mapperFactory;
        this.wxOrderService = wxOrderService;
        this.wxUsingService = wxUsingService;
        this.wxUptimeService = wxUptimeService;
        this.usingApiService = usingApiService;
    }

    @Override
    public PageTo<OrderTo> getOrderList(RequestTo dto) { // 分页查询
        PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
        List<WxOrder> list = wxOrderService.findList(dto);
        mapperFactory.classMap(WxOrder.class, OrderTo.class).byDefault().register();
        return new PageTo<>(PageInfo.of(list), mapperFactory.getMapperFacade().mapAsList(list, OrderTo.class));
    }

    @Override
    public int getCountByUsingDid(String did, long time) {
        return wxUsingService.getCountByUsingDid(did, time);
    }

    @Override
    public UptimeTo getUptimeTo(int aid, int hid, int oid) {
        UptimeTo uptimeTo=new UptimeTo();
        int[] ints = new int[]{0, aid, hid, oid};
        WxUptime midday = wxUptimeService.findByXid(ints, WxRelation.TYPE_MIDDAY, true);
        uptimeTo.setNoonStartTime(midday.getStartTime());
        uptimeTo.setNoonStopTime(midday.getStopTime());
        WxUptime uptime = wxUptimeService.findByXid(ints, WxRelation.TYPE_UPTIME, true);
        uptimeTo.setStartTime(uptime.getStartTime());
        uptimeTo.setStopTime(uptime.getStopTime());
        return uptimeTo;
    }

    @Override
    public String usingNotify(String did, int lockStatus) {
        WxUsing wxUsing = wxUsingService.findUsingByDid(did, System.currentTimeMillis()/1000 , false);
        if(wxUsing!=null){
            wxUsingService.updateUsingStatus(wxUsing, String.valueOf(lockStatus));
        }
        logger.info("usingNotify did:{} lockStatus:{} wxUsing:{}", did, lockStatus, wxUsing);
        return null;
    }

    @Override
    public PayInfoTo getPayInfoByDid(String did, int orderType) {
        return wxOrderService.getPayInfoByDid(did,orderType);
    }
}
