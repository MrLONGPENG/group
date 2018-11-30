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


    @RequestMapping(value = "/feign/unlock",method = RequestMethod.POST)
    String unlock(@RequestParam(value = "did") String did);


    @RequestMapping(value = "/feign/getLockStatus",method = RequestMethod.POST)
    String getLockStatus(@RequestParam(value = "did") String did);




}
