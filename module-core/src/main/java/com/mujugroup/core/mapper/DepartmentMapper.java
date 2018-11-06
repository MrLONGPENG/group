package com.mujugroup.core.mapper;

import com.lveqia.cloud.common.objeck.DBMap;
import com.mujugroup.core.model.Department;
import com.mujugroup.core.objeck.vo.department.ListVo;
import com.mujugroup.core.sql.DepartmentSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import com.mujugroup.core.objeck.vo.SelectVo;

import java.util.Date;
import java.util.List;

/**
 * 科室表,数据库操作接口类
 * 类名:DepartmentMapper
 * 创建人:LEOLAUREL
 * 创建时间:20180627
 */
@Mapper
@Component(value = "departmentMapper")
public interface DepartmentMapper {

    @InsertProvider(type = DepartmentSqlProvider.class, method = "insert")
    boolean insert(Department department);

    @UpdateProvider(type = DepartmentSqlProvider.class, method = "update")
    boolean update(Department department);

    @Delete("delete from t_department where id= #{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_department WHERE id = #{id}")
    @Results(id = "department", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class)
            , @Result(column = "status", property = "status", javaType = Integer.class)
            , @Result(column = "hospital_id", property = "hid", javaType = Integer.class)
            , @Result(column = "name", property = "name", javaType = String.class)
            , @Result(column = "mid", property = "mid", javaType = Integer.class)
            , @Result(column = "remark", property = "remark", javaType = String.class)
            , @Result(column = "sort", property = "sort", javaType = Integer.class)
            , @Result(column = "create_date", property = "createDate", javaType = Date.class)
    })
    Department findById(Integer id);

    @Select("SELECT * FROM t_department")
    @ResultMap("department")
    List<Department> findListAll();


    @ResultMap("department")
    @Select("SELECT * FROM t_department WHERE `status`= 1 AND hospital_id = #{hid}")
    List<Department> findListByHid(@Param(value = "hid") String hid);

    @Select("SELECT id as `key`, `name` as value  FROM t_department WHERE `status`= 1 AND hospital_id = #{hid} ")
    @Results({@Result(column = "key", property = "key", javaType = String.class)
            , @Result(column = "value", property = "value", javaType = String.class)})
    List<DBMap> findOidByHid(@Param(value = "hid") String hid);


    @ResultType(String.class)
    @Select("SELECT `name` FROM `t_department` WHERE `status`= 1 AND `id` = #{id}")
    String getDepartmentNameById(@Param("id") String id);

    @Select("SELECT COUNT(*) FROM t_department WHERE `name`=#{name} AND hospital_id = #{hid}")
    @ResultType(Integer.class)
    Integer isExistName(@Param(value = "name") String name,@Param(value = "hid") String hid);

    @SelectProvider(type = DepartmentSqlProvider.class, method = "findAll")
    @Results(id = "listVo", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class)
            , @Result(column = "status", property = "status", javaType = Integer.class)
            , @Result(column = "hid", property = "hid", javaType = Integer.class)
            , @Result(column = "name", property = "name", javaType = String.class)
            , @Result(column = "mid", property = "mid", javaType = Integer.class)
            , @Result(column = "remark", property = "remark", javaType = String.class)
            , @Result(column = "sort", property = "sort", javaType = Integer.class)
            , @Result(column = "create_date", property = "createDate", javaType = Date.class)
            , @Result(column = "hospital", property = "hospital", javaType = String.class)
            , @Result(column = "department", property = "department", javaType = String.class)
    })
    List<ListVo> findAll(@Param(value = "hid") String hid,@Param(value = "name") String name,@Param(value = "status") String status);


    @Results(id = "selectVo", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class),
            @Result(column = "name", property = "name", javaType = String.class)
    })
    @SelectProvider(type = DepartmentSqlProvider.class, method = "getSelectList")
    List<SelectVo> getSelectList(@Param(value = "hid") int hid, @Param(value = "name") String name);
}