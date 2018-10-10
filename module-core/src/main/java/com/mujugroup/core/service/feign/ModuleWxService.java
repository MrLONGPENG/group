package com.mujugroup.core.service.feign;


import com.mujugroup.core.model.Device;
import com.mujugroup.core.service.feign.error.ModuleWxServiceError;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Component(value = "moduleWxService")
@FeignClient(value = "module-wx", fallback = ModuleWxServiceError.class)
public interface ModuleWxService {

    @RequestMapping(value = "/merge/getPayCount", method = RequestMethod.POST)
    Map<String, String> getPayCount(@RequestParam(value = "param") String param);

    @RequestMapping(value = "/merge/getPaymentInfo", method = RequestMethod.POST)
    Map<String, String> getPaymentInfo(@RequestParam(value = "param") String param);

    @RequestMapping(value = "/uptime/query", method = RequestMethod.POST)
    String queryUptime(@RequestParam(name = "type") int type, @RequestParam(name = "key") int key
            , @RequestParam(name = "kid") int kid);

    @RequestMapping(value = "/feign/getCountByUsingDid", method = RequestMethod.GET)
   int  getCountByUsingDid(@RequestParam(value = "did") String did,@RequestParam(value = "time") long time);
}
