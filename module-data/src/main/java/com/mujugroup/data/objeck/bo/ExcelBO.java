package com.mujugroup.data.objeck.bo;

import com.github.wxiaoqi.merge.annonation.MergeField;
import com.lveqia.cloud.common.StringUtil;
import com.lveqia.cloud.common.util.Constant;
import com.mujugroup.data.service.feign.ModuleCoreService;
import com.mujugroup.data.service.feign.ModuleWxService;

import java.io.Serializable;

/**
 * Business Object
 * 表格导出数据
 * 总激活数、总用户数、昨日使用数以及使用率
 */
public class ExcelBO implements Serializable {

    private String refDate;

    private String hospital;

    @MergeField(feign = ModuleCoreService.class, method = "getAgentById"
            , isValueNeedMerge = true, defaultValue = Constant.STRING_UNKNOWN)
    private String agent;

    @MergeField(feign = ModuleCoreService.class, method = "getProvinceByHid"
            , isValueNeedMerge = true, defaultValue = Constant.STRING_UNKNOWN)
    private String province;

    @MergeField(feign = ModuleCoreService.class, method = "getCityByHid"
            , isValueNeedMerge = true, defaultValue = Constant.STRING_UNKNOWN)
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

    public ExcelBO(String name, String refDate, int aid, int hid, long end) {
        this.refDate = refDate;
        this.agent = String.valueOf(aid);
        this.hospital = name;
        this.province = String.valueOf(hid);
        this.city = String.valueOf(hid);
        // 拼接总激活数 ｛AID,HID,OID,,结束时间戳｝
        this.active = StringUtil.toLinkByComma(aid, hid, Constant.DIGIT_ZERO, end);
        this.usage = StringUtil.toLinkByComma(aid, hid, 0, 0, 0, refDate);
        this.usageRate = StringUtil.toLinkByComma(aid, hid, 0, refDate);
        this.profit = StringUtil.toLinkByComma(aid, hid, 0, 0, 0, refDate);

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
