package com.mujugroup.wx.mapper;

import com.mujugroup.wx.model.WxRepair;
import com.mujugroup.wx.sql.WxRepairSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 保修信息表,数据库操作接口类
 * 类名:WxRepairMapper
 * 创建人:LEOLAUREL
 * 创建时间:20180707
 */
@Mapper
@Component(value ="wxRepairMapper")
public interface WxRepairMapper {

    @InsertProvider(type = WxRepairSqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    boolean insert(WxRepair wxRepair);

    @UpdateProvider(type = WxRepairSqlProvider.class, method = "update")
    boolean update(WxRepair wxRepair);

    @Delete("delete from t_wx_repair where id= #{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_wx_repair WHERE id = #{id}")
    @Results(id = "wxRepair", value = {
         @Result(id=true, column="id",property="id",javaType=Integer.class)
             ,@Result(column="did",property="did",javaType=Long.class)
             ,@Result(column="open_id",property="openId",javaType=String.class)
             ,@Result(column="fault_cause",property="faultCause",javaType=String.class)
             ,@Result(column="fault_describe",property="faultDescribe",javaType=String.class)
             ,@Result(column="restorer",property="restorer",javaType=String.class)
             ,@Result(column="repair_status",property="repairStatus",javaType=Integer.class)
             ,@Result(column="crtTime",property="crtTime",javaType=Date.class)
             ,@Result(column="updTime",property="updTime",javaType=Date.class)
    })
    WxRepair findById(Integer id);

    @Select("SELECT * FROM t_wx_repair limit 1000")
    @ResultMap("wxRepair")
    List<WxRepair> findListAll();

}