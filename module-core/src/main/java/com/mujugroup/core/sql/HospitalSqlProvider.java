package com.mujugroup.core.sql;

import com.lveqia.cloud.common.util.StringUtil;
import com.lveqia.cloud.common.config.Constant;
import com.mujugroup.core.model.Hospital;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * 医院信息表,SQL语句组装类
 * 类名:HospitalSqlProvider
 * 创建人:LEOLAUREL
 * 创建时间:20180627
 */
public class HospitalSqlProvider {

    public String insert(Hospital hospital){
        return new SQL(){{
            INSERT_INTO("t_hospital");
            if(hospital.getId()!= null) VALUES("id", "#{id}");
            if(hospital.getName()!= null) VALUES("name", "#{name}");
            if(hospital.getAgentId()!= null) VALUES("agentId", "#{agentId}");
            if(hospital.getTel()!= null) VALUES("tel", "#{tel}");
            if(hospital.getPerson()!= null) VALUES("person", "#{person}");
            if(hospital.getRemark()!= null) VALUES("remark", "#{remark}");
            if(hospital.getCrtTime()!= null) VALUES("crtTime", "#{crtTime}");
            if(hospital.getCrtId()!= null) VALUES("crtId", "#{crtId}");
            if(hospital.getAddress()!= null) VALUES("address", "#{address}");
            if(hospital.getCountry()!= null) VALUES("country", "#{country}");
            if(hospital.getProvince()!= null) VALUES("province", "#{province}");
            if(hospital.getCity()!= null) VALUES("city", "#{city}");
            if(hospital.getLongitude()!= null) VALUES("longitude", "#{longitude}");
            if(hospital.getLatitude()!= null) VALUES("latitude", "#{latitude}");
            if(hospital.getEnable()!= null) VALUES("enable", "#{enable}");
            if(hospital.getIssync()!= null) VALUES("issync", "#{issync}");
            if(hospital.getLevel()!= null) VALUES("level", "#{level}");
        }}.toString();
    }



    public String update(Hospital hospital){
        return new SQL(){{
            UPDATE("t_hospital");
            if(hospital.getId()!= null) SET("id = #{id}");
            if(hospital.getName()!= null) SET("name = #{name}");
            if(hospital.getAgentId()!= null) SET("agentId = #{agentId}");
            if(hospital.getTel()!= null) SET("tel = #{tel}");
            if(hospital.getPerson()!= null) SET("person = #{person}");
            if(hospital.getRemark()!= null) SET("remark = #{remark}");
            if(hospital.getCrtTime()!= null) SET("crtTime = #{crtTime}");
            if(hospital.getCrtId()!= null) SET("crtId = #{crtId}");
            if(hospital.getAddress()!= null) SET("address = #{address}");
            if(hospital.getCountry()!= null) SET("country = #{country}");
            if(hospital.getProvince()!= null) SET("province = #{province}");
            if(hospital.getCity()!= null) SET("city = #{city}");
            if(hospital.getLongitude()!= null) SET("longitude = #{longitude}");
            if(hospital.getLatitude()!= null) SET("latitude = #{latitude}");
            if(hospital.getEnable()!= null) SET("enable = #{enable}");
            if(hospital.getIssync()!= null) SET("issync = #{issync}");
            if(hospital.getLevel()!= null) SET("level = #{level}");
            WHERE("id = #{id}");
        }}.toString();
    }

   /* public String getHospitalList(@Param("aid") int aid, @Param("name") String name){
        return new SQL(){{
            SELECT("id, name");
            FROM("t_hospital");
            WHERE("enable = 22");
            if(aid!=0) AND().WHERE("agentId = #{aid}");
            if(!StringUtil.isEmpty(name) && name.contains(Constant.SIGN_PERCENT)){
                AND().WHERE("name like %#{name}%");
            }else if(!StringUtil.isEmpty(name)){
                AND().WHERE("name = #{name}");
            }
        }}.toString();
    }*/
   public String getHospitalList(@Param("aid") int aid, @Param("name") String name){
       return new SQL(){{
           SELECT("id, name");
           FROM("t_hospital");
           WHERE("enable = 22");
           if(aid!=0) AND().WHERE("agentId = #{aid}");
           if(!StringUtil.isEmpty(name) &&name.indexOf(name)!=-1){
               AND().WHERE("name like concat(concat('%',#{name}),'%')");
           }else if(!StringUtil.isEmpty(name)){
               AND().WHERE("name = #{name}");
           }
       }}.toString();
   }
}
