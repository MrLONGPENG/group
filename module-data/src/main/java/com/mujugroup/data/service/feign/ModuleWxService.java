package com.mujugroup.data.service.feign;


import com.lveqia.cloud.common.objeck.to.AidHidOidTO;
import com.lveqia.cloud.common.objeck.to.OrderTO;
import com.lveqia.cloud.common.objeck.to.PageTO;
import com.mujugroup.data.service.feign.error.ModuleWxServiceError;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Component(value ="moduleWxService")
@FeignClient(value = "module-wx" ,fallback = ModuleWxServiceError.class)
public interface ModuleWxService {


    /**
     * 获取指定时间范围内总用户数
     * @param param start,end  开始与结束时间戳
     * @return key:start,end value:count
     */
    @RequestMapping(value = "/merge/getUserCount",method = RequestMethod.POST)
    Map<String, String> getUserCount(@RequestParam(value = "param") String param);


    /**
     * 根据条件获取指定时间类的使用数量
     * @param param 代理商ID,医院ID,科室ID,开始时间戳,结束时间戳,日期字符 (ps:日期字符可能为空，多个数据分号分割)
     * @return  key:aid,hid,oid,start,end,date value:count
     */
    @RequestMapping(value = "/merge/getUsageCount",method = RequestMethod.POST)
    Map<String, String> getUsageCount(@RequestParam(value = "param") String param);


    /**
     * 根据条件获取指定时间类的使用率
     * @param param 代理商ID,医院ID,科室ID,日期字符 (ps:日期字符可能为空，多个数据分号分割)
     * @return  key:aid,hid,oid,date value:count
     */
    @RequestMapping(value = "/merge/getUsageRate",method = RequestMethod.POST)
    Map<String, String> getUsageRate(@RequestParam(value = "param") String param);

    /**
     * 获取指定时间内、指定条件下的利润总和
     * @param param aid,hid,oid,start,end
     * @return key:aid,hid,oid,start,end value:profit(单位分)
     */
    @RequestMapping(value = "/merge/getTotalProfit",method = RequestMethod.POST)
    Map<String, String> getTotalProfit(@RequestParam(value = "param") String param);

    /**
     * 获取订单类型：晚修；午休
     * @param param gid
     */
    @RequestMapping(value = "/merge/getOrderTypeById",method = RequestMethod.POST)
    Map<String, String> getOrderTypeById(@RequestParam(value = "param") String param);


    @RequestMapping(value = "/feign/getOrderList", method = RequestMethod.POST
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    PageTO<OrderTO> getOrderList(@RequestBody AidHidOidTO aidHidOidDto);
}
