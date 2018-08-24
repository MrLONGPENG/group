package com.mujugroup.wx.service.impl;

import com.lveqia.cloud.common.dto.AidHidOidDto;
import com.lveqia.cloud.common.dto.OrderDto;
import com.mujugroup.wx.model.WxOrder;
import com.mujugroup.wx.service.FeignService;
import com.mujugroup.wx.service.WxOrderService;
import ma.glasnost.orika.MapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("feignService")
public class FeignServiceImpl implements FeignService {

    private final MapperFactory mapperFactory;
    private final WxOrderService wxOrderService;

    private final Logger logger = LoggerFactory.getLogger(FeignServiceImpl.class);


    public FeignServiceImpl(MapperFactory mapperFactory, WxOrderService wxOrderService) {
        this.mapperFactory = mapperFactory;
        this.wxOrderService = wxOrderService;
    }

    @Override
    public List<OrderDto> getOrderList(AidHidOidDto dto) {
        mapperFactory.classMap(WxOrder.class, OrderDto.class).byDefault().register();
        return mapperFactory.getMapperFacade().mapAsList(wxOrderService.findList(dto.getAid(), dto.getHid()
                , dto.getOid(), dto.getStart(),dto.getEnd(),dto.getTradeNo()), OrderDto.class);
    }
}
