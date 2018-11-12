package com.mujugroup.data.objeck.vo;
import com.lveqia.cloud.common.objeck.vo.InfoVo;
import com.lveqia.cloud.common.objeck.vo.LockVo;
import com.lveqia.cloud.common.objeck.vo.PayInfoVo;
import lombok.Data;

import java.io.Serializable;
@Data
public class DeviceVo implements Serializable {
    //订单信息
    private PayInfoVo payInfoVo;
    //锁信息
    private LockVo lockVo;
    //设备信息
    private InfoVo infoVo;
}
