package com.mujugroup.data.service.impl;

import com.github.wxiaoqi.merge.annonation.MergeResult;
import com.lveqia.cloud.common.objeck.to.OrderTO;
import com.mujugroup.data.objeck.bo.OrderBO;
import com.mujugroup.data.service.OrderService;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("orderService")
public class OrderServiceImpl implements OrderService {

    private final MapperFactory mapperFactory;

    @Autowired
    public OrderServiceImpl(MapperFactory mapperFactory) {
        this.mapperFactory = mapperFactory;
    }


    /**
     * 传输对象转换成业务对象，且数据聚合相关属性
     */
    @Override
    @MergeResult
    public List<OrderBO> mergeOrderBO(List<OrderTO> list) {
        mapperFactory.classMap(OrderTO.class, OrderBO.class)
                .field("aid","agent")
                .field("hid","hospital")
                .field("oid","department")
                .field("did","bedInfo")
                .field("did","did")
                .fieldMap("payTime").converter("timestampConvert").add()
                .fieldMap("payPrice").converter("rmbPriceConvert").add()
                .fieldMap("orderType").converter("orderTypeConvert").add()
                .byDefault().register();
        return mapperFactory.getMapperFacade().mapAsList(list, OrderBO.class);
    }
}
