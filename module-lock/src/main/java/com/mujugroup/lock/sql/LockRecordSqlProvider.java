package com.mujugroup.lock.sql;

import com.mujugroup.lock.model.LockRecord;
import org.apache.ibatis.jdbc.SQL;

/**
 * 开关锁记录表,SQL语句组装类
 * 类名:LockRecordSqlProvider
 * @author leolaurel
 * 创建时间:20181102
 */
public class LockRecordSqlProvider {

    public String insert(LockRecord lockRecord){
        return new SQL(){{
            INSERT_INTO("t_lock_record");
            if(lockRecord.getId()!= null) VALUES("`id`", "#{id}");
            if(lockRecord.getDid()!= null) VALUES("`did`", "#{did}");
            if(lockRecord.getLockId()!= null) VALUES("`lock_id`", "#{lockId}");
            if(lockRecord.getTime()!= null) VALUES("`time`", "#{time}");
            if(lockRecord.getLockStatus()!= null) VALUES("`lockStatus`", "#{lockStatus}");
        }}.toString();
    }



    public String update(LockRecord lockRecord){
        return new SQL(){{
            UPDATE("t_lock_record");
            if(lockRecord.getId()!= null) SET("`id` = #{id}");
            if(lockRecord.getDid()!= null) SET("`did` = #{did}");
            if(lockRecord.getLockId()!= null) SET("`lock_id` = #{lockId}");
            if(lockRecord.getTime()!= null) SET("`time` = #{time}");
            if(lockRecord.getLockStatus()!= null) SET("`lockStatus` = #{lockStatus}");
            WHERE("id = #{id}");
        }}.toString();
    }
}
