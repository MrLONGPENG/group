package com.mujugroup.data.service.feign;

import com.lveqia.cloud.common.objeck.to.LockTo;
import com.mujugroup.data.service.feign.error.ModuleLockServiceError;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component(value = "moduleLockService")
@FeignClient(value = "module-lock", fallback = ModuleLockServiceError.class)
public interface ModuleLockService {
    @RequestMapping(value = "/feign/getLockInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    LockTo getLockInfo(@RequestParam(value = "bid") String bid);

    @RequestMapping(value = "/feign/getFailNameByDid", method = RequestMethod.POST)
    List<String> getFailNameByDid(@RequestParam(value = "did") String did);
}
