package com.mujugroup.lock.objeck.vo.lockRecord;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AddVo implements Serializable {
    private Integer id;
    private Long did;
    private Long lockId;
    private Date time;
    private Integer lockStatus;
}
