package com.mujugroup.core.mapper;

import com.lveqia.cloud.common.objeck.DBMap;
import com.mujugroup.core.model.AuthData;
import com.mujugroup.core.objeck.bo.TreeBO;
import com.mujugroup.core.sql.AuthDataSqlProvider;
import com.mujugroup.core.sql.DeviceSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 数据权限关系表,数据库操作接口类
 * 类名:AuthDataMapper
 *
 * @author leolaurel
 * 创建时间:20180927
 */
@Mapper
@Component(value = "authDataMapper")
public interface AuthDataMapper {

    @InsertProvider(type = AuthDataSqlProvider.class, method = "insert")
    boolean insert(AuthData authData);

    @InsertProvider(type = AuthDataSqlProvider.class, method = "addAuthData")
    int addAuthData(@Param("uid") int uid, @Param("ids") int[] rid, @Param("types") int[] types);

    @UpdateProvider(type = AuthDataSqlProvider.class, method = "update")
    boolean update(AuthData authData);

    @Delete("delete from t_auth_data where id= #{id}")
    boolean deleteById(int id);

    @Delete("delete from t_auth_data where uid= #{uid}")
    int deleteByUid(@Param("uid") long uid);

    @Select("SELECT * FROM t_auth_data WHERE id = #{id}")
    @Results(id = "authData", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class)
            , @Result(column = "uid", property = "uid", javaType = Integer.class)
            , @Result(column = "rid", property = "rid", javaType = Integer.class)
            , @Result(column = "type", property = "type", javaType = Integer.class)
    })
    AuthData findById(Integer id);

    @Select("SELECT * FROM t_auth_data")
    @ResultMap("authData")
    List<AuthData> findListAll();


    @Results(id = "treeVO", value = {@Result(id = true, column = "id", property = "id", javaType = String.class)
            , @Result(column = "id", property = "children", javaType = String.class)
            , @Result(column = "name", property = "name", javaType = String.class)})
    @Select("SELECT CONCAT('AID',a.id) as id, a.name as name FROM t_agent a  WHERE a.`enable`= 1")
    List<TreeBO> getAllAgentList();

    @ResultMap("treeVO")
    @Select("SELECT CONCAT('AID',d.rid) as id, a.name as name FROM t_auth_data d, t_agent a" +
            " WHERE d.rid = a.id AND d.`type`=1  AND d.uid = #{uid}")
    List<TreeBO> getAgentAuthData(@Param("uid") long uid);

    @ResultMap("treeVO")
    @Select("SELECT CONCAT('HID',d.rid) as id, h.name as name FROM t_auth_data d, t_hospital h" +
            " WHERE d.rid = h.id AND d.`type`=2  AND d.uid = #{uid}")
    List<TreeBO> getHospitalAuthData(@Param("uid") long uid);


    @ResultMap("treeVO")
    @Select("SELECT CONCAT('OID',d.rid) as id, o.name as name FROM t_auth_data d, t_department o" +
            " WHERE d.rid = o.id AND d.`type`=3  AND d.uid = #{uid}")
    List<TreeBO> getDepartmentAuthData(@Param("uid") long uid);


    @ResultMap("treeVO")
    @Select("SELECT CONCAT('HID',id) as id, name FROM t_hospital WHERE `enable`=22  AND `agentId` = #{aid}")
    List<TreeBO> getAuthTreeByAid(@Param("aid") String aid);


    @ResultMap("treeVO")
    @Select("SELECT CONCAT('OID',id) as id, name FROM t_department WHERE `hospital_id` = #{hid}")
    List<TreeBO> getAuthTreeByHid(@Param("hid") String hid);


    @Results({@Result(column="key", property="key", javaType=String.class)
            , @Result(column="value", property="value", javaType=String.class)})
    @Select("select `type` as `key`,group_concat(`rid`) as `value` from `t_auth_data`" +
            " WHERE uid=#{uid} group by `type`")
    List<DBMap> getAuthData(@Param("uid") int uid);


}