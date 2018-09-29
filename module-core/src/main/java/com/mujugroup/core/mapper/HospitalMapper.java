package com.mujugroup.core.mapper;

import com.mujugroup.core.model.Hospital;
import com.mujugroup.core.objeck.vo.SelectVO;
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

    @Select("SELECT * FROM t_hospital WHERE enable = 22")
    @ResultMap("hospital")
    List<Hospital> findListAll();



    @ResultMap("hospital")
    @Select("SELECT * FROM t_hospital WHERE enable = 22 AND agentId = #{aid} ")
    List<Hospital> findListByAid(String aid);

    @ResultType(String.class)
    @Select("SELECT `name` FROM `t_hospital` WHERE `id` = #{hid}")
    String getHospitalById(@Param("hid") String hid);

    @ResultType(String.class)
    @Select("SELECT c.`name` FROM `t_hospital` h LEFT JOIN `t_country_province_city` c" +
            " ON h.`province` = c.`id` WHERE h.`id` = #{hid}")
    String getProvinceByHid(@Param("hid") String hid);

    @ResultType(String.class)
    @Select("SELECT c.`name` FROM `t_hospital` h LEFT JOIN `t_country_province_city` c" +
            " ON h.`city` = c.`id` WHERE h.`id` = #{hid}")
    String getCityByHid(@Param("hid") String hid);


    @ResultMap("hospital")
    @Select("SELECT * FROM t_hospital WHERE enable = 22 AND province = #{pid} AND city = #{cid} ")
    List<Hospital> getHospitalByRegion(@Param("pid") String pid, @Param("cid") String cid);


    @Results({@Result(id=true, column="id",property="id",javaType=Integer.class)
            ,@Result(column="name",property="name",javaType=String.class)
    })
    @SelectProvider(type = HospitalSqlProvider.class, method = "getHospitalList")
    List<SelectVO> getHospitalList(@Param("aid") int aid, @Param("name") String name);

    @Select("Select d.rid as id,a.name as name  From t_auth_data d, t_hospital a WHERE d.rid = a.id AND d.`type`=#{type} AND a.enable = 22 AND d.uid = #{uid}")
    @Results(id = "HospticalList", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class),
            @Result(column = "name", property = "name", javaType = String.class)
    })
    List<SelectVO> getHospitalListByUid(@Param("type") int type,@Param("uid") long uid);

    @Select("Select d.rid as id,a.name as name  From t_auth_data d, t_hospital a WHERE d.rid = a.agentId AND d.`type`=#{type} AND a.enable = 22 AND d.uid = #{uid}")
    @ResultMap("HospticalList")
    List<SelectVO> getAgentHospitalListByUid(@Param("type") int type,@Param("uid") long uid);
}