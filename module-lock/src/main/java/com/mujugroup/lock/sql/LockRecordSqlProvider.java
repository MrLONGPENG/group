package com.mujugroup.lock.sql;

import com.lveqia.cloud.common.config.Constant;
import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.lock.model.LockRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * 锁记录表,SQL语句组装类
 * 类名:LockRecordSqlProvider
 *
 * @author leolaurel
 * 创建时间:20181106
 */
public class LockRecordSqlProvider {

    public String insert(LockRecord lockRecord) {
        return new SQL() {{
            INSERT_INTO("t_lock_record");
            if (lockRecord.getId() != null) VALUES("`id`", "#{id}");
            if (lockRecord.getDid() != null) VALUES("`did`", "#{did}");
            if (lockRecord.getLockId() != null) VALUES("`lock_id`", "#{lockId}");
            if (lockRecord.getCsq() != null) VALUES("`csq`", "#{csq}");
            if (lockRecord.getTemp() != null) VALUES("`temp`", "#{temp}");
            if (lockRecord.getCharge() != null) VALUES("`charge`", "#{charge}");
            if (lockRecord.getVoltage() != null) VALUES("`voltage`", "#{voltage}");
            if (lockRecord.getElectric() != null) VALUES("`electric`", "#{electric}");
            if (lockRecord.getBatteryStat() != null) VALUES("`battery_stat`", "#{batteryStat}");
            if (lockRecord.getLockStatus() != null) VALUES("`lock_status`", "#{lockStatus}");
            if (lockRecord.getLastRefresh() != null) VALUES("`last_refresh`", "#{lastRefresh}");
            if (lockRecord.getCrtTime() != null) VALUES("`crtTime`", "#{crtTime}");
        }}.toString();
    }


    public String update(LockRecord lockRecord) {
        return new SQL() {{
            UPDATE("t_lock_record");
            if (lockRecord.getDid() != null) SET("`did` = #{did}");
            if (lockRecord.getLockId() != null) SET("`lock_id` = #{lockId}");
            if (lockRecord.getCsq() != null) SET("`csq` = #{csq}");
            if (lockRecord.getTemp() != null) SET("`temp` = #{temp}");
            if (lockRecord.getCharge() != null) SET("`charge` = #{charge}");
            if (lockRecord.getVoltage() != null) SET("`voltage` = #{voltage}");
            if (lockRecord.getElectric() != null) SET("`electric` = #{electric}");
            if (lockRecord.getBatteryStat() != null) SET("`battery_stat` = #{batteryStat}");
            if (lockRecord.getLockStatus() != null) SET("`lock_status` = #{lockStatus}");
            if (lockRecord.getLastRefresh() != null) SET("`last_refresh` = #{lastRefresh}");
            if (lockRecord.getCrtTime() != null) SET("`crtTime` = #{crtTime}");
            WHERE("id = #{id}");
        }}.toString();
    }

    public String getRecordList(@Param(value = "did") String did, @Param(value = "bid") String bid
            , @Param(value = "startTime") String startTime, @Param(value = "endTime") String endTime
            , @Param(value = "chargeStart") String chargeStart, @Param(value = "chargeEnd") String chargeEnd) {
        return new SQL() {{
            SELECT("*");
            FROM("t_lock_record");
            WHERE("1=1");
            if (!StringUtil.isEmpty(did)) {
                AND().WHERE("did = #{did}");
            }
            if (!StringUtil.isEmpty(bid)) {
                AND().WHERE("lock_id = #{bid}");
            }
            if (!StringUtil.isEmpty(startTime) && !Constant.DIGIT_ZERO.equals(startTime))
                AND().WHERE("`crtTime` >= #{startTime}");
            if (!StringUtil.isEmpty(endTime) && !Constant.DIGIT_ZERO.equals(endTime))
                AND().WHERE("`crtTime` < #{endTime}");
            if (!StringUtil.isEmpty(chargeStart)) {
                AND().WHERE("`charge`>= #{chargeStart}");
            }
            if (!StringUtil.isEmpty(chargeEnd)) {
                AND().WHERE("`charge`<= #{chargeEnd}");
            }
            ORDER_BY("`id` DESC");
        }}.toString();

    }
}
