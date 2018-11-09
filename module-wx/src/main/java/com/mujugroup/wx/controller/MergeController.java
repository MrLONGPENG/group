package com.mujugroup.wx.controller;


import com.mujugroup.wx.service.MergeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 数据聚合模块之间调用接口
 */
@RestController
@RequestMapping("/merge")
public class MergeController {

    private MergeService mergeService;

    @Autowired
    public MergeController(MergeService mergeService) {
        this.mergeService = mergeService;
    }


    /**
     * 获取最近二十四小时支付数
     *
     * @param param aid 或 aid&hid
     */
    @RequestMapping(value = "/getPayCount", method = RequestMethod.POST)
    public Map<String, String> getPayCount(@RequestParam(value = "param") String param) {
        return mergeService.getPayCount(param);
    }

    /**
     * 获取最后一次支付信息
     *
     * @param param did  多个分号分隔
     */
    @RequestMapping(value = "/getPaymentInfo", method = RequestMethod.POST)
    public Map<String, String> getPaymentInfo(@RequestParam(value = "param") String param) {
        return mergeService.getPaymentInfo(param);
    }

    /**
     * 获取最后一次订单的到期时间
     *
     * @param param did  多个分号分隔
     */
    @RequestMapping(value = "/getOrderEndTimeByDid", method = RequestMethod.POST)
    public Map<String, String> getOrderEndTimeByDid(@RequestParam(value = "param") String param) {
        return mergeService.getOrderEndTimeByDid(param);
    }

    /**
     * 根据条件获取指定时间类的使用率
     *
     * @param param 代理商ID&医院ID&科室ID&日期字符
     * @return key:aid&hid&oid&date value:count
     */
    @RequestMapping(value = "/getUsageRate", method = RequestMethod.POST)
    public Map<String, String> getUsageRate(@RequestParam(value = "param") String param) {
        return mergeService.getUsageRate(param);
    }

    /**
     * 根据条件获取指定时间类的使用数量
     *
     * @param param 代理商ID&医院ID&科室ID&日期字符(代理商、医院皆可使用多个查询，逗号分隔)
     * @return key:aid&hid&oid&start&end&date value:count
     */
    @RequestMapping(value = "/getUsageCount", method = RequestMethod.POST)
    public Map<String, String> getUsageCount(@RequestParam(value = "param") String param) {
        return mergeService.getUsageCount(param);
    }

    /**
     * 获取指定时间范围内总用户数
     *
     * @param param start,end  开始与结束时间戳
     * @return key:start,end value:count
     */
    @RequestMapping(value = "/getUserCount", method = RequestMethod.POST)
    public Map<String, String> getUserCount(@RequestParam(value = "param") String param) {
        return mergeService.getUserCount(param);
    }

    /**
     * 获取指定时间内、指定条件下的利润总和
     *
     * @param param 代理商ID&医院ID&科室ID&开始时间戳&结束时间戳&日期字符 (ps:日期字符可能为空)
     * @return key:aid&hid&oid&start&end&date value:profit(单位分)
     */
    @RequestMapping(value = "/getTotalProfit", method = RequestMethod.POST)
    public Map<String, String> getTotalProfit(@RequestParam(value = "param") String param) {
        return mergeService.getTotalProfit(param);
    }

}
