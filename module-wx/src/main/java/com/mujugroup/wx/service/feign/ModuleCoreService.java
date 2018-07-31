package com.mujugroup.wx.service.feign;


import com.mujugroup.wx.service.feign.error.ModuleCoreServiceError;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Component(value ="moduleCoreService")
@FeignClient(value = "module-core" ,fallback = ModuleCoreServiceError.class)
public interface ModuleCoreService {

    @RequestMapping(value = "/device/query",method = RequestMethod.POST)
    String deviceQuery(@RequestParam(value = "did") String did);



    @RequestMapping(value = "/device/list",method = RequestMethod.POST)
    String deviceList(@RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "pageSize") int pageSize
            , @RequestParam(value = "status") int status);
}
