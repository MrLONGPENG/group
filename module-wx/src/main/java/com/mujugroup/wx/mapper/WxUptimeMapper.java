package com.mujugroup.wx.mapper;

import com.mujugroup.wx.model.WxUptime;
import com.mujugroup.wx.sql.WxUptimeSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * 木巨柜运行时间表,数据库操作接口类
 * 类名:WxUptimeMapper
 * @author leolaurel
 * 创建时间:20180712
 */
@Mapper
@Component(value ="wxUptimeMapper")
public interface WxUptimeMapper {

    @InsertProvider(type = WxUptimeSqlProvider.class, method = "insert")
    boolean insert(WxUptime wxUptime);

    @UpdateProvider(type = WxUptimeSqlProvider.class, method = "update")
    boolean update(WxUptime wxUptime);

    @Delete("delete from t_wx_uptime where id= #{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_wx_uptime WHERE id = #{id}")
    @Results(id = "wxUptime", value = {
         @Result(id=true, column="id",property="id",javaType=Integer.class)
             ,@Result(column="start_time",property="startTime",javaType=Integer.class)
             ,@Result(column="stop_time",property="stopTime",javaType=Integer.class)
             ,@Result(column="start_desc",property="startDesc",javaType=String.class)
             ,@Result(column="stop_desc",property="stopDesc",javaType=String.class)
             ,@Result(column="explain",property="explain",javaType=String.class)
    })
    WxUptime findById(Integer id);


    @ResultMap("wxUptime")
    @Select("SELECT * FROM t_wx_uptime limit 1000")
    List<WxUptime> findListAll();


    @ResultMap("wxUptime")
    @SelectProvider(type = WxUptimeSqlProvider.class, method = "findListByRelation")
    List<WxUptime> findListByRelation(@Param("key") Integer key, @Param("kid") Integer kid);

}