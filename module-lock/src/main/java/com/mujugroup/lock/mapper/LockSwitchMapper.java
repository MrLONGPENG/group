package com.mujugroup.lock.mapper;

import com.mujugroup.lock.model.LockSwitch;
import com.mujugroup.lock.sql.LockSwitchSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 开关锁记录表,数据库操作接口类
 * 类名:LockSwitchMapper
 *
 * @author leolaurel
 * 创建时间:20181102
 */
@Mapper
@Component(value = "lockSwitchMapper")
public interface LockSwitchMapper {

    @InsertProvider(type = LockSwitchSqlProvider.class, method = "insert")
    boolean insert(LockSwitch lockSwitch);

    @UpdateProvider(type = LockSwitchSqlProvider.class, method = "update")
    boolean update(LockSwitch lockSwitch);

    @Delete("delete from t_lock_switch where id= #{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_lock_switch WHERE id = #{id}")
    @Results(id = "lockSwitch", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class)
            , @Result(column = "did", property = "did", javaType = Long.class)
            , @Result(column = "lock_id", property = "lockId", javaType = Long.class)
            , @Result(column = "receiveTime", property = "receiveTime", javaType = Date.class)
            , @Result(column = "lockStatus", property = "lockStatus", javaType = Integer.class)
            , @Result(column = "localTime", property = "localTime", javaType = Date.class)
    })
    LockSwitch findById(Integer id);

    @Select("SELECT * FROM t_lock_switch")
    @ResultMap("lockSwitch")
    List<LockSwitch> findListAll();


    @SelectProvider(type = LockSwitchSqlProvider.class,method = "getLockStatusList")
    @ResultMap("lockSwitch")
    List<LockSwitch> getLockStatusList(@Param(value = "did") String did, @Param(value = "bid") String bid
            ,@Param(value = "startTime") String startTime,@Param(value = "endTime")String endTime);

    @ResultMap("lockSwitch")
    @Select("SELECT * FROM `t_lock_switch` WHERE `did` = #{did} AND `lockStatus` = 2 order by id desc limit 1")
    LockSwitch getLastOpenRecord(@Param(value = "did") long did);
}