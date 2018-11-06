package com.mujugroup.core.objeck.bo;

import com.github.wxiaoqi.merge.annonation.MergeField;
import com.lveqia.cloud.common.config.Constant;
import com.mujugroup.core.service.MergeService;
import com.mujugroup.core.service.feign.LveqiaCloudZuulService;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DeviceBo implements Serializable {

    @MergeField(feign = LveqiaCloudZuulService.class, method = "getNameByUid"
            , isValueNeedMerge = true, defaultValue = Constant.STRING_UNKNOWN)
    private String name;
    private Integer id;
    private String did;
    private Integer agentId;
    private Integer hospitalId;
    private String hospitalBed;
    private Date crtTime;
    private Integer crtId;
    private Integer status;
    private Integer useflag;
    private String remark;
    private Integer depart;
    private String bid;
    private Integer bell;
    private Integer run;
    private Integer updateId;
    private Date updateTime;
    private Integer issync;
    @MergeField(feign = MergeService.class, method = "getAgentById", isValueNeedMerge = true
            , defaultValue = Constant.STRING_UNKNOWN)
    private String agentName;
    @MergeField(feign = MergeService.class, method = "getHospitalById", isValueNeedMerge = true
            , defaultValue = Constant.STRING_UNKNOWN)
    private String hospitalName;
    @MergeField(feign = MergeService.class, method = "getDepartmentById", isValueNeedMerge = true
            , defaultValue = Constant.STRING_UNKNOWN)
    private String departmentName;

}
