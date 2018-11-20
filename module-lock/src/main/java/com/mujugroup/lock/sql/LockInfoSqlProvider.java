package com.mujugroup.lock.sql;

import com.lveqia.cloud.common.config.Constant;
import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.lock.model.LockInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * 锁设备信息表,SQL语句组装类
 * 类名:LockInfoSqlProvider
 * 创建人:leolaurel
 * 创建时间:20180623
 */
public class LockInfoSqlProvider {

    public String insert(LockInfo lockInfo) {
        return new SQL() {{
            INSERT_INTO("t_lock_info");
            if (lockInfo.getId() != null) VALUES("id", "#{id}");
            if (lockInfo.getLockId() != null) VALUES("lock_id", "#{lockId}");
            if (lockInfo.getBrand() != null) VALUES("brand", "#{brand}");
            if (lockInfo.getMac() != null) VALUES("mac", "#{mac}");
            if (lockInfo.getKey() != null) VALUES("key", "#{key}");
            if (lockInfo.getSimId() != null) VALUES("sim_id", "#{simId}");
            if (lockInfo.getFVersion() != null) VALUES("f_version", "#{fVersion}");
            if (lockInfo.getHVersion() != null) VALUES("h_version", "#{hVersion}");
            if (lockInfo.getLongitude() != null) VALUES("longitude", "#{longitude}");
            if (lockInfo.getLatitude() != null) VALUES("latitude", "#{latitude}");
            if (lockInfo.getCsq() != null) VALUES("csq", "#{csq}");
            if (lockInfo.getTemp() != null) VALUES("temp", "#{temp}");
            if (lockInfo.getCharge() != null) VALUES("charge", "#{charge}");
            if (lockInfo.getVoltage() != null) VALUES("voltage", "#{voltage}");
            if (lockInfo.getElectric() != null) VALUES("electric", "#{electric}");
            if (lockInfo.getUpgrade() != null) VALUES("upgrade", "#{upgrade}");
            if (lockInfo.getBatteryStat() != null) VALUES("battery_stat", "#{batteryStat}");
            if (lockInfo.getLockStatus() != null) VALUES("lock_status", "#{lockStatus}");
            if (lockInfo.getLastRefresh() != null) VALUES("last_refresh", "#{lastRefresh}");
        }}.toString();
    }


    public String update(LockInfo lockInfo) {
        return new SQL() {{
            UPDATE("t_lock_info");
            if (lockInfo.getId() != null) SET("id = #{id}");
            if (lockInfo.getLockId() != null) SET("lock_id = #{lockId}");
            if (lockInfo.getBrand() != null) SET("brand = #{brand}");
            if (lockInfo.getMac() != null) SET("mac = #{mac}");
            if (lockInfo.getKey() != null) SET("key = #{key}");
            if (lockInfo.getSimId() != null) SET("sim_id = #{simId}");
            if (lockInfo.getFVersion() != null) SET("f_version = #{fVersion}");
            if (lockInfo.getHVersion() != null) SET("h_version = #{hVersion}");
            if (lockInfo.getLongitude() != null) SET("longitude = #{longitude}");
            if (lockInfo.getLatitude() != null) SET("latitude = #{latitude}");
            if (lockInfo.getCsq() != null) SET("csq = #{csq}");
            if (lockInfo.getTemp() != null) SET("temp = #{temp}");
            if (lockInfo.getCharge() != null) SET("charge = #{charge}");
            if (lockInfo.getVoltage() != null) SET("voltage = #{voltage}");
            if (lockInfo.getElectric() != null) SET("electric = #{electric}");
            if (lockInfo.getUpgrade() != null) SET("upgrade = #{upgrade}");
            if (lockInfo.getBatteryStat() != null) SET("battery_stat = #{batteryStat}");
            if (lockInfo.getLockStatus() != null) SET("lock_status = #{lockStatus}");
            if (lockInfo.getLastRefresh() != null) SET("last_refresh = #{lastRefresh}");
            WHERE("id = #{id}");
        }}.toString();
    }

    public String getInfoList(@Param(value = "did") String did, @Param(value = "bid") String bid, @Param(value = "fVersion") String fVersion
            , @Param(value = "hVersion") String hVersion, @Param(value = "batteryStatStart") String batteryStatStart
            , @Param(value = "batteryStatEnd") String batteryStatEnd, @Param(value = "csqStart") String csqStart
            , @Param(value = "csqEnd") String csqEnd
            , @Param(value = "lockStatus") int lockStatus, int elecStatus
            , int lineStatus) {
        return new SQL() {{
            SELECT(" i.*,f.did, CASE  WHEN TIMESTAMPDIFF(SECOND,i.last_refresh,NOW()) >=1800 THEN '离线'   WHEN  TIMESTAMPDIFF(SECOND,i.last_refresh,NOW()) <1800 THEN '在线' ELSE NULL END ");
            FROM("t_lock_info i,t_lock_did f");
            WHERE("i.lock_id=f.lock_id");
            if (!StringUtil.isEmpty(did)) {
                AND().WHERE("f.did= #{did}");
            }
            if (!StringUtil.isEmpty(bid)) {
                AND().WHERE("i.lock_id= #{bid}");
            }
            if (!StringUtil.isEmpty(fVersion)) {
                AND().WHERE("i.f_version= #{fVersion}");
            }
            if (!StringUtil.isEmpty(hVersion)) {
                AND().WHERE("i.h_version= #{hVersion}");
            }
            if ((!StringUtil.isEmpty(batteryStatStart) && !Constant.DIGIT_ZERO.equals(batteryStatStart))
                    && (!StringUtil.isEmpty(batteryStatEnd) && !Constant.DIGIT_ZERO.equals(batteryStatEnd))) {
                AND().WHERE("i.battery_stat>= #{batteryStatStart} AND i.battery_stat <= #{batteryStatEnd}");
            }
            if (!StringUtil.isEmpty(csqStart)) {

                AND().WHERE("i.csq>= #{csqStart}");
            }
            if (!StringUtil.isEmpty(csqEnd)) {
                AND().WHERE(" i.csq <= #{csqEnd}");
            }
            if (lockStatus != 0) {
                AND().WHERE("i.lock_status = #{lockStatus}");
            }
            if (elecStatus == 1) {
                AND().WHERE("i.electric= 1");
            } else if (elecStatus == 0) {
                AND().WHERE("i.electric= 0");
            }
            //'离线状态(当前时间减去最近一次上传时间的时间差大于等于1800秒)
            if (lineStatus == 0) {
                AND().WHERE("TIMESTAMPDIFF(SECOND,i.last_refresh,NOW()) >=1800");
            }
            //在线状态
            if (lineStatus == 1) {
                AND().WHERE("TIMESTAMPDIFF(SECOND,i.last_refresh,NOW()) <1800");
            }
            ORDER_BY("`id` DESC");
        }}.toString();
    }
}
