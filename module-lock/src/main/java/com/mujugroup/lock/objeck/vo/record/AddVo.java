package com.mujugroup.lock.objeck.vo.record;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AddVo implements Serializable {
    private Integer id;
    private Long did;
    private Long lockId;
    private Date receiveTime;
    private Integer lockStatus;
    private Date localTime;
}
