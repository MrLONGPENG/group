package com.mujugroup.data.service.feign;


import com.lveqia.cloud.common.to.AidHidOidTO;
import com.lveqia.cloud.common.to.OrderTO;
import com.lveqia.cloud.common.to.PageTO;
import com.mujugroup.data.service.feign.error.ModuleWxServiceError;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Component(value ="moduleWxService")
@FeignClient(value = "module-wx" ,fallback = ModuleWxServiceError.class)
public interface ModuleWxService {

    @RequestMapping(value = "/merge/getUserCount",method = RequestMethod.POST)
    Map<String, String> getUserCount(@RequestParam(value = "param") String param);

    @RequestMapping(value = "/merge/getUsageCount",method = RequestMethod.POST)
    Map<String, String> getUsageCount(@RequestParam(value = "param") String param);

    @RequestMapping(value = "/merge/getUsageRate",method = RequestMethod.POST)
    Map<String, String> getUsageRate(@RequestParam(value = "param") String param);

    /**
     * 获取订单类型：晚修；午休
     * @param param gid
     */
    @RequestMapping(value = "/merge/getOrderTypeById",method = RequestMethod.POST)
    Map<String, String> getOrderTypeById(@RequestParam(value = "param") String param);


    @ResponseBody
    @RequestMapping(value = "/feign/getOrderList", method = RequestMethod.POST
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    PageTO<OrderTO> getOrderList(@RequestBody AidHidOidTO aidHidOidDto);
}
