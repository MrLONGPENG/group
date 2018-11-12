package com.mujugroup.lock.mapper;

import com.lveqia.cloud.common.objeck.DBMap;
import com.mujugroup.lock.model.LockFail;
import com.mujugroup.lock.objeck.bo.fail.FailBo;
import com.mujugroup.lock.sql.LockFailSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 锁故障表,数据库操作接口类
 * 类名:LockFailMapper
 *
 * @author leolaurel
 * 创建时间:20181107
 */
@Mapper
@Component(value = "lockFailMapper")
public interface LockFailMapper {

    @InsertProvider(type = LockFailSqlProvider.class, method = "insert")
    boolean insert(LockFail lockFail);

    @UpdateProvider(type = LockFailSqlProvider.class, method = "update")
    boolean update(LockFail lockFail);

    @Delete("delete from t_lock_fail where id= #{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_lock_fail WHERE id = #{id}")
    @Results(id = "lockFail", value = {
            @Result(id = true, column = "id", property = "id", javaType = Long.class)
            , @Result(column = "did", property = "did", javaType = Long.class)
            , @Result(column = "lock_id", property = "lockId", javaType = Long.class)
            , @Result(column = "aid", property = "aid", javaType = Integer.class)
            , @Result(column = "hid", property = "hid", javaType = Integer.class)
            , @Result(column = "oid", property = "oid", javaType = Integer.class)
            , @Result(column = "fail_type", property = "failType", javaType = Integer.class)
            , @Result(column = "error_type", property = "errorType", javaType = Integer.class)
            , @Result(column = "last_refresh", property = "lastRefresh", javaType = Date.class)
            , @Result(column = "finish_time", property = "finishTime", javaType = Date.class)
            , @Result(column = "status", property = "status", javaType = Integer.class)
            , @Result(column = "resolve_man", property = "resolveMan", javaType = Integer.class)
            , @Result(column = "resolve_type", property = "resolveType", javaType = Integer.class)
            , @Result(column = "explain", property = "explain", javaType = String.class)
    })
    LockFail findById(Integer id);

    @Select("SELECT * FROM t_lock_fail")
    @ResultMap("lockFail")
    List<LockFail> findListAll();

    @SelectProvider(type = LockFailSqlProvider.class, method = "getFailCount")
    @ResultType(DBMap.class)
    List<DBMap> getFailCount(@Param(value = "aid") String aid, @Param(value = "hid") String hid, @Param(value = "oid") String oid);


    @SelectProvider(type = LockFailSqlProvider.class, method = "getFailInfoList")
    @Results(value = {@Result(column = "did", property = "did", javaType = String.class)
            , @Result(column = "oid", property = "oid", javaType = String.class)
            , @Result(column = "dict_name", property = "name", javaType = String.class)
            , @Result(column = "lock_status", property = "status", javaType = Integer.class)
            , @Result(column = "battery_stat", property = "battery", javaType = Integer.class)
            , @Result(column = "last_refresh", property = "lastRefresh", javaType = Date.class)
            , @Result(column = "electric", property = "electric", javaType = Integer.class)
            , @Result(column = "bed", property = "bed", javaType = String.class)
            , @Result(column = "endTime", property = "endTime", javaType = String.class)
    })
    List<FailBo> getFailInfoList(@Param(value = "aid") String aid, @Param(value = "hid") String hid, @Param(value = "oid") String oid, @Param(value = "type") int type);

    @Select(" SELECT d.dict_name FROM t_lock_fail f,t_lock_dict d WHERE f.fail_type=d.dict_key AND f.did= #{did}")
    @ResultType(String.class)
    List<String> getFailNameByDid(@Param(value = "did") String did);
}