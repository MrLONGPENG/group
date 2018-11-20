package com.mujugroup.lock.objeck.vo.record;

import com.lveqia.cloud.common.objeck.vo.PageVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "ListVo", description = "获取记录列表的Vo对象")
@EqualsAndHashCode(callSuper = true)
@Data
public class ListVo extends PageVo implements Serializable {

    private Integer id;


    private String did;


    private String lockId;


    private Integer csq;


    private Integer temp;


    private Integer charge = 0;


    private Integer voltage;


    private Integer electric;


    private Integer batteryStat;

    /**
     * 锁状态 1:关 2:开 (助力车 3:中间态 ) 4代表开锁机械故障、5代表关锁机械故障，6代表锁端本地时间不在限制时间范围内
     * 表字段 : t_lock_record.lock_status
     */
    private Integer lockStatus;

    /**
     * 最后上报时间
     * 表字段 : t_lock_record.last_refresh
     */

    private Date lastRefresh;

    /**
     * 创建时间
     * 表字段 : t_lock_record.crtTime
     */
    private Date crtTime;

    @ApiModelProperty(value = "startTime", notes = "开始时间")
    private String startTime;
    @ApiModelProperty(value = "endTime", notes = "结束时间")
    private String endTime;
    private String chargeStart;
    private String chargeEnd;
}
