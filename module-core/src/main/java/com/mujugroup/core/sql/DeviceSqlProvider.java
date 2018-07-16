package com.mujugroup.core.sql;

import com.mujugroup.core.model.Device;
import org.apache.ibatis.jdbc.SQL;

/**
 * 设备关联表,SQL语句组装类
 * 类名:DeviceSqlProvider
 * 创建人:LEOLAUREL
 * 创建时间:20180627
 */
public class DeviceSqlProvider {

    public String insert(Device device){
        return new SQL(){{
            INSERT_INTO("t_device");
            if(device.getId()!= null) VALUES("id", "#{id}");
            if(device.getMac()!= null) VALUES("mac", "#{mac}");
            if(device.getAgentId()!= null) VALUES("agentId", "#{agentId}");
            if(device.getHospitalId()!= null) VALUES("hospitalId", "#{hospitalId}");
            if(device.getHospitalBed()!= null) VALUES("hospitalBed", "#{hospitalBed}");
            if(device.getCrtTime()!= null) VALUES("crtTime", "#{crtTime}");
            if(device.getCrtId()!= null) VALUES("crtId", "#{crtId}");
            if(device.getStatus()!= null) VALUES("status", "#{status}");
            if(device.getUseflag()!= null) VALUES("useflag", "#{useflag}");
            if(device.getReserveDate()!= null) VALUES("reserve_date", "#{reserveDate}");
            if(device.getImgUrl()!= null) VALUES("imgUrl", "#{imgUrl}");
            if(device.getRemark()!= null) VALUES("remark", "#{remark}");
            if(device.getDepart()!= null) VALUES("depart", "#{depart}");
            if(device.getCode()!= null) VALUES("code", "#{code}");
            if(device.getPay()!= null) VALUES("pay", "#{pay}");
            if(device.getRun()!= null) VALUES("run", "#{run}");
            if(device.getStationId()!= null) VALUES("station_id", "#{stationId}");
            if(device.getIsStation()!= null) VALUES("is_station", "#{isStation}");
            if(device.getUpdateId()!= null) VALUES("update_id", "#{updateId}");
            if(device.getUpdateTime()!= null) VALUES("update_time", "#{updateTime}");
            if(device.getIssync()!= null) VALUES("issync", "#{issync}");
        }}.toString();
    }



    public String update(Device device){
        return new SQL(){{
            UPDATE("t_device");
            if(device.getId()!= null) SET("id = #{id}");
            if(device.getMac()!= null) SET("mac = #{mac}");
            if(device.getAgentId()!= null) SET("agentId = #{agentId}");
            if(device.getHospitalId()!= null) SET("hospitalId = #{hospitalId}");
            if(device.getHospitalBed()!= null) SET("hospitalBed = #{hospitalBed}");
            if(device.getCrtTime()!= null) SET("crtTime = #{crtTime}");
            if(device.getCrtId()!= null) SET("crtId = #{crtId}");
            if(device.getStatus()!= null) SET("status = #{status}");
            if(device.getUseflag()!= null) SET("useflag = #{useflag}");
            if(device.getReserveDate()!= null) SET("reserve_date = #{reserveDate}");
            if(device.getImgUrl()!= null) SET("imgUrl = #{imgUrl}");
            if(device.getRemark()!= null) SET("remark = #{remark}");
            if(device.getDepart()!= null) SET("depart = #{depart}");
            if(device.getCode()!= null) SET("code = #{code}");
            if(device.getPay()!= null) SET("pay = #{pay}");
            if(device.getRun()!= null) SET("run = #{run}");
            if(device.getStationId()!= null) SET("station_id = #{stationId}");
            if(device.getIsStation()!= null) SET("is_station = #{isStation}");
            if(device.getUpdateId()!= null) SET("update_id = #{updateId}");
            if(device.getUpdateTime()!= null) SET("update_time = #{updateTime}");
            if(device.getIssync()!= null) SET("issync = #{issync}");
            WHERE("id = #{id}");
        }}.toString();
    }
}
