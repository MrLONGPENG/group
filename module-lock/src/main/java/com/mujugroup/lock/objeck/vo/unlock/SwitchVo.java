package com.mujugroup.lock.objeck.vo.unlock;

import lombok.Data;


@Data
public class SwitchVo {

    private String did;
    private String bid;
    private String lockStatus;
    private String localTime;
    private String receiveTime;

}
