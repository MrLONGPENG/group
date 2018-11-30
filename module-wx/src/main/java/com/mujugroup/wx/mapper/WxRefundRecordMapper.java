package com.mujugroup.wx.mapper;

import com.mujugroup.wx.model.WxRefundRecord;
import com.mujugroup.wx.sql.WxRefundRecordSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 退款记录表,数据库操作接口类
 * 类名:WxRefundRecordMapper
 * @author leolaurel
 * 创建时间:20181130
 */
@Mapper
@Component(value ="wxRefundRecordMapper")
public interface WxRefundRecordMapper {

    @InsertProvider(type = WxRefundRecordSqlProvider.class, method = "insert")
    boolean insert(WxRefundRecord wxRefundRecord);

    @UpdateProvider(type = WxRefundRecordSqlProvider.class, method = "update")
    boolean update(WxRefundRecord wxRefundRecord);

    @Delete("delete from t_wx_refund_record where id= #{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_wx_refund_record WHERE id = #{id}")
    @Results(id = "wxRefundRecord", value = {
         @Result(id=true, column="id",property="id",javaType=Long.class)
             ,@Result(column="open_id",property="openId",javaType=String.class)
             ,@Result(column="trade_no",property="tradeNo",javaType=String.class)
             ,@Result(column="refund_no",property="refundNo",javaType=String.class)
             ,@Result(column="refund_count",property="refundCount",javaType=Integer.class)
             ,@Result(column="refundDesc",property="refundDesc",javaType=String.class)
             ,@Result(column="refund_price",property="refundPrice",javaType=Integer.class)
             ,@Result(column="total_price",property="totalPrice",javaType=Integer.class)
             ,@Result(column="refund_status",property="refundStatus",javaType=Integer.class)
             ,@Result(column="crtTime",property="crtTime",javaType=Date.class)
    })
    WxRefundRecord findById(Integer id);

    @Select("SELECT * FROM t_wx_refund_record")
    @ResultMap("wxRefundRecord")
    List<WxRefundRecord> findListAll();

}