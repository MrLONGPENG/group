package com.mujugroup.data.service.feign;


import com.mujugroup.data.service.feign.error.ModuleCoreServiceError;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

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

    /** 根据HID获取医院所属省份 */
    @RequestMapping(value = "/merge/getProvinceByHid",method = RequestMethod.POST)
    Map<String, String> getProvinceByHid(@RequestParam(value = "param") String param);

    /** 根据HID获取医院所属城市 */
    @RequestMapping(value = "/merge/getCityByHid",method = RequestMethod.POST)
    Map<String, String> getCityByHid(@RequestParam(value = "param") String param);

    /** 根据OID获取科室名字 */
    @RequestMapping(value = "/merge/getDepartmentById",method = RequestMethod.POST)
    Map<String, String> getDepartmentById(@RequestParam(value = "param") String param);


    /** 根据DID获取床位信息 */
    @RequestMapping(value = "/merge/getBedInfoByDid",method = RequestMethod.POST)
    Map<String, String> getBedInfoByDid(@RequestParam(value = "param") String param);


    /** 根据AID获取使属医院的ID与名字 */
    @RequestMapping(value = "/feign/getHospitalByAid",method = RequestMethod.POST)
    Map<Integer, String> getHospitalByAid(@RequestParam(value = "aid") int aid);


    /** 根据AID获取使属医院的ID与名字 */
    @RequestMapping(value = "/feign/getHospitalByRegion",method = RequestMethod.POST)
    Set<Integer> getHospitalByRegion(@RequestParam(value = "pid") int pid, @RequestParam(value = "cid")int cid);

}
