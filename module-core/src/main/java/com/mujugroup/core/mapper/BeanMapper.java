package com.mujugroup.core.mapper;

import com.mujugroup.core.objeck.bean.DepartmentBean;
import com.mujugroup.core.objeck.bean.DeviceBean;
import com.mujugroup.core.objeck.bean.HospitalBean;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Component;

/**
 * 设备关联表,数据库操作接口类
 * 类名:DeviceMapper
 * @author :LEOLAUREL
 * 创建时间:20180627
 */
@Mapper
@Component(value ="beanMapper")
public interface BeanMapper {


    @Select("SELECT * FROM t_device WHERE `status`=14 AND mac = #{did}")
    @Results(value={@Result(column="mac",property="did",javaType=String.class)
            ,@Result(column="code",property="code",javaType=String.class)
            ,@Result(column="agentId",property="agentId",javaType=Integer.class)
            ,@Result(column="hospitalId",property="hospitalId",javaType=Integer.class)
            ,@Result(column="depart",property="departmentId",javaType=Integer.class)
            ,@Result(column="hospitalBed",property="hospitalBed",javaType=String.class)
            ,@Result(column="hospitalId",property="hospital",
                one=@One(
                    select="com.mujugroup.core.mapper.BeanMapper.findHospitalBeanById",
                    fetchType=FetchType.EAGER
            ))
            ,@Result(column="depart",property="department",
                one=@One(
                    select="com.mujugroup.core.mapper.BeanMapper.findDepartmentBeanById",
                    fetchType=FetchType.EAGER
            ))
    })
    DeviceBean findDeviceBeanByDid(String did);

    @Select("SELECT * FROM t_hospital WHERE id = #{id}")
    @Results(value = {@Result(id=true, column="id",property="id",javaType=Integer.class)
            ,@Result(column="name",property="name",javaType=String.class)
            ,@Result(column="address",property="address",javaType=String.class)

    })
    HospitalBean findHospitalBeanById(Integer id);




    @Select("SELECT * FROM t_department WHERE id = #{id}")
    @Results(value = {@Result(id=true, column="id",property="id",javaType=Integer.class)
            ,@Result(column="hospital_id",property="hospitalId",javaType=Integer.class)
            ,@Result(column="name",property="name",javaType=String.class)
    })
    DepartmentBean findDepartmentBeanById(Integer id);



    
}