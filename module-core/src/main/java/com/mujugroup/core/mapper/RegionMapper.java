package com.mujugroup.core.mapper;

import com.mujugroup.core.model.Region;
import com.mujugroup.core.objeck.vo.SelectVo;
import com.mujugroup.core.sql.RegionSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 国家省份城市表,数据库操作接口类
 * 类名:RegionMapper
 * @author leolaurel
 * 创建时间:20180828
 */
@Mapper
@Component(value ="regionMapper")
public interface RegionMapper {

    @InsertProvider(type = RegionSqlProvider.class, method = "insert")
    boolean insert(Region countryProvinceCity);

    @UpdateProvider(type = RegionSqlProvider.class, method = "update")
    boolean update(Region countryProvinceCity);

    @Delete("delete from t_country_province_city where id= #{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_country_province_city WHERE id = #{id}")
    @Results(id = "countryProvinceCity", value = {
         @Result(id=true, column="id",property="id",javaType=Integer.class)
             ,@Result(column="name",property="name",javaType=String.class)
             ,@Result(column="pid",property="pid",javaType=Integer.class)
             ,@Result(column="remark",property="remark",javaType=String.class)
             ,@Result(column="key",property="key",javaType=String.class)
             ,@Result(column="status",property="status",javaType=Integer.class)
             ,@Result(column="ord",property="ord",javaType=Integer.class)
             ,@Result(column="longitude",property="longitude",javaType=Double.class)
             ,@Result(column="latitude",property="latitude",javaType=Double.class)
    })
    Region findById(Integer id);

    @Select("SELECT * FROM t_country_province_city")
    @ResultMap("countryProvinceCity")
    List<Region> findListAll();



    @Select("SELECT * FROM t_country_province_city WHERE status=1 AND pid = #{pid}")
    @Results({@Result(id=true, column="id",property="id",javaType=Integer.class)
            ,@Result(column="name",property="name",javaType=String.class)
    })
    List<SelectVo> getRegionByPid(@Param("pid") int pid);
}