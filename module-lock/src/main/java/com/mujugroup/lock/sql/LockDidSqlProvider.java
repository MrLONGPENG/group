package com.mujugroup.lock.sql;

import com.mujugroup.lock.model.LockDid;
import org.apache.ibatis.jdbc.SQL;

/**
 * 柜子业务编号与锁编号关系表,SQL语句组装类
 * 类名:LockDidSqlProvider
 * 创建人:leolaurel
 * 创建时间:20180623
 */
public class LockDidSqlProvider {

    public String insert(LockDid lockDid){
        return new SQL(){{
            INSERT_INTO("t_lock_did");
            if(lockDid.getId()!= null) VALUES("id", "#{id}");
            if(lockDid.getDid()!= null) VALUES("did", "#{did}");
            if(lockDid.getBrand()!= null) VALUES("brand", "#{brand}");
            if(lockDid.getLockId()!= null) VALUES("lock_id", "#{lockId}");
            if(lockDid.getLockHex()!= null) VALUES("lock_hex", "#{lockHex}");
            if(lockDid.getUpdateTime()!= null) VALUES("update_time", "#{updateTime}");
        }}.toString();
    }



    public String update(LockDid lockDid){
        return new SQL(){{
            UPDATE("t_lock_did");
            if(lockDid.getDid()!= null) SET("did = #{did}");
            if(lockDid.getBrand()!= null) SET("brand = #{brand}");
            if(lockDid.getLockId()!= null) SET("lock_id = #{lockId}");
            if(lockDid.getLockHex()!= null) SET("lock_hex = #{lockHex}");
            if(lockDid.getUpdateTime()!= null) SET("update_time = #{updateTime}");
            WHERE("id = #{id}");
        }}.toString();
    }
}
