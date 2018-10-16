package com.mujugroup.core.controller;

import com.mujugroup.core.model.Department;
import com.mujugroup.core.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 服务模块之间调用接口
 */
@RestController
@RequestMapping("/feign")
public class FeignController {


    private FeignService feignService;

    @Autowired
    public FeignController(FeignService feignService) {
        this.feignService = feignService;
    }

    /**
     * 根据代理商ID获取医院ID 以及医院名称
     *
     * @param aid 代理商ID
     * @return key:hid value:name
     */
    @RequestMapping(value = "/getHospitalByAid", method = RequestMethod.POST)
    public Map<Integer, String> getHospitalByAid(@RequestParam(value = "aid") String aid) {
        return feignService.getHospitalByAid(aid);
    }

    /**
     * 根据省份ID 以及城市ID 获取代理商医院ID和代理商ID
     *
     * @param pid 省份ID cid 城市ID
     * @return set:hid
     */
    @RequestMapping(value = "/getHospitalByRegion", method = RequestMethod.POST)
    public Set<Integer> getHospitalByRegion(@RequestParam(value = "pid") String pid
            , @RequestParam(value = "cid") String cid) {
        return feignService.getHospitalByRegion(pid, cid);
    }

    /**
     * 给指定用户添加数据权限
     *
     * @param uid      用户ID
     * @param authData 数据权限, 格式 AID1,HID1或OID1
     */
    @RequestMapping(value = "/addAuthData", method = RequestMethod.POST)
    public int addAuthData(@RequestParam(value = "uid") int uid, @RequestParam(value = "authData") String[] authData) {
        return feignService.addAuthData(uid, authData);
    }

    /**
     * 给指定用户查询数据权限
     *
     * @param uid 用户ID
     * @return Map key: 1
     */
    @RequestMapping(value = "/getAuthData", method = RequestMethod.POST)
    public Map<String, String> getAuthData(@RequestParam(value = "uid") int uid) {
        return feignService.getAuthData(uid);
    }

    /**
     * 根据HID获取医院Json对象
     */
    @RequestMapping(value = "/getHospitalJson", method = RequestMethod.POST)
    public Map<String, String> getHospitalJson(@RequestParam(value = "hid") String hid) {
        return feignService.getHospitalJson(hid);
    }

    /**
     * 根据医院ID查询所属科室
     * @param hid 医院ID
     * @return
     */
    @RequestMapping(value = "/findOidByHid",method = RequestMethod.POST)
    Map<Integer,String> findOidByHid(@RequestParam(value = "hid") String hid){
        return  feignService.findOidByHid(hid);
    }
    @RequestMapping(value = "/findName")
    String getHospitalName(@RequestParam(value = "id") Integer id){
        return  feignService.getHospitalName(id);
    }
}
