package com.mujugroup.lock.objeck.vo.fail;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FailVo implements Serializable {
    private long id;
    private String did;
    private String name;
    //开关锁状态
    private String status;
    private String battery;
    private String electric;
    private String lastRefresh;
    private String department;
    private String hospital;
    private String bed;
    private String endTime;
    //故障解决状态
    private String resolveStatus;
    //查询开始时间
    private String lastRefreshStart;
    //查询结束时间
    private String lastRefreshEnd;

}
