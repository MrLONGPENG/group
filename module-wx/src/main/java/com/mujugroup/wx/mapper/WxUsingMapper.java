package com.mujugroup.wx.mapper;

import com.mujugroup.wx.model.WxUsing;
import com.mujugroup.wx.sql.WxUsingSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 使用支付记录表,数据库操作接口类
 * 类名:WxUsingMapper
 * 创建人:LEOLAUREL
 * 创建时间:20180703
 */
@Mapper
@Component(value = "wxUsingMapper")
public interface WxUsingMapper {

    @InsertProvider(type = WxUsingSqlProvider.class, method = "insert")
    boolean insert(WxUsing wxUsing);

    @UpdateProvider(type = WxUsingSqlProvider.class, method = "update")
    boolean update(WxUsing wxUsing);

    @Delete("delete from t_wx_using where id= #{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_wx_using WHERE `deleted`=0 AND id = #{id}")
    @Results(id = "wxUsing", value = {
            @Result(id = true, column = "id", property = "id", javaType = Integer.class)
            , @Result(column = "did", property = "did", javaType = Long.class)
            , @Result(column = "open_id", property = "openId", javaType = String.class)
            , @Result(column = "pay_cost", property = "payCost", javaType = Integer.class)
            , @Result(column = "pay_time", property = "payTime", javaType = Long.class)
            , @Result(column = "end_time", property = "endTime", javaType = Long.class)
            , @Result(column = "unlock_time", property = "unlockTime", javaType = Date.class)
            , @Result(column = "using", property = "using", javaType = Boolean.class)
            , @Result(column = "deleted", property = "deleted", javaType = Boolean.class)
    })
    WxUsing findById(Integer id);

    @Select("SELECT * FROM t_wx_using WHERE `deleted`=0 limit 1000")
    @ResultMap("wxUsing")
    List<WxUsing> findListAll();


    @Select("SELECT * FROM t_wx_using WHERE `deleted`=0 AND open_id = #{openId} AND end_time > #{time}")
    @ResultMap("wxUsing")
    List<WxUsing> findUsingByOpenId(@Param("openId") String openId, @Param("time") long time);


    @Select("SELECT * FROM t_wx_using WHERE `deleted`=0 AND did = #{did} AND end_time > #{time}")
    @ResultMap("wxUsing")
    List<WxUsing> findUsingByDid(@Param("did") String did, @Param("time") long time);


    @Update("UPDATE t_wx_using SET `deleted`=1 WHERE did = #{did} and end_time > #{time}")
    boolean deleteByDid(@Param("did") String did, @Param("time") long time);

    @Select("SELECT COUNT(*) FROM t_wx_using WHERE `deleted`=0 AND did = #{did} AND end_time > #{time}")
    @ResultType(int.class)
    int getCountByUsingDid(@Param("did") String did, @Param("time") long time);

    @Select("SELECT * FROM t_wx_using WHERE `deleted`=0 AND open_id= #{openId} AND did = #{did} AND pay_time = #{payTime} LIMIT 1 ")
    @ResultMap("wxUsing")
    WxUsing getWxUsingByDidAndPayTime(@Param(value = "openId") String openId, @Param(value = "did") String did, @Param(value = "payTime") long payTime);
}