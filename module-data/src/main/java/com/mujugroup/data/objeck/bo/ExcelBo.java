package com.mujugroup.data.objeck.bo;

import com.github.wxiaoqi.merge.annonation.MergeField;
import com.google.gson.JsonObject;
import com.lveqia.cloud.common.util.StringUtil;
import com.lveqia.cloud.common.config.Constant;
import com.mujugroup.data.service.feign.ModuleCoreService;
import com.mujugroup.data.service.feign.ModuleWxService;

import java.io.Serializable;

/**
 * Business Object
 * 表格导出数据
 * 总激活数、总用户数、昨日使用数以及使用率
 */
public class ExcelBo implements Serializable {

    private String refDate;

    private String hospital;

    private String agent;

    private String province;

    private String city;

    @MergeField(feign = ModuleCoreService.class, method = "getTotalActiveCount"
            , isValueNeedMerge = true, defaultValue = Constant.DIGIT_ZERO)
    private String active;

    @MergeField(feign = ModuleWxService.class, method = "getUsageCount"
            , isValueNeedMerge = true, defaultValue = Constant.DIGIT_ZERO)
    private String usage;

    @MergeField(feign = ModuleWxService.class, method = "getUsageRate"
            , isValueNeedMerge = true, defaultValue = Constant.DIGIT_ZERO)
    private String usageRate;

    @MergeField(feign = ModuleWxService.class, method = "getTotalProfit"
            , isValueNeedMerge = true, defaultValue = Constant.DIGIT_ZERO)
    private String profit;

    public ExcelBo(String refDate, JsonObject info, long end) {
        this.refDate = refDate;
        initInfo(info); // 初始化其信息
        // 拼接总激活数 ｛AID,HID,OID,,结束时间戳｝
        String aid = info.get("aid").getAsString();
        String hid = info.get("hid").getAsString();
        this.active = StringUtil.toLinkByAnd(aid, hid, Constant.DIGIT_ZERO, end);
        this.usage = StringUtil.toLinkByAnd(aid, hid, Constant.DIGIT_ZERO, refDate);
        this.usageRate = StringUtil.toLinkByAnd(aid, hid, Constant.DIGIT_ZERO, refDate);
        this.profit = StringUtil.toLinkByAnd(aid, hid, Constant.DIGIT_ZERO, 0, 0, refDate);

    }

    /**
     * @param info [aid,hid,agent,hospital,province,city]
     */
    private void initInfo(JsonObject info) {
        this.agent = info.get("agent").getAsString();
        this.hospital = info.get("hospital").getAsString();
        this.province = info.get("province").getAsString();
        this.city = info.get("city").getAsString();

    }

    public String getRefDate() {
        return refDate;
    }

    public void setRefDate(String refDate) {
        this.refDate = refDate;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getUsageRate() {
        return usageRate;
    }

    public void setUsageRate(String usageRate) {
        this.usageRate = usageRate;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }
}
