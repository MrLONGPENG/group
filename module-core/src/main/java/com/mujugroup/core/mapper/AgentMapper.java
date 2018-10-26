package com.mujugroup.core.mapper;

import com.mujugroup.core.model.Agent;
import com.mujugroup.core.objeck.vo.SelectVO;
import com.mujugroup.core.sql.AgentSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * ,数据库操作接口类
 * 类名:AgentMapper
 *
 * @author leolaurel
 * 创建时间:20180828
 */
@Mapper
@Component(value = "agentMapper")
public interface AgentMapper {
    @InsertProvider(type = AgentSqlProvider.class, method = "insert")
    boolean insert(Agent agent);

    @UpdateProvider(type = AgentSqlProvider.class, method = "update")
    boolean update(Agent agent);

    @Delete("delete from t_agent where id= #{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_agent WHERE id = #{id}")
    @Results(id = "agent", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class)
            , @Result(column = "name", property = "name", javaType = String.class)
            , @Result(column = "crtTime", property = "crtTime", javaType = Date.class)
            , @Result(column = "enable", property = "enable", javaType = Integer.class)
    })
    Agent findById(Integer id);

    @Select("SELECT * FROM t_agent")
    @ResultMap("agent")
    List<Agent> findListAll();

    /**
     * 角色为代理商，且创建者不是代理商的数据
     */
    @Select("SELECT managerid as id ,name FROM t_sys_manager WHERE `role`=6 " +
            "AND `crt_id` NOT IN (SELECT managerid FROM t_sys_manager WHERE `role`=6) " +
            "AND `locked`=1 GROUP BY `name` ORDER BY `name`")
    @Results({@Result(id = true, column = "id", property = "id", javaType = Integer.class)
            , @Result(column = "name", property = "name", javaType = String.class)
    })
    List<SelectVO> getAgentList();

    @Select("SELECT a.id as id,a.name as name From t_agent a ")
    @Results(id = "agentVo", value = {@Result(id = true, column = "id", property = "id", javaType = Integer.class)
            , @Result(column = "name", property = "name", javaType = String.class)
    })
    List<SelectVO> getTheAgentList();

    /**
     * 通过用户ID获取代理商
     */
    @Select("SELECT d.rid as id , a.name as name FROM t_auth_data d, t_agent a" +
            " WHERE d.rid = a.id AND d.`type`=1  AND d.uid = #{uid}")
    @Results(id = "authAgentList", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class),
            @Result(column = "name", property = "name", javaType = String.class)
    })
    List<SelectVO> getAgentListByUid(@Param("uid") long uid);

    @Select("Select d.rid as id, a.name as name  From t_auth_data d, t_hospital a WHERE d.rid = a.id AND d.`type`=2  AND a.enable = 22 AND d.uid = #{uid}")
    @ResultMap("authAgentList")
    List<SelectVO> getAgentHospitalByUid(@Param("uid") long uid);

    @Select("Select COUNT(*) FROM t_agent a,t_hospital b WHERE a.id=b.agentid AND a.id=#{id} limit 1")
    @ResultType(boolean.class)
    boolean exist(@Param("id") int id);

    @Select("SELECT COUNT(*)  FROM t_agent WHERE `name`=#{name}")
    @ResultType(Integer.class)
    Integer isExistName(@Param(value = "name") String name);

    @SelectProvider(type = AgentSqlProvider.class,method = "findAll")
    @ResultMap("agent")
    List<Agent> findAll(@Param(value = "name") String name,@Param(value = "enable")int enable);
}