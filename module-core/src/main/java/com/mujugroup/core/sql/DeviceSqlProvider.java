package com.mujugroup.core.sql;

import com.lveqia.cloud.common.util.StringUtil;
import com.lveqia.cloud.common.config.Constant;
import com.mujugroup.core.model.Device;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.io.StringReader;

/**
 * 设备关联表,SQL语句组装类
 * 类名:DeviceSqlProvider
 * 创建人:LEOLAUREL
 * 创建时间:20180627
 */
public class DeviceSqlProvider {

    public String insert(Device device) {
        return new SQL() {{
            INSERT_INTO("t_device");
            if (device.getId() != null) VALUES("id", "#{id}");
            if (device.getDid() != null) VALUES("did", "#{did}");
            if (device.getAgentId() != null) VALUES("agentId", "#{agentId}");
            if (device.getHospitalId() != null) VALUES("hospitalId", "#{hospitalId}");
            if (device.getHospitalBed() != null) VALUES("hospitalBed", "#{hospitalBed}");
            if (device.getCrtTime() != null) VALUES("crtTime", "#{crtTime}");
            if (device.getCrtId() != null) VALUES("crtId", "#{crtId}");
            if (device.getStatus() != null) VALUES("status", "#{status}");
            if (device.getUseflag() != null) VALUES("useflag", "#{useflag}");
            if (device.getRemark() != null) VALUES("remark", "#{remark}");
            if (device.getDepart() != null) VALUES("depart", "#{depart}");
            if (device.getBid() != null) VALUES("bid", "#{bid}");
            if (device.getBell() != null) VALUES("bell", "#{bell}");
            if (device.getRun() != null) VALUES("run", "#{run}");
            if (device.getUpdateId() != null) VALUES("update_id", "#{updateId}");
            if (device.getUpdateTime() != null) VALUES("update_time", "#{updateTime}");
            if (device.getIssync() != null) VALUES("issync", "#{issync}");
        }}.toString();
    }


    public String update(Device device) {
        return new SQL() {{
            UPDATE("t_device");
            if (device.getId() != null) SET("id = #{id}");
            if (device.getDid() != null) SET("did = #{did}");
            if (device.getAgentId() != null) SET("agentId = #{agentId}");
            if (device.getHospitalId() != null) SET("hospitalId = #{hospitalId}");
            if (device.getHospitalBed() != null) SET("hospitalBed = #{hospitalBed}");
            if (device.getCrtTime() != null) SET("crtTime = #{crtTime}");
            if (device.getCrtId() != null) SET("crtId = #{crtId}");
            if (device.getStatus() != null) SET("status = #{status}");
            if (device.getUseflag() != null) SET("useflag = #{useflag}");
            if (device.getRemark() != null) SET("remark = #{remark}");
            if (device.getDepart() != null) SET("depart = #{depart}");
            if (device.getBid() != null) SET("bid = #{bid}");
            if (device.getBell() != null) SET("bell = #{bell}");
            if (device.getRun() != null) SET("run = #{run}");
            if (device.getUpdateId() != null) SET("update_id = #{updateId}");
            if (device.getUpdateTime() != null) SET("update_time = #{updateTime}");
            if (device.getIssync() != null) SET("issync = #{issync}");
            WHERE("id = #{id}");
        }}.toString();
    }

    public String getActiveByGroup(@Param("aid") String aid, @Param("hid") String hid, @Param("oid") String oid
            , @Param("grain") String grain, @Param("start") String start, @Param("end") String end) {
        return getActiveByKey("1".equals(grain) ? "DATE_FORMAT(`crtTime`,'%Y%m%d') as `key`" : "2".equals(grain)
                ? "CONCAT(DATE_FORMAT(DATE_ADD(crtTime,INTERVAL -DAYOFWEEK(DATE(crtTime))+1 DAY)" +
                ",'%Y%m%d'), '-', DATE_FORMAT(DATE_ADD(crtTime,INTERVAL -DAYOFWEEK(DATE(crtTime))+8 DAY)" +
                ",'%Y%m%d')) as `key`" : "DATE_FORMAT(crtTime,'%Y%m') as `key`", aid, hid, oid, start, end);
    }


    public String getActiveCount(@Param("aid") String aid, @Param("hid") String hid, @Param("oid") String oid
            , @Param("start") String start, @Param("end") String end) {
        return getActiveByKey(null, aid, hid, oid, start, end);
    }

