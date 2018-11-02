package com.mujugroup.lock.mapper;

import com.mujugroup.lock.model.LockRecord;
import com.mujugroup.lock.sql.LockRecordSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 开关锁记录表,数据库操作接口类
 * 类名:LockRecordMapper
 * @author leolaurel
 * 创建时间:20181102
 */
@Mapper
@Component(value ="lockRecordMapper")
public interface LockRecordMapper {

    @InsertProvider(type = LockRecordSqlProvider.class, method = "insert")
    boolean insert(LockRecord lockRecord);

    @UpdateProvider(type = LockRecordSqlProvider.class, method = "update")
    boolean update(LockRecord lockRecord);

    @Delete("delete from t_lock_record where id= #{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_lock_record WHERE id = #{id}")
    @Results(id = "lockRecord", value = {
         @Result(id=true, column="id",property="id",javaType=Integer.class)
             ,@Result(column="did",property="did",javaType=Long.class)
             ,@Result(column="lock_id",property="lockId",javaType=Long.class)
             ,@Result(column="lock_hex",property="lockHex",javaType=String.class)
             ,@Result(column="time",property="time",javaType=Date.class)
             ,@Result(column="lockStatus",property="lockStatus",javaType=Integer.class)
    })
    LockRecord findById(Integer id);

    @Select("SELECT * FROM t_lock_record")
    @ResultMap("lockRecord")
    List<LockRecord> findListAll();

}