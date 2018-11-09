package com.mujugroup.lock.objeck.bo.fail;

import com.github.wxiaoqi.merge.annonation.MergeField;
import com.lveqia.cloud.common.config.Constant;
import com.mujugroup.lock.service.feign.ModuleCoreService;
import com.mujugroup.lock.service.feign.ModuleWxService;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FailBo implements Serializable {

    private String did;
    private String name;
    private Integer status;
    private Integer battery;
    private Integer electric;
    private Date lastRefresh;

    @MergeField(feign = ModuleCoreService.class, method = "getDepartmentById"
            , isValueNeedMerge = true, defaultValue = Constant.STRING_UNKNOWN)
    private String oid;

    @MergeField(feign = ModuleCoreService.class, method = "getBedInfoByDid"
            , isValueNeedMerge = true, defaultValue = Constant.STRING_UNKNOWN)
    private String bed;

    @MergeField(feign = ModuleWxService.class, method = "getOrderEndTimeByDid"
            , isValueNeedMerge = true, defaultValue = Constant.STRING_UNKNOWN)
    private String endTime;

}
