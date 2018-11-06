package com.mujugroup.lock.sql;

import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.lock.model.LockSwitch;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * 开关锁记录表,SQL语句组装类
 * 类名:LockSwitchSqlProvider
 *
 * @author leolaurel
 * 创建时间:20181102
 */
public class LockSwitchSqlProvider {

    public String insert(LockSwitch lockSwitch) {
        return new SQL() {{
            INSERT_INTO("t_lock_switch");
            if (lockSwitch.getId() != null) VALUES("`id`", "#{id}");
            if (lockSwitch.getDid() != null) VALUES("`did`", "#{did}");
            if (lockSwitch.getLockId() != null) VALUES("`lock_id`", "#{lockId}");
            if (lockSwitch.getReceiveTime() != null) VALUES("`receiveTime`", "#{receiveTime}");
            if (lockSwitch.getLockStatus() != null) VALUES("`lockStatus`", "#{lockStatus}");
            if (lockSwitch.getLocalTime() != null) VALUES("`localTime`", "#{localTime}");
        }}.toString();
    }


    public String update(LockSwitch lockSwitch) {
        return new SQL() {{
            UPDATE("t_lock_switch");
            if (lockSwitch.getId() != null) SET("`id` = #{id}");
            if (lockSwitch.getDid() != null) SET("`did` = #{did}");
            if (lockSwitch.getLockId() != null) SET("`lock_id` = #{lockId}");
            if (lockSwitch.getReceiveTime() != null) SET("`receiveTime` = #{receiveTime}");
            if (lockSwitch.getLockStatus() != null) SET("`lockStatus` = #{lockStatus}");
            if (lockSwitch.getLocalTime() != null) SET("`localTime` = #{localTime}");
            WHERE("id = #{id}");
        }}.toString();
    }

    public String getLockStatusList(@Param(value = "did") String did,@Param(value = "bid") String bid) {
        return new SQL() {{
            SELECT("*");
            FROM("t_lock_switch");
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
