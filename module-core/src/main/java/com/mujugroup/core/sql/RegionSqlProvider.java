package com.mujugroup.core.sql;

import com.mujugroup.core.model.Region;
import org.apache.ibatis.jdbc.SQL;

/**
 * 国家省份城市表,SQL语句组装类
 * 类名:RegionSqlProvider
 * @author leolaurel
 * 创建时间:20180828
 */
public class RegionSqlProvider {

    public String insert(Region countryProvinceCity){
        return new SQL(){{
            INSERT_INTO("t_country_province_city");
            if(countryProvinceCity.getId()!= null) VALUES("`id`", "#{id}");
            if(countryProvinceCity.getName()!= null) VALUES("`name`", "#{name}");
            if(countryProvinceCity.getPid()!= null) VALUES("`pid`", "#{pid}");
            if(countryProvinceCity.getRemark()!= null) VALUES("`remark`", "#{remark}");
            if(countryProvinceCity.getKey()!= null) VALUES("`key`", "#{key}");
            if(countryProvinceCity.getStatus()!= null) VALUES("`status`", "#{status}");
            if(countryProvinceCity.getOrd()!= null) VALUES("`ord`", "#{ord}");
            if(countryProvinceCity.getLongitude()!= null) VALUES("`longitude`", "#{longitude}");
            if(countryProvinceCity.getLatitude()!= null) VALUES("`latitude`", "#{latitude}");
        }}.toString();
    }



    public String update(Region countryProvinceCity){
        return new SQL(){{
            UPDATE("t_country_province_city");
            if(countryProvinceCity.getId()!= null) SET("`id` = #{id}");
            if(countryProvinceCity.getName()!= null) SET("`name` = #{name}");
            if(countryProvinceCity.getPid()!= null) SET("`pid` = #{pid}");
            if(countryProvinceCity.getRemark()!= null) SET("`remark` = #{remark}");
            if(countryProvinceCity.getKey()!= null) SET("`key` = #{key}");
            if(countryProvinceCity.getStatus()!= null) SET("`status` = #{status}");
            if(countryProvinceCity.getOrd()!= null) SET("`ord` = #{ord}");
            if(countryProvinceCity.getLongitude()!= null) SET("`longitude` = #{longitude}");
            if(countryProvinceCity.getLatitude()!= null) SET("`latitude` = #{latitude}");
            WHERE("id = #{id}");
        }}.toString();
    }
}
