package com.mujugroup.data.service.feign;


import com.mujugroup.data.service.feign.error.ModuleCoreServiceError;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Component(value ="moduleCoreService")
@FeignClient(value = "module-core" ,fallback = ModuleCoreServiceError.class)
public interface ModuleCoreService {

    /** 获取指定时间段内的新增激活数 */
    @RequestMapping(value = "/merge/getNewlyActiveCount",method = RequestMethod.POST)
    Map<String, String> getNewlyActiveCount(@RequestParam(value = "param") String param);

    /** 获取到指定时间的总激活数 */
    @RequestMapping(value = "/merge/getTotalActiveCount",method = RequestMethod.POST)
    Map<String, String> getTotalActiveCount(@RequestParam(value = "param") String param);

    /** 根据AID获取代理商名字 */
    @RequestMapping(value = "/merge/getAgentById",method = RequestMethod.POST)
    Map<String, String> getAgentById(@RequestParam(value = "param") String param);

    /** 根据HID获取医院名字 */
    @RequestMapping(value = "/merge/getHospitalById",method = RequestMethod.POST)
    Map<String, String> getHospitalById(@RequestParam(value = "param") String param);

    /** 根据OID获取科室名字 */
    @RequestMapping(value = "/merge/getDepartmentById",method = RequestMethod.POST)
    Map<String, String> getDepartmentById(@RequestParam(value = "param") String param);



}
