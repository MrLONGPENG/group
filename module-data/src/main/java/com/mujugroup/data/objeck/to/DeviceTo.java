package com.mujugroup.data.objeck.to;

import com.lveqia.cloud.common.objeck.to.InfoTo;
import com.lveqia.cloud.common.objeck.to.LockTo;
import com.lveqia.cloud.common.objeck.to.PayInfoTo;
import lombok.Data;

import java.io.Serializable;

@Data
public class DeviceTo implements Serializable {
    //订单信息
    private PayInfoTo payInfoTo;
    //锁信息
    private LockTo lockTo;
    //设备信息
    private InfoTo infoTo;
}
