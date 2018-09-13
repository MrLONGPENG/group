package com.mujugroup.data.service;


import com.lveqia.cloud.common.objeck.to.OrderTO;
import com.mujugroup.data.objeck.bo.OrderBO;

import java.util.List;

public interface OrderService {

    List<OrderBO> mergeOrderBO(List<OrderTO> list);

}
