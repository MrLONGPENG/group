package com.mujugroup.wx.service.feign;


import com.mujugroup.wx.service.feign.error.ModuleLockServiceError;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Component(value ="moduleLockService")
@FeignClient(value = "module-lock" ,fallback = ModuleLockServiceError.class)
public interface ModuleLockService {

    @RequestMapping(value = "/did/bid",method = RequestMethod.POST)
    String bidToDid(@RequestParam(value = "bid") String bid);


    @RequestMapping(value = "/did/status",method = RequestMethod.POST)
    String getStatus(@RequestParam(value = "did") String did);



    @RequestMapping(value = "/device/unlock",method = RequestMethod.POST)
    String deviceUnlock(@RequestParam(value = "did") String did);
}
