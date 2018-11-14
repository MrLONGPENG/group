package com.mujugroup.lock.service.feign;

import com.lveqia.cloud.common.objeck.to.InfoTo;
import com.mujugroup.lock.service.feign.error.ModuleCoreServiceError;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Component(value = "moduleCoreService")
@FeignClient(value = "module-core", fallback = ModuleCoreServiceError.class)
public interface ModuleCoreService {
    /**
     * 根据UID获取权限相关信息
     */
    @RequestMapping(value = "/feign/getAuthData", method = RequestMethod.POST)
    Map<String, String> getAuthData(@RequestParam(value = "uid") String uid);

    /**
     * 根据OID获取科室名字
     */
    @RequestMapping(value = "/merge/getDepartmentById", method = RequestMethod.POST)
    Map<String, String> getDepartmentById(@RequestParam(value = "param") String param);


    /**
     * 根据DID获取床位信息
     */
    @RequestMapping(value = "/merge/getBedInfoByDid", method = RequestMethod.POST)
    Map<String, String> getBedInfoByDid(@RequestParam(value = "param") String param);

    /**
     * 获取激活设备的did
     *
     * @return
     */
    @RequestMapping(value = "/feign/getActivateInfoTo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<InfoTo> getActivateInfoTo(@RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "pageSize") int pageSize);

    @RequestMapping(value = "/feign/getDeviceInfo", method = RequestMethod.POST
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    InfoTo getDeviceInfo(@RequestParam(value = "did", required = false) String did
            , @RequestParam(value = "bid", required = false) String bid);


}
