package com.mujugroup.core.mapper;

import com.mujugroup.core.bean.DeviceBean;
import com.mujugroup.core.model.Device;
import com.mujugroup.core.sql.DeviceSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 设备关联表,数据库操作接口类
 * 类名:DeviceMapper
 * 创建人:LEOLAUREL
 * 创建时间:20180627
 */
@Mapper
@Component(value ="deviceMapper")
public interface DeviceMapper {

    @InsertProvider(type = DeviceSqlProvider.class, method = "insert")
    boolean insert(Device device);

    @UpdateProvider(type = DeviceSqlProvider.class, method = "update")
    boolean update(Device device);

    @Delete("delete from t_device where id= #{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_device WHERE id = #{id}")
    @Results(id = "device", value = {
         @Result(id=true, column="id",property="id",javaType=Integer.class)
             ,@Result(column="mac",property="mac",javaType=String.class)
             ,@Result(column="agentId",property="agentId",javaType=Integer.class)
             ,@Result(column="hospitalId",property="hospitalId",javaType=Integer.class)
             ,@Result(column="hospitalBed",property="hospitalBed",javaType=String.class)
             ,@Result(column="crtTime",property="crtTime",javaType=Date.class)
             ,@Result(column="crtId",property="crtId",javaType=Integer.class)
             ,@Result(column="status",property="status",javaType=Integer.class)
             ,@Result(column="useflag",property="useflag",javaType=Integer.class)
             ,@Result(column="reserve_date",property="reserveDate",javaType=Date.class)
             ,@Result(column="imgUrl",property="imgUrl",javaType=String.class)
             ,@Result(column="remark",property="remark",javaType=String.class)
             ,@Result(column="depart",property="depart",javaType=Integer.class)
             ,@Result(column="code",property="code",javaType=String.class)
             ,@Result(column="pay",property="pay",javaType=Integer.class)
             ,@Result(column="run",property="run",javaType=Integer.class)
             ,@Result(column="station_id",property="stationId",javaType=Integer.class)
             ,@Result(column="is_station",property="isStation",javaType=Integer.class)
             ,@Result(column="update_id",property="updateId",javaType=Integer.class)
             ,@Result(column="update_time",property="updateTime",javaType=Date.class)
             ,@Result(column="issync",property="issync",javaType=Integer.class)
    })
    Device findById(Integer id);

    @Select("SELECT * FROM t_device limit 1000")
    @ResultMap("device")
    List<Device> findListAll();



}