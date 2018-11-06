package com.mujugroup.wx.service.feign;


import com.lveqia.cloud.common.objeck.to.InfoTo;
import com.mujugroup.wx.service.feign.error.ModuleCoreServiceError;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;

@Component(value = "moduleCoreService")
@FeignClient(value = "module-core", fallback = ModuleCoreServiceError.class)
public interface ModuleCoreService {

    @RequestMapping(value = "/feign/getDeviceInfo", method = RequestMethod.POST
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    InfoTo getDeviceInfo(@RequestParam(value = "did") String did, @RequestParam(value = "bid") String bid);

    @RequestMapping(value = "/merge/getTotalActiveCount", method = RequestMethod.POST)
    Map<String, String> getTotalActiveCount(@RequestParam(value = "param") String param);

    @RequestMapping(value = "/feign/findOidByHid", method = RequestMethod.POST)
    Map<Integer, String> findOidByHid(@RequestParam(value = "hid") String hid);

    @RequestMapping(value = "/feign/findName", method = RequestMethod.POST)
    String getHospitalName(@RequestParam(value = "id") Integer id);
}
