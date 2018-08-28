package com.mujugroup.core.mapper;

import com.mujugroup.core.model.Agent;
import com.mujugroup.core.objeck.vo.SelectVO;
import com.mujugroup.core.sql.AgentProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * ,数据库操作接口类
 * 类名:AgentMapper
 * @author leolaurel
 * 创建时间:20180828
 */
@Mapper
@Component(value ="agentMapper")
public interface AgentMapper {

    @InsertProvider(type = AgentProvider.class, method = "insert")
    boolean insert(Agent sysManager);

    @UpdateProvider(type = AgentProvider.class, method = "update")
    boolean update(Agent sysManager);

    @Delete("delete from t_sys_manager where id= #{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_sys_manager WHERE id = #{id}")
    @Results(id = "sysManager", value = {
            @Result(column="managerid",property="managerid",javaType=Integer.class)
             ,@Result(column="account",property="account",javaType=String.class)
             ,@Result(column="password",property="password",javaType=String.class)
             ,@Result(column="role",property="role",javaType=Integer.class)
             ,@Result(column="name",property="name",javaType=String.class)
             ,@Result(column="lastloginip",property="lastloginip",javaType=String.class)
             ,@Result(column="lastlogintime",property="lastlogintime",javaType=Date.class)
             ,@Result(column="credentialsSalt",property="credentialsSalt",javaType=String.class)
             ,@Result(column="locked",property="locked",javaType=String.class)
             ,@Result(column="email",property="email",javaType=String.class)
             ,@Result(column="phone",property="phone",javaType=String.class)
             ,@Result(column="sex",property="sex",javaType=String.class)
             ,@Result(column="type",property="type",javaType=String.class)
             ,@Result(column="photo",property="photo",javaType=String.class)
             ,@Result(column="crt_id",property="crtId",javaType=Integer.class)
             ,@Result(column="crt_time",property="crtTime",javaType=Date.class)
             ,@Result(column="hospitalId",property="hospitalId",javaType=Integer.class)
             ,@Result(column="countryId",property="countryId",javaType=Integer.class)
             ,@Result(column="proId",property="proId",javaType=Integer.class)
             ,@Result(column="cityId",property="cityId",javaType=Integer.class)
    })
    Agent findById(Integer id);

    @Select("SELECT * FROM t_sys_manager")
    @ResultMap("sysManager")
    List<Agent> findListAll();


    /**
     * 角色为代理商，且创建者不是代理商的数据
     */
    @Select("SELECT managerid as id ,name FROM t_sys_manager WHERE `role`=6 " +
            "AND `crt_id` NOT IN (SELECT managerid FROM t_sys_manager WHERE `role`=6) " +
            "AND `locked`=1 GROUP BY `name` ORDER BY `name`")
    @Results({@Result(id=true, column="id",property="id",javaType=Integer.class)
            ,@Result(column="name",property="name",javaType=String.class)
    })
    List<SelectVO> getAgentList();
}