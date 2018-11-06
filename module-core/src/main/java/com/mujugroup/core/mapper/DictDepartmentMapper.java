package com.mujugroup.core.mapper;

import com.mujugroup.core.model.DictDepartment;
import com.mujugroup.core.objeck.vo.SelectVo;
import com.mujugroup.core.objeck.vo.dictDepartment.ListVo;
import com.mujugroup.core.sql.DictDepartmentSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 科室字典表,数据库操作接口类
 * 类名:DictDepartmentMapper
 *
 * @author leolaurel
 * 创建时间:20181022
 */
@Mapper
@Component(value = "dictDepartmentMapper")
public interface DictDepartmentMapper {

    @InsertProvider(type = DictDepartmentSqlProvider.class, method = "insert")
    boolean insert(DictDepartment dictDepartment);

    @UpdateProvider(type = DictDepartmentSqlProvider.class, method = "update")
    boolean update(DictDepartment dictDepartment);

    @Delete("delete from t_dict_department where id= #{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_dict_department WHERE id = #{id}")
    @Results(id = "dictDepartment", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class)
            , @Result(column = "name", property = "name", javaType = String.class)
            , @Result(column = "remark", property = "remark", javaType = String.class)
            , @Result(column = "create_date", property = "createDate", javaType = Date.class)
            , @Result(column = "enable", property = "enable", javaType = Integer.class)
            , @Result(column = "create_userid", property = "createUserid", javaType = Integer.class)
            , @Result(column = "update_userid", property = "updateUserid", javaType = Integer.class)
            , @Result(column = "update_time", property = "updateTime", javaType = Date.class)
    })
    DictDepartment findById(Integer id);

    @Select("SELECT * FROM t_dict_department")
    @ResultMap("dictDepartment")
    List<DictDepartment> findListAll();

    @Select("select count(`name`) from t_dict_department where `name`= #{name}")
    @ResultType(Integer.class)
    Integer isExistName(@Param(value = "name") String name);

    @SelectProvider(type = DictDepartmentSqlProvider.class, method = "getDictDepartmentList")
    @Results(id = "selectVo", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class),
            @Result(column = "name", property = "name", javaType = String.class)
    })
    List<SelectVo> getDictDepartmentList(@Param(value = "name") String name);

    @SelectProvider(type = DictDepartmentSqlProvider.class, method = "findAll")
    @Results(id = "listVo", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class),
            @Result(column = "name", property = "name", javaType = String.class),
            @Result(column = "remark", property = "remark", javaType = String.class),
            @Result(column = "createDate", property = "createDate", javaType = Date.class),
            @Result(column = "enable", property = "enable", javaType = Integer.class),
            @Result(column = "createUserid", property = "createUserid", javaType = Integer.class),
            @Result(column = "updateUserid", property = "updateUserid", javaType = Integer.class),
            @Result(column = "updateTime", property = "updateTime", javaType = Date.class)
    })
    List<ListVo> findAll(@Param(value = "name") String name);

}