package com.mujugroup.core.sql;

import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.core.model.DictDepartment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * 科室字典表,SQL语句组装类
 * 类名:DictDepartmentSqlProvider
 *
 * @author leolaurel
 * 创建时间:20181022
 */
public class DictDepartmentSqlProvider {

    public String insert(DictDepartment dictDepartment) {
        return new SQL() {{
            INSERT_INTO("t_dict_department");
            if (dictDepartment.getId() != null) VALUES("`id`", "#{id}");
            if (dictDepartment.getName() != null) VALUES("`name`", "#{name}");
            if (dictDepartment.getRemark() != null) VALUES("`remark`", "#{remark}");
            if (dictDepartment.getCreateDate() != null) VALUES("`create_date`", "#{createDate}");
            if (dictDepartment.getEnable() != null) VALUES("`enable`", "#{enable}");
            if (dictDepartment.getCreateUserid() != null) VALUES("`create_userid`", "#{createUserid}");
            if (dictDepartment.getUpdateUserid() != null) VALUES("`update_userid`", "#{updateUserid}");
            if (dictDepartment.getUpdateTime() != null) VALUES("`update_time`", "#{updateTime}");
        }}.toString();
    }


    public String update(DictDepartment dictDepartment) {
        return new SQL() {{
            UPDATE("t_dict_department");
            if (dictDepartment.getId() != null) SET("`id` = #{id}");
            if (dictDepartment.getName() != null) SET("`name` = #{name}");
            if (dictDepartment.getRemark() != null) SET("`remark` = #{remark}");
            if (dictDepartment.getCreateDate() != null) SET("`create_date` = #{createDate}");
            if (dictDepartment.getEnable() != null) SET("`enable` = #{enable}");
            if (dictDepartment.getCreateUserid() != null) SET("`create_userid` = #{createUserid}");
            if (dictDepartment.getUpdateUserid() != null) SET("`update_userid` = #{updateUserid}");
            if (dictDepartment.getUpdateTime() != null) SET("`update_time` = #{updateTime}");
            WHERE("id = #{id}");
        }}.toString();
    }

    public String getDictDepartmentList(@Param(value = "name") String name) {
        return new SQL() {{
            SELECT("id, name");
            FROM("t_dict_department");
            WHERE("`enable`=1");
            if (!StringUtil.isEmpty(name)) {
                AND().WHERE("name like concat(concat('%',#{name}),'%')");
            }
        }}.toString();
    }
    public String findAll(@Param(value = "name") String name){
        return new SQL() {{
            SELECT("*");
            FROM("t_dict_department");
            WHERE("`enable`=1");
            if (!StringUtil.isEmpty(name)) {
                AND().WHERE("name like concat(concat('%',#{name}),'%')");
            }
        }}.toString();
    }
}
