package com.mujugroup.wx.mapper;

import com.mujugroup.wx.model.WxOrder;
import com.mujugroup.wx.sql.WxOrderSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 订单信息表,数据库操作接口类
 * 类名:WxOrderMapper
 * 创建人:LEOLAUREL
 * 创建时间:20180703
 */
@Mapper
@Component(value ="wxOrderMapper")
public interface WxOrderMapper {

    @InsertProvider(type = WxOrderSqlProvider.class, method = "insert")
    boolean insert(WxOrder wxOrder);

    @UpdateProvider(type = WxOrderSqlProvider.class, method = "update")
    boolean update(WxOrder wxOrder);

    @Delete("delete from t_wx_order where id= #{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_wx_order WHERE id = #{id}")
    @Results(id = "wxOrder", value = {
         @Result(id=true, column="id",property="id",javaType=Long.class)
            ,@Result(column="did",property="did",javaType=Long.class)
            ,@Result(column="aid",property="aid",javaType=Integer.class)
            ,@Result(column="hid",property="hid",javaType=Integer.class)
            ,@Result(column="oid",property="oid",javaType=Integer.class)
            ,@Result(column="gid",property="gid",javaType=Integer.class)
            ,@Result(column="open_id",property="openId",javaType=String.class)
            ,@Result(column="trade_no",property="tradeNo",javaType=String.class)
            ,@Result(column="transaction_id",property="transactionId",javaType=String.class)
            ,@Result(column="pay_price",property="payPrice",javaType=Integer.class)
            ,@Result(column="pay_status",property="payStatus",javaType=Integer.class)
            ,@Result(column="pay_time",property="payTime",javaType=Long.class)
            ,@Result(column="end_time",property="endTime",javaType=Long.class)
            ,@Result(column="crtTime",property="crtTime",javaType=Date.class)
    })
    WxOrder findById(Integer id);

    @Select("SELECT * FROM t_wx_order limit 1000")
    @ResultMap("wxOrder")
    List<WxOrder> findListAll();


    @Select("SELECT * FROM t_wx_order where trade_no= #{orderNo}")
    @ResultMap("wxOrder")
    WxOrder findByNo(@Param("orderNo")String orderNo);


    @Select("SELECT * FROM t_wx_order where open_id= #{openId} and pay_status= #{status} order by id desc")
    @ResultMap("wxOrder")
    List<WxOrder> findListBySelf(@Param("openId")String openId, @Param("status")Integer status);
}