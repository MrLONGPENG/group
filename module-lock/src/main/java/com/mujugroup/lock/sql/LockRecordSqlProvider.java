package com.mujugroup.lock.sql;

import com.mujugroup.lock.model.LockRecord;
import org.apache.ibatis.jdbc.SQL;

/**
 * 锁记录表,SQL语句组装类
 * 类名:LockRecordSqlProvider
 * @author leolaurel
 * 创建时间:20181106
 */
public class LockRecordSqlProvider {

    public String insert(LockRecord lockRecord){
        return new SQL(){{
            INSERT_INTO("t_lock_record");
            if(lockRecord.getId()!= null) VALUES("`id`", "#{id}");
            if(lockRecord.getDid()!= null) VALUES("`did`", "#{did}");
            if(lockRecord.getLockId()!= null) VALUES("`lock_id`", "#{lockId}");
            if(lockRecord.getCsq()!= null) VALUES("`csq`", "#{csq}");
            if(lockRecord.getTemp()!= null) VALUES("`temp`", "#{temp}");
            if(lockRecord.getCharge()!= null) VALUES("`charge`", "#{charge}");
            if(lockRecord.getVoltage()!= null) VALUES("`voltage`", "#{voltage}");
            if(lockRecord.getElectric()!= null) VALUES("`electric`", "#{electric}");
            if(lockRecord.getBatteryStat()!= null) VALUES("`battery_stat`", "#{batteryStat}");
            if(lockRecord.getLockStatus()!= null) VALUES("`lock_status`", "#{lockStatus}");
            if(lockRecord.getLastRefresh()!= null) VALUES("`last_refresh`", "#{lastRefresh}");
            if(lockRecord.getCrtTime()!= null) VALUES("`crtTime`", "#{crtTime}");
        }}.toString();
    }



    public String update(LockRecord lockRecord){
        return new SQL(){{
            UPDATE("t_lock_record");
            if(lockRecord.getId()!= null) SET("`id` = #{id}");
            if(lockRecord.getDid()!= null) SET("`did` = #{did}");
            if(lockRecord.getLockId()!= null) SET("`lock_id` = #{lockId}");
            if(lockRecord.getCsq()!= null) SET("`csq` = #{csq}");
            if(lockRecord.getTemp()!= null) SET("`temp` = #{temp}");
            if(lockRecord.getCharge()!= null) SET("`charge` = #{charge}");
            if(lockRecord.getVoltage()!= null) SET("`voltage` = #{voltage}");
            if(lockRecord.getElectric()!= null) SET("`electric` = #{electric}");
            if(lockRecord.getBatteryStat()!= null) SET("`battery_stat` = #{batteryStat}");
            if(lockRecord.getLockStatus()!= null) SET("`lock_status` = #{lockStatus}");
            if(lockRecord.getLastRefresh()!= null) SET("`last_refresh` = #{lastRefresh}");
            if(lockRecord.getCrtTime()!= null) SET("`crtTime` = #{crtTime}");
            WHERE("id = #{id}");
        }}.toString();
    }
}
