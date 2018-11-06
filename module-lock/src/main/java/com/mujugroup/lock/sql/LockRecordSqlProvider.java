package com.mujugroup.lock.sql;

import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.lock.model.LockRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * 开关锁记录表,SQL语句组装类
 * 类名:LockRecordSqlProvider
 *
 * @author leolaurel
 * 创建时间:20181102
 */
public class LockRecordSqlProvider {

    public String insert(LockRecord lockRecord) {
        return new SQL() {{
            INSERT_INTO("t_lock_record");
            if (lockRecord.getId() != null) VALUES("`id`", "#{id}");
            if (lockRecord.getDid() != null) VALUES("`did`", "#{did}");
            if (lockRecord.getLockId() != null) VALUES("`lock_id`", "#{lockId}");
            if (lockRecord.getReceiveTime() != null) VALUES("`receiveTime`", "#{receiveTime}");
            if (lockRecord.getLockStatus() != null) VALUES("`lockStatus`", "#{lockStatus}");
            if (lockRecord.getLocalTime() != null) VALUES("`localTime`", "#{localTime}");
        }}.toString();
    }


    public String update(LockRecord lockRecord) {
        return new SQL() {{
            UPDATE("t_lock_record");
            if (lockRecord.getId() != null) SET("`id` = #{id}");
            if (lockRecord.getDid() != null) SET("`did` = #{did}");
            if (lockRecord.getLockId() != null) SET("`lock_id` = #{lockId}");
            if (lockRecord.getReceiveTime() != null) SET("`receiveTime` = #{receiveTime}");
            if (lockRecord.getLockStatus() != null) SET("`lockStatus` = #{lockStatus}");
            if (lockRecord.getLocalTime() != null) SET("`localTime` = #{localTime}");
            WHERE("id = #{id}");
        }}.toString();
    }

    public String getLockStatusList(@Param(value = "did") String did,@Param(value = "bid") String bid) {
        return new SQL() {{
            SELECT("*");
            FROM("t_lock_record");
            WHERE("1=1");
            if (!StringUtil.isEmpty(did)) {
                AND().WHERE("did= #{did}");
            }
            if (!StringUtil.isEmpty(bid)) {
                AND().WHERE("lock_id= #{bid}");
            };
            ORDER_BY("`localTime`  DESC LIMIT 10");
        }}.toString();

    }
}
