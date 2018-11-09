package com.mujugroup.lock.objeck.vo.fail;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FailVo implements Serializable {

    private String did;
    private String name;
    private String status;
    private String battery;
    private String electric;
    private String lastRefresh;
    private String department;
    private String bed;
    private String endTime;


}
