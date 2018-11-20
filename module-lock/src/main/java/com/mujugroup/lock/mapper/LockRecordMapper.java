package com.mujugroup.lock.mapper;

import com.mujugroup.lock.model.LockRecord;
import com.mujugroup.lock.objeck.vo.record.ListVo;
import com.mujugroup.lock.sql.LockRecordSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 锁记录表,数据库操作接口类
 * 类名:LockRecordMapper
 *
 * @author leolaurel
 * 创建时间:20181106
 */
@Mapper
@Component(value = "lockRecordMapper")
public interface LockRecordMapper {

    @InsertProvider(type = LockRecordSqlProvider.class, method = "insert")
    boolean insert(LockRecord lockRecord);

    @UpdateProvider(type = LockRecordSqlProvider.class, method = "update")
    boolean update(LockRecord lockRecord);

    @Delete("delete from t_lock_record where id= #{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_lock_record WHERE id = #{id}")
    @Results(id = "record", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class)
            , @Result(column = "did", property = "did", javaType = Long.class)
            , @Result(column = "lock_id", property = "lockId", javaType = Long.class)
            , @Result(column = "csq", property = "csq", javaType = Integer.class)
            , @Result(column = "temp", property = "temp", javaType = Integer.class)
            , @Result(column = "charge", property = "charge", javaType = Integer.class)
            , @Result(column = "voltage", property = "voltage", javaType = Integer.class)
            , @Result(column = "electric", property = "electric", javaType = Integer.class)
            , @Result(column = "battery_stat", property = "batteryStat", javaType = Integer.class)
            , @Result(column = "lock_status", property = "lockStatus", javaType = Integer.class)
            , @Result(column = "last_refresh", property = "lastRefresh", javaType = Date.class)
            , @Result(column = "crtTime", property = "crtTime", javaType = Date.class)
    })
    LockRecord findById(Integer id);

    @Select("SELECT * FROM t_lock_record")
    @ResultMap("record")
    List<LockRecord> findListAll();

    @Select("SELECT * from t_lock_record where did= #{did} order by `id` desc limit #{limitNum}")
    @ResultMap("record")
    List<LockRecord> findByDid(@Param(value = "did") String did, @Param(value = "limitNum") Integer limitNum);

    @SelectProvider(type = LockRecordSqlProvider.class, method = "getRecordList")
    @Results(id = "ListVo", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class)
            , @Result(column = "did", property = "did", javaType = String.class)
            , @Result(column = "lock_id", property = "lockId", javaType = String.class)
            , @Result(column = "csq", property = "csq", javaType = Integer.class)
            , @Result(column = "temp", property = "temp", javaType = Integer.class)
            , @Result(column = "charge", property = "charge", javaType = Integer.class)
            , @Result(column = "voltage", property = "voltage", javaType = Integer.class)
            , @Result(column = "electric", property = "electric", javaType = Integer.class)
            , @Result(column = "battery_stat", property = "batteryStat", javaType = Integer.class)
            , @Result(column = "lock_status", property = "lockStatus", javaType = Integer.class)
            , @Result(column = "last_refresh", property = "lastRefresh", javaType = Date.class)
            , @Result(column = "crtTime", property = "crtTime", javaType = Date.class)
    })
    List<ListVo> getRecordList(@Param(value = "did") String did, @Param(value = "bid") String bid
            , @Param(value = "startTime") String startTime, @Param(value = "endTime") String endTime
            , @Param(value = "chargeStart") String chargeStart, @Param(value = "chargeEnd") String chargeEnd);

}