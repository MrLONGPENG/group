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
@Component(value ="wxUsingMapper")
public interface WxUsingMapper {

    @InsertProvider(type = WxUsingSqlProvider.class, method = "insert")
    boolean insert(WxUsing wxUsing);

    @UpdateProvider(type = WxUsingSqlProvider.class, method = "update")
    boolean update(WxUsing wxUsing);

    @Delete("delete from t_wx_using where id= #{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_wx_using WHERE id = #{id}")
    @Results(id = "wxUsing", value = {
            @Result(id=true, column="id",property="id",javaType=Integer.class)
            ,@Result(column="did",property="did",javaType=Long.class)
            ,@Result(column="open_id",property="openId",javaType=String.class)
            ,@Result(column="pay_cost",property="payCost",javaType=Integer.class)
            ,@Result(column="pay_time",property="payTime",javaType=Long.class)
            ,@Result(column="end_time",property="endTime",javaType=Long.class)
            ,@Result(column="unlock_time",property="unlockTime",javaType=Date.class)
            ,@Result(column="using",property="using",javaType=Boolean.class)
            ,@Result(column="deleted",property="deleted",javaType=Boolean.class)
    })
    WxUsing findById(Integer id);

    @Select("SELECT * FROM t_wx_using limit 1000")
    @ResultMap("wxUsing")
    List<WxUsing> findListAll();


    @Select("SELECT * FROM t_wx_using where open_id = #{openId} and end_time > #{time}")
    @ResultMap("wxUsing")
    List<WxUsing> findUsingByOpenId(@Param("openId") String openId, @Param("time") long time);


    @Select("SELECT * FROM t_wx_using where did = #{did} and end_time > #{time}")
    @ResultMap("wxUsing")
    List<WxUsing> findUsingByDid(@Param("did") String did, @Param("time") long time);


    @Delete("delete from t_wx_using where  did = #{did} and end_time > #{time}")
    boolean deleteByDid(@Param("did") String did, @Param("time") long time);
}