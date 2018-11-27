package com.mujugroup.wx.mapper;

import com.mujugroup.wx.model.WxDeposit;
import com.mujugroup.wx.sql.WxDepositSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 押金表,数据库操作接口类
 * 类名:WxDepositMapper
 * @author leolaurel
 * 创建时间:20181127
 */
@Mapper
@Component(value ="wxDepositMapper")
public interface WxDepositMapper {

    @InsertProvider(type = WxDepositSqlProvider.class, method = "insert")
    boolean insert(WxDeposit wxDeposit);

    @UpdateProvider(type = WxDepositSqlProvider.class, method = "update")
    boolean update(WxDeposit wxDeposit);

    @Delete("delete from t_wx_deposit where id= #{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_wx_deposit WHERE id = #{id}")
    @Results(id = "wxDeposit", value = {
         @Result(id=true, column="id",property="id",javaType=Long.class)
             ,@Result(column="gid",property="gid",javaType=Integer.class)
             ,@Result(column="open_id",property="openId",javaType=String.class)
             ,@Result(column="trade_no",property="tradeNo",javaType=String.class)
             ,@Result(column="deposit",property="deposit",javaType=Integer.class)
             ,@Result(column="status",property="status",javaType=Integer.class)
             ,@Result(column="crtTime",property="crtTime",javaType=Date.class)
             ,@Result(column="updTime",property="updTime",javaType=Date.class)
    })
    WxDeposit findById(Integer id);

    @Select("SELECT * FROM t_wx_deposit")
    @ResultMap("wxDeposit")
    List<WxDeposit> findListAll();

}