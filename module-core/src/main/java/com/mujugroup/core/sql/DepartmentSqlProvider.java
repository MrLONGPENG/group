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
    public String findAll(@Param(value = "hid") int hid, @Param(value = "name") String name) {
        return new SQL() {{
            SELECT("t.id,  t.hospital_id ,t.name ,dict.name AS mujuName ,h.name AS hospitalName,t.sort,t.create_date,dict.id AS moid,t.status,t.remark");
            FROM("t_department t,t_dict_department dict,t_hospital h");
            WHERE("t.aihui_depart_id=dict.id AND t.hospital_id=h.id");
            if (!StringUtil.isEmpty(name)) {
                AND().WHERE("t.name like concat(concat('%',#{name}),'%')");
            }
            if (hid != 0) {
                AND().WHERE("t.hospital_id = #{hid}");
            }
        }}.toString();
    }

    public String getSelectList(@Param(value = "hid") int hid, @Param(value = "name") String name){
        return new SQL() {{
            SELECT("id, name");
            FROM("t_department");
            WHERE("`status` = 1");
            if (!StringUtil.isEmpty(name)) {
                AND().WHERE("name like concat(concat('%',#{name}),'%')");
            }
            if (hid != 0) {
                AND().WHERE("hospital_id = #{hid}");
            }
        }}.toString();
    }
}
