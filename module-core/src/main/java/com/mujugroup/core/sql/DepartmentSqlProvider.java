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
            if (department.getHid() != null) VALUES("hospital_id", "#{hid}");
            if (department.getName() != null) VALUES("name", "#{name}");
            if (department.getMid() != null) VALUES("mid", "#{mid}");
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
            if (department.getHid() != null) SET("hospital_id = #{hid}");
            if (department.getName() != null) SET("name = #{name}");
            if (department.getMid() != null) SET("mid = #{mid}");
            if (department.getRemark() != null) SET("remark = #{remark}");
            if (department.getSort() != null) SET("sort = #{sort}");
            if (department.getCreateDate() != null) SET("create_date = #{createDate}");
            WHERE("id = #{id}");
        }}.toString();
    }

    public String findAll(@Param(value = "hid") String hid, @Param(value = "name") String name) {
        return new SQL() {{
            SELECT("t.id, h.id AS hid, t.name, IFNULL(dict.name,'') AS department ,h.name AS hospital" +
                    ",t.sort, t.create_date, t.mid AS mid, t.status, t.remark");
            FROM("t_department t");
            LEFT_OUTER_JOIN("t_dict_department dict ON t.mid=dict.id");
            INNER_JOIN("t_hospital h  ON t.hospital_id=h.id");
            if (!StringUtil.isEmpty(name)) {
                AND().WHERE("t.name like concat(concat('%',#{name}),'%')");
            }
            if ((!StringUtil.isEmpty(hid) && hid.contains(Constant.SIGN_DOU_HAO))) {
                AND().WHERE("t.hospital_id in (" + hid + ")");
            }else if (!StringUtil.isEmpty(hid) && !Constant.DIGIT_ZERO.equals(hid)){
                AND().WHERE("t.hospital_id = #{hid}");
            }
        }}.toString();
    }

    public String getSelectList(@Param(value = "hid") int hid, @Param(value = "name") String name) {
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
