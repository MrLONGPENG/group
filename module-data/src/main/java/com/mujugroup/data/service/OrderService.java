package com.mujugroup.data.service;


import com.lveqia.cloud.common.objeck.to.OrderTo;
import com.mujugroup.data.objeck.bo.OrderBo;

import java.util.List;

public interface OrderService {

    List<OrderBo> mergeOrderBO(List<OrderTo> list);

}
