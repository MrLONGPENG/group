package com.mujugroup.core.mapper;

import com.mujugroup.core.model.Hospital;
import com.mujugroup.core.sql.HospitalSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 医院信息表,数据库操作接口类
 * 类名:HospitalMapper
 * 创建人:LEOLAUREL
 * 创建时间:20180627
 */
@Mapper
@Component(value ="hospitalMapper")
public interface HospitalMapper {

    @InsertProvider(type = HospitalSqlProvider.class, method = "insert")
    boolean insert(Hospital hospital);

    @UpdateProvider(type = HospitalSqlProvider.class, method = "update")
    boolean update(Hospital hospital);

    @Delete("delete from t_hospital where id= #{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_hospital WHERE id = #{id}")
    @Results(id = "hospital", value = {
         @Result(id=true, column="id",property="id",javaType=Integer.class)
             ,@Result(column="name",property="name",javaType=String.class)
             ,@Result(column="agentId",property="agentId",javaType=String.class)
             ,@Result(column="tel",property="tel",javaType=String.class)
             ,@Result(column="person",property="person",javaType=String.class)
             ,@Result(column="remark",property="remark",javaType=String.class)
             ,@Result(column="crtTime",property="crtTime",javaType=Date.class)
             ,@Result(column="crtId",property="crtId",javaType=Integer.class)
             ,@Result(column="address",property="address",javaType=String.class)
             ,@Result(column="country",property="country",javaType=Integer.class)
             ,@Result(column="province",property="province",javaType=Integer.class)
             ,@Result(column="city",property="city",javaType=Integer.class)
             ,@Result(column="longitude",property="longitude",javaType=Double.class)
             ,@Result(column="latitude",property="latitude",javaType=Double.class)
             ,@Result(column="enable",property="enable",javaType=Integer.class)
             ,@Result(column="issync",property="issync",javaType=Integer.class)
             ,@Result(column="level",property="level",javaType=String.class)
    })
    Hospital findById(Integer id);

    @Select("SELECT * FROM t_hospital limit 1000")
    @ResultMap("hospital")
    List<Hospital> findListAll();

}