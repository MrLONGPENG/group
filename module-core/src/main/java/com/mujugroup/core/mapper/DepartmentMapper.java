package com.mujugroup.core.mapper;

import com.mujugroup.core.model.Department;
import com.mujugroup.core.sql.DepartmentSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 科室表,数据库操作接口类
 * 类名:DepartmentMapper
 * 创建人:LEOLAUREL
 * 创建时间:20180627
 */
@Mapper
@Component(value ="departmentMapper")
public interface DepartmentMapper {

    @InsertProvider(type = DepartmentSqlProvider.class, method = "insert")
    boolean insert(Department department);

    @UpdateProvider(type = DepartmentSqlProvider.class, method = "update")
    boolean update(Department department);

    @Delete("delete from t_department where id= #{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_department WHERE id = #{id}")
    @Results(id = "department", value = {
         @Result(id=true, column="id",property="id",javaType=Integer.class)
             ,@Result(column="status",property="status",javaType=Integer.class)
             ,@Result(column="hospital_id",property="hospitalId",javaType=Integer.class)
             ,@Result(column="name",property="name",javaType=String.class)
             ,@Result(column="aihui_depart_id",property="aihuiDepartId",javaType=Integer.class)
             ,@Result(column="remark",property="remark",javaType=String.class)
             ,@Result(column="sort",property="sort",javaType=Integer.class)
             ,@Result(column="create_date",property="createDate",javaType=Date.class)
    })
    Department findById(Integer id);

    @Select("SELECT * FROM t_department")
    @ResultMap("department")
    List<Department> findListAll();


    @ResultMap("department")
    @Select("SELECT * FROM t_department WHERE `status`= 1 AND hospital_id = #{hid}")
    List<Department> findListByHid(String hid);
}