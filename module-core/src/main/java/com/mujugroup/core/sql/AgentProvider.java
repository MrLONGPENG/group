package com.mujugroup.core.sql;

import com.mujugroup.core.model.Agent;
import org.apache.ibatis.jdbc.SQL;

/**
 * ,SQL语句组装类
 * 类名:AgentProvider
 * @author leolaurel
 * 创建时间:20180828
 */
public class AgentProvider {

    public String insert(Agent sysManager){
        return new SQL(){{
            INSERT_INTO("t_sys_manager");
            if(sysManager.getManagerid()!= null) VALUES("`managerid`", "#{managerid}");
            if(sysManager.getAccount()!= null) VALUES("`account`", "#{account}");
            if(sysManager.getPassword()!= null) VALUES("`password`", "#{password}");
            if(sysManager.getRole()!= null) VALUES("`role`", "#{role}");
            if(sysManager.getName()!= null) VALUES("`name`", "#{name}");
            if(sysManager.getLastloginip()!= null) VALUES("`lastloginip`", "#{lastloginip}");
            if(sysManager.getLastlogintime()!= null) VALUES("`lastlogintime`", "#{lastlogintime}");
            if(sysManager.getCredentialsSalt()!= null) VALUES("`credentialsSalt`", "#{credentialsSalt}");
            if(sysManager.getLocked()!= null) VALUES("`locked`", "#{locked}");
            if(sysManager.getEmail()!= null) VALUES("`email`", "#{email}");
            if(sysManager.getPhone()!= null) VALUES("`phone`", "#{phone}");
            if(sysManager.getSex()!= null) VALUES("`sex`", "#{sex}");
            if(sysManager.getType()!= null) VALUES("`type`", "#{type}");
            if(sysManager.getPhoto()!= null) VALUES("`photo`", "#{photo}");
            if(sysManager.getCrtId()!= null) VALUES("`crt_id`", "#{crtId}");
            if(sysManager.getCrtTime()!= null) VALUES("`crt_time`", "#{crtTime}");
            if(sysManager.getHospitalId()!= null) VALUES("`hospitalId`", "#{hospitalId}");
            if(sysManager.getCountryId()!= null) VALUES("`countryId`", "#{countryId}");
            if(sysManager.getProId()!= null) VALUES("`proId`", "#{proId}");
            if(sysManager.getCityId()!= null) VALUES("`cityId`", "#{cityId}");
        }}.toString();
    }



    public String update(Agent sysManager){
        return new SQL(){{
            UPDATE("t_sys_manager");
            if(sysManager.getManagerid()!= null) SET("`managerid` = #{managerid}");
            if(sysManager.getAccount()!= null) SET("`account` = #{account}");
            if(sysManager.getPassword()!= null) SET("`password` = #{password}");
            if(sysManager.getRole()!= null) SET("`role` = #{role}");
            if(sysManager.getName()!= null) SET("`name` = #{name}");
            if(sysManager.getLastloginip()!= null) SET("`lastloginip` = #{lastloginip}");
            if(sysManager.getLastlogintime()!= null) SET("`lastlogintime` = #{lastlogintime}");
            if(sysManager.getCredentialsSalt()!= null) SET("`credentialsSalt` = #{credentialsSalt}");
            if(sysManager.getLocked()!= null) SET("`locked` = #{locked}");
            if(sysManager.getEmail()!= null) SET("`email` = #{email}");
            if(sysManager.getPhone()!= null) SET("`phone` = #{phone}");
            if(sysManager.getSex()!= null) SET("`sex` = #{sex}");
            if(sysManager.getType()!= null) SET("`type` = #{type}");
            if(sysManager.getPhoto()!= null) SET("`photo` = #{photo}");
            if(sysManager.getCrtId()!= null) SET("`crt_id` = #{crtId}");
            if(sysManager.getCrtTime()!= null) SET("`crt_time` = #{crtTime}");
            if(sysManager.getHospitalId()!= null) SET("`hospitalId` = #{hospitalId}");
            if(sysManager.getCountryId()!= null) SET("`countryId` = #{countryId}");
            if(sysManager.getProId()!= null) SET("`proId` = #{proId}");
            if(sysManager.getCityId()!= null) SET("`cityId` = #{cityId}");
            WHERE("id = #{id}");
        }}.toString();
    }
}
