package com.lveqia.cloud.zuul.service.feign;

import com.lveqia.cloud.zuul.service.feign.error.ModuleCoreServiceError;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Component(value ="moduleCoreService")
@FeignClient(value = "module-core" ,fallback = ModuleCoreServiceError.class)
public interface ModuleCoreService {
    @RequestMapping(value = "/feign/addAuthData",method = RequestMethod.POST)
    int addAuthData(@RequestParam(value = "uid") int uid, @RequestParam(value = "authDatas") String[] authDatas);
}
