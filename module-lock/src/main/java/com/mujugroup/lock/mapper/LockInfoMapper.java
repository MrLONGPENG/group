package com.mujugroup.lock.mapper;


import com.lveqia.cloud.common.objeck.to.LockTo;
import com.mujugroup.lock.model.LockInfo;
import com.mujugroup.lock.objeck.vo.info.ListVo;
import com.mujugroup.lock.sql.LockInfoSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 锁状态数据库操作接口类
 * 类名:LockInfoMapper
 * 创建人:leolaurel
 * 创建时间:20180621
 */
@Mapper
@Component(value = "lockInfoMapper")
public interface LockInfoMapper {

    @InsertProvider(type = LockInfoSqlProvider.class, method = "insert")
    boolean insert(LockInfo lockInfo);

    @UpdateProvider(type = LockInfoSqlProvider.class, method = "update")
    boolean update(LockInfo lockInfo);

    @Delete("delete from t_lock_info where id=#{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_lock_info WHERE id = #{id}")
    @Results(id = "lockInfo", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class)
            , @Result(column = "lock_id", property = "lockId", javaType = Long.class),
            @Result(column = "brand", property = "brand", javaType = Integer.class),
            @Result(column = "mac", property = "mac", javaType = String.class),
            @Result(column = "key", property = "key", javaType = String.class),
            @Result(column = "sim_id", property = "simId", javaType = String.class),
            @Result(column = "f_version", property = "fVersion", javaType = Integer.class),
            @Result(column = "h_version", property = "hVersion", javaType = Integer.class),
            @Result(column = "longitude", property = "longitude", javaType = java.math.BigDecimal.class),
            @Result(column = "latitude", property = "latitude", javaType = java.math.BigDecimal.class),
            @Result(column = "csq", property = "csq", javaType = Integer.class),
            @Result(column = "temp", property = "temp", javaType = Integer.class),
            @Result(column = "charge", property = "charge", javaType = Integer.class),
            @Result(column = "voltage", property = "voltage", javaType = Integer.class),
            @Result(column = "electric", property = "electric", javaType = Integer.class),
            @Result(column = "upgrade", property = "upgrade", javaType = Integer.class),
            @Result(column = "battery_stat", property = "batteryStat", javaType = Integer.class),
            @Result(column = "lock_status", property = "lockStatus", javaType = Integer.class),
            @Result(column = "last_refresh", property = "lastRefresh", javaType = Date.class)})
    LockInfo findById(Integer id);

    @Select("SELECT * FROM t_lock_info")
    @ResultMap("lockInfo")
    List<LockInfo> findListAll();

    /**
     * 根据参数查询对象
     *
     * @return LockInfo
     */
    @Select("SELECT * FROM `t_lock_info` WHERE lock_id = #{bid}")
    @ResultMap("lockInfo")
    LockInfo getLockInfoByBid(String bid);

    @Select("SELECT t.did, csq,temp,charge,voltage,electric,battery_stat batteryStat,lock_status lockStatus" +
            ",last_refresh lastRefresh,f_version fVersion,h_version hVersion FROM `t_lock_info` info, `t_lock_did` t" +
            " WHERE info.lock_id = t.lock_id AND t.did= #{did} ")
    LockTo getInfoByDid(@Param(value = "did") String did);

    @SelectProvider(type = LockInfoSqlProvider.class, method = "getInfoList")
    @Results(id = "listVo", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class),
             @Result(column = "did", property = "did", javaType = String.class),
            @Result(column = "lock_id", property = "lockId", javaType = String.class),
            @Result(column = "brand", property = "brand", javaType = Integer.class),
            @Result(column = "f_version", property = "fVersion", javaType = Integer.class),
            @Result(column = "h_version", property = "hVersion", javaType = Integer.class),
            @Result(column = "csq", property = "csq", javaType = Integer.class),
            @Result(column = "temp", property = "temp", javaType = Integer.class),
            @Result(column = "charge", property = "charge", javaType = Integer.class),
            @Result(column = "voltage", property = "voltage", javaType = Integer.class),
            @Result(column = "electric", property = "electric", javaType = Integer.class),
            @Result(column = "battery_stat", property = "batteryStat", javaType = Integer.class),
            @Result(column = "lock_status", property = "lockStatus", javaType = Integer.class),
            @Result(column = "last_refresh", property = "lastRefresh", javaType = Date.class)})

    List<ListVo> getInfoList(@Param(value = "did") String did, @Param(value = "bid") String bid, @Param(value = "fVersion") String fVersion
            , @Param(value = "hVersion") String hVersion, @Param(value = "batteryStatStart") String batteryStatStart
            , @Param(value = "batteryStatEnd") String batteryStatEnd, @Param(value = "csqStart") String csqStart
            , @Param(value = "csqEnd") String csqEnd
            , @Param(value = "lockStatus") int lockStatus, int elecStatus
            , int lineStatus);
}
