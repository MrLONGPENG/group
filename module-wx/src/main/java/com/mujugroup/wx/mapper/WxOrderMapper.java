package com.mujugroup.wx.mapper;

import com.lveqia.cloud.common.objeck.DBMap;
import com.lveqia.cloud.common.objeck.DBObj;
import com.lveqia.cloud.common.objeck.to.OrderTo;
import com.lveqia.cloud.common.objeck.to.PayInfoTo;
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
@Component(value = "wxOrderMapper")
public interface WxOrderMapper {

    @InsertProvider(type = WxOrderSqlProvider.class, method = "insert")
    boolean insert(WxOrder wxOrder);

    @UpdateProvider(type = WxOrderSqlProvider.class, method = "update")
    boolean update(WxOrder wxOrder);

    @Delete("delete from t_wx_order where id= #{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_wx_order WHERE id = #{id}")
    @Results(id = "wxOrder", value = {
            @Result(id = true, column = "id", property = "id", javaType = Long.class)
            , @Result(column = "did", property = "did", javaType = Long.class)
            , @Result(column = "aid", property = "aid", javaType = Integer.class)
            , @Result(column = "hid", property = "hid", javaType = Integer.class)
            , @Result(column = "oid", property = "oid", javaType = Integer.class)
            , @Result(column = "gid", property = "gid", javaType = Integer.class)
            , @Result(column = "open_id", property = "openId", javaType = String.class)
            , @Result(column = "trade_no", property = "tradeNo", javaType = String.class)
            , @Result(column = "transaction_id", property = "transactionId", javaType = String.class)
            , @Result(column = "order_type", property = "orderType", javaType = Integer.class)
            , @Result(column = "pay_price", property = "payPrice", javaType = Integer.class)
            , @Result(column = "pay_status", property = "payStatus", javaType = Integer.class)
            , @Result(column = "pay_time", property = "payTime", javaType = Long.class)
            , @Result(column = "end_time", property = "endTime", javaType = Long.class)
            , @Result(column = "crtTime", property = "crtTime", javaType = Date.class)
    })
    WxOrder findById(Integer id);

    @ResultMap("wxOrder")
    @Select("SELECT * FROM t_wx_order WHERE did = #{did} AND pay_status = 2 ORDER BY ID DESC LIMIT 1")
    WxOrder findLastOrderByDid(@Param("did") String did);


    @Select("SELECT * FROM t_wx_order")
    @ResultMap("wxOrder")
    List<WxOrder> findListAll();


    @Select("SELECT * FROM t_wx_order where trade_no= #{orderNo}")
    @ResultMap("wxOrder")
    WxOrder findByNo(@Param("orderNo") String orderNo);


    @Select("SELECT * FROM t_wx_order where open_id= #{openId} and pay_status= #{status} order by id desc")
    @ResultMap("wxOrder")
    List<WxOrder> findListBySelf(@Param("openId") String openId, @Param("status") Integer status);


    @Select("SELECT hid, count(DISTINCT did) as count FROM t_wx_order where aid= #{aid}" +
            " AND pay_status = 2 AND pay_time > #{payTime} group by hid")
    @Results({@Result(column = "hid", property = "key", javaType = String.class)
            , @Result(column = "count", property = "value", javaType = String.class)})
    List<DBMap> getPayCountByAid(@Param("aid") String aid, @Param("payTime") long payTime);


    @Select("SELECT oid, count(DISTINCT did) as count FROM t_wx_order WHERE aid= #{aid} AND hid= #{hid}" +
            " AND pay_status = 2 AND pay_time > #{payTime} group by oid")
    @Results({@Result(column = "oid", property = "key", javaType = String.class)
            , @Result(column = "count", property = "value", javaType = String.class)})
    List<DBMap> getPayCountByHid(@Param("aid") String aid, @Param("hid") String hid, @Param("payTime") long payTime);


    @SelectProvider(type = WxOrderSqlProvider.class, method = "getUsageCount")
    @Results({@Result(column = "count1", property = "count1", javaType = Integer.class)
            , @Result(column = "count2", property = "count2", javaType = Integer.class)})
    DBObj getUsageCount(@Param("aid") String aid, @Param("hid") String hid, @Param("oid") String oid
            , @Param("orderType") int orderType, @Param("start") long start, @Param("end") long end
            , @Param("usage") long usage);


    @ResultMap("wxOrder")
    @SelectProvider(type = WxOrderSqlProvider.class, method = "findList")
    List<WxOrder> findList(@Param("aid") String aid, @Param("hid") String hid, @Param("oid") String oid
            , @Param("start") long start, @Param("end") long end, @Param("tradeNo") String tradeNo
            , @Param("orderType") int orderType
            , @Param("did") String did);

    @ResultType(String.class)
    @SelectProvider(type = WxOrderSqlProvider.class, method = "getTotalProfit")
    String getTotalProfit(@Param("aid") String aid, @Param("hid") String hid, @Param("oid") String oid
            , @Param("did") String did, @Param("tradeNo") String tradeNo, @Param("orderType") int orderType
            , @Param("start") long start, @Param("end") long end);

    @ResultType(String.class)
    @Select("SELECT `end_time` endTime  FROM t_wx_order WHERE did = #{did} AND pay_status = 2 ORDER BY ID DESC LIMIT 1")
    String getOrderEndTimeByDid(String did);

    @SelectProvider(type = WxOrderSqlProvider.class, method = "getPayInfoByDid")
    PayInfoTo getPayInfoByDid(@Param(value = "did") String did, @Param(value = "type") int orderType);

    @Select("SELECT * FROM t_wx_order WHERE open_id= #{openId} and pay_status= #{status} and trade_no= #{orderNo} order by id desc")
    @ResultMap("wxOrder")
    WxOrder getOrderByOpenidAndTradeNo(@Param("openId") String openId, @Param("status") Integer status, @Param("orderNo") String orderNo);

    @Select("SELECT * FROM t_wx_order WHERE open_id= #{open_id} ORDER BY id DESC LIMIT 1")
    WxOrder getLastOrderByOpenId(@Param(value = "open_id") String openId);

    @Select("SELECT * FROM t_wx_order " +
            " WHERE pay_status = 2 AND did= #{did}   ORDER BY id DESC LIMIT 1")
    @Results(id = "OrderTo", value = {
            @Result(column = "did", property = "did", javaType = String.class)
            , @Result(column = "aid", property = "aid", javaType = String.class)
            , @Result(column = "hid", property = "hid", javaType = String.class)
            , @Result(column = "oid", property = "oid", javaType = String.class)
            , @Result(column = "gid", property = "gid", javaType = String.class)
            , @Result(column = "open_id", property = "openId", javaType = String.class)
            , @Result(column = "trade_no", property = "tradeNo", javaType = String.class)
            , @Result(column = "order_type", property = "orderType", javaType = Integer.class)
            , @Result(column = "pay_price", property = "payPrice", javaType = Integer.class)
            , @Result(column = "pay_status", property = "payStatus", javaType = Integer.class)
            , @Result(column = "pay_time", property = "payTime", javaType = Long.class)
            , @Result(column = "end_time", property = "endTime", javaType = Long.class)
    })
    OrderTo getOrderByCondition(@Param(value = "did") long did);
}