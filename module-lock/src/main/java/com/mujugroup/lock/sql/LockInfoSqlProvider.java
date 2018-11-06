package com.mujugroup.lock.sql;

import com.mujugroup.lock.model.LockInfo;
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
            if (lockInfo.getLockId() != null) VALUES("lock_id", "#{bid}");
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
            if (lockInfo.getLockId() != null) SET("lock_id = #{bid}");
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
}
