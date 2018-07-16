package com.mujugroup.lock.mapper;


import com.mujugroup.lock.model.LockDid;
import com.mujugroup.lock.sql.LockDidSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 锁设备数据库操作接口类
 * 类名:LockInfo
 * 创建人:leolaurel
 * 创建时间:20180621
 */
@Mapper
@Component(value ="lockDidMapper")
public interface LockDidMapper {

    @InsertProvider(type = LockDidSqlProvider.class, method = "insert")
    boolean insert(LockDid lockDid);

    @UpdateProvider(type = LockDidSqlProvider.class, method = "update")
    boolean update(LockDid lockDid);

    @Delete("delete from t_lock_did where id= #{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_lock_did WHERE id = #{id}")
    @Results(id = "lockDid", value = {
            @Result(id=true, column="id",property="id",javaType=Integer.class)
            ,@Result(column="did",property="did",javaType=Long.class)
            ,@Result(column="brand",property="brand",javaType=Integer.class)
            ,@Result(column="lock_id",property="lockId",javaType=Long.class)
            ,@Result(column="lock_hex",property="lockHex",javaType=String.class)
            ,@Result(column="update_time",property="updateTime",javaType=Date.class)
    })
    LockDid findById(Integer id);
    /**
     * 查询全部List
     * @return List<LockDid>
     */
    @Select("SELECT * FROM `t_lock_did` limit 100")
    @ResultMap("lockDid")
    List<LockDid> list();


    /**
     * 根据参数查询对象
     * @return List<LockDid>
     */
    @Select("SELECT * FROM `t_lock_did`  WHERE did = #{did}")
    @ResultMap("lockDid")
    LockDid getLockDidByDid(@Param("did") String did);

    /**
     * 根据参数查询对象
     * @return List<LockDid>
     */
    @Select("SELECT * FROM `t_lock_did`  WHERE lock_id = #{bid}")
    @ResultMap("lockDid")
    LockDid getLockDidByBid(@Param("bid") String bid);

}