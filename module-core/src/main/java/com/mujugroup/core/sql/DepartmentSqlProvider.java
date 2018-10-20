package com.mujugroup.core.sql;

import com.lveqia.cloud.common.config.Constant;
import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.core.model.Department;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * 科室表,SQL语句组装类
 * 类名:DepartmentSqlProvider
 * 创建人:LEOLAUREL
 * 创建时间:20180627
 */
public class DepartmentSqlProvider {

    public String insert(Department department) {
        return new SQL() {{
            INSERT_INTO("t_department");
            if (department.getId() != null) VALUES("id", "#{id}");
            if (department.getStatus() != null) VALUES("status", "#{status}");
            if (department.getHospitalId() != null) VALUES("hospital_id", "#{hospitalId}");
            if (department.getName() != null) VALUES("name", "#{name}");
            if (department.getAihuiDepartId() != null) VALUES("aihui_depart_id", "#{aihuiDepartId}");
            if (department.getRemark() != null) VALUES("remark", "#{remark}");
            if (department.getSort() != null) VALUES("sort", "#{sort}");
            if (department.getCreateDate() != null) VALUES("create_date", "#{createDate}");
        }}.toString();
    }


    public String update(Department department) {
        return new SQL() {{
            UPDATE("t_department");
            if (department.getId() != null) SET("id = #{id}");
            if (department.getStatus() != null) SET("status = #{status}");
            if (department.getHospitalId() != null) SET("hospital_id = #{hospitalId}");
            if (department.getName() != null) SET("name = #{name}");
            if (department.getAihuiDepartId() != null) SET("aihui_depart_id = #{aihuiDepartId}");
            if (department.getRemark() != null) SET("remark = #{remark}");
            if (department.getSort() != null) SET("sort = #{sort}");
            if (department.getCreateDate() != null) SET("create_date = #{createDate}");
            WHERE("id = #{id}");
        }}.toString();
    }

    public String getDepartmentList(@Param("name") String name) {
        return new SQL() {{
            SELECT("id,`name`");
            FROM("t_aihui_department");
            WHERE("`enable`=22");
            if(!StringUtil.isEmpty(name) &&name.indexOf(name)!=-1){
                AND().WHERE("name like concat(concat('%',#{name}),'%')");
            }else if(!StringUtil.isEmpty(name)){
                AND().WHERE("name = #{name}");
            }
        }}.toString();
    }

    public String getListByHidOrName(@Param(value = "hid") String hid, @Param(value = "name") String name) {
        return new SQL() {{
            SELECT(" id ,`name`");
            FROM("t_department");
            WHERE("`status`= 1");
            AND().WHERE("hospital_id = #{hid}");
            if(!StringUtil.isEmpty(name) &&name.indexOf(name)!=-1){
                AND().WHERE("name like concat(concat('%',#{name}),'%')");
            }else if(!StringUtil.isEmpty(name)){
                AND().WHERE("name = #{name}");
            }
        }}.toString();

    }
}