    public String getActiveRemoveCount(@Param("aid") String aid, @Param("hid") String hid, @Param("oid") String oid
            , @Param("start") String start, @Param("end") String end) {
        return new SQL() {{
            SELECT("COUNT(DISTINCT `did`) as `value`");
            FROM("t_device");
            WHERE("status = 17");
            if (!StringUtil.isEmpty(aid) && aid.contains(Constant.SIGN_DOU_HAO)) {
                AND().WHERE("`agentId` in (" + aid + ")");
            } else if (!Constant.DIGIT_ZERO.equals(aid)) {
                AND().WHERE("agentId = #{aid}");
            }
            if (!StringUtil.isEmpty(hid) && hid.contains(Constant.SIGN_DOU_HAO)) {
                AND().WHERE("`hospitalId` in (" + hid + ")");
            } else if (!Constant.DIGIT_ZERO.equals(hid)) {
                AND().WHERE("`hospitalId` = #{hid}");
            }
            if (!Constant.DIGIT_ZERO.equals(oid)) AND().WHERE("depart = #{oid}");
            if (!Constant.DIGIT_ZERO.equals(start)) AND().WHERE("crtTime >= FROM_UNIXTIME(#{start})");
            if (!Constant.DIGIT_ZERO.equals(end)) AND().WHERE("crtTime < FROM_UNIXTIME(#{end})");
            if (!Constant.DIGIT_ZERO.equals(end)) AND().WHERE("update_time>FROM_UNIXTIME(#{end})");
        }}.toString();
    }

    private String getActiveByKey(String key, String aid, String hid, String oid, String start, String end) {
        return new SQL() {{
            if (key != null) {
                SELECT(key + ", COUNT(DISTINCT `did`) as `value`");
            } else {
                SELECT("COUNT(DISTINCT `did`) as `value`");
            }
            FROM("t_device");
            WHERE("status = 14");
            if (!StringUtil.isEmpty(aid) && aid.contains(Constant.SIGN_DOU_HAO)) {
                AND().WHERE("`agentId` in (" + aid + ")");
            } else if (!Constant.DIGIT_ZERO.equals(aid)) {
                AND().WHERE("agentId = #{aid}");
            }
            if (!StringUtil.isEmpty(hid) && hid.contains(Constant.SIGN_DOU_HAO)) {
                AND().WHERE("`hospitalId` in (" + hid + ")");
            } else if (!Constant.DIGIT_ZERO.equals(hid)) {
                AND().WHERE("`hospitalId` = #{hid}");
            }
            if (!Constant.DIGIT_ZERO.equals(oid)) AND().WHERE("depart = #{oid}");
            if (!Constant.DIGIT_ZERO.equals(start)) AND().WHERE("crtTime >= FROM_UNIXTIME(#{start})");
            if (!Constant.DIGIT_ZERO.equals(end)) AND().WHERE("crtTime < FROM_UNIXTIME(#{end})");
            if (key != null) GROUP_BY("`key`");
        }}.toString();
    }


    public String findListAll(@Param(value = "did") String did, @Param(value = "bid") String bid
            , @Param(value = "bed") String bed, @Param(value = "aid") String aid, @Param(value = "hid") String hid
            , @Param(value = "oid") String oid, @Param(value = "status") int status) {
        return new SQL() {{
            SELECT("*");
            FROM("t_device");
            if (status!=0) {
               WHERE("`status`= #{status}");
            }else{
                WHERE("`status`=14");
            }
            if (!StringUtil.isEmpty(did)) {
                AND().WHERE("did=#{did}");
            }
            if (!StringUtil.isEmpty(bid)) {
                AND().WHERE("`bid`=#{bid}");
            }
            if (!StringUtil.isEmpty(bed)) {
                AND().WHERE("hospitalBed like concat(concat('%',#{bed}),'%')");
            }
            if (!StringUtil.isEmpty(aid) && !Constant.DIGIT_ZERO.equals(aid)) {
                AND().WHERE("agentId=#{aid}");
            }
            if (!StringUtil.isEmpty(hid) && !Constant.DIGIT_ZERO.equals(hid)) {
                AND().WHERE("hospitalId=#{hid}");
            }
            if (!StringUtil.isEmpty(oid) && !Constant.DIGIT_ZERO.equals(oid)) {
                AND().WHERE("depart=#{oid}");
            }
        }}.toString();
    }

    public String getDeviceInfo(@Param(value = "did") String did, @Param(value = "bid") String bid) {
        return new SQL() {{
            SELECT("d.did, d.bid, d.agentId as aid, h.id as hid, o.id as oid, d.hospitalBed as bed" +
                    ", h.`name` as hidName , o.`name` as oidName, h.address");
            FROM("t_device d,t_hospital h, t_department o");
            WHERE("d.`hospitalId` = h.id AND d.`depart` = o.id AND d.`status`=14");
            if (!StringUtil.isEmpty(did)) {
                AND().WHERE("d.`did`=#{did}");
            }
            if (!StringUtil.isEmpty(bid)) {
                AND().WHERE("d.`bid`=#{bid}");
            }
        }}.toString();
    }
}
