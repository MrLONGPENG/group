package com.mujugroup.wx.mapper;

import com.mujugroup.wx.model.WxRecordMain;
import com.mujugroup.wx.objeck.vo.recordMain.ListVo;
import com.mujugroup.wx.sql.WxRecordMainSqlProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 支付记录主表,数据库操作接口类
 * 类名:WxRecordMainMapper
 *
 * @author leolaurel
 * 创建时间:20181127
 */
@Mapper
@Component(value = "wxRecordMainMapper")
public interface WxRecordMainMapper {

    @InsertProvider(type = WxRecordMainSqlProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    boolean insert(WxRecordMain wxRecordMain);

    @UpdateProvider(type = WxRecordMainSqlProvider.class, method = "update")
    boolean update(WxRecordMain wxRecordMain);

    @Delete("delete from t_wx_record_main where id= #{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_wx_record_main WHERE id = #{id}")
    @Results(id = "wxRecordMain", value = {
            @Result(id = true, column = "id", property = "id", javaType = Long.class)
            , @Result(column = "did", property = "did", javaType = Long.class)
            , @Result(column = "aid", property = "aid", javaType = Integer.class)
            , @Result(column = "hid", property = "hid", javaType = Integer.class)
            , @Result(column = "oid", property = "oid", javaType = Integer.class)
            , @Result(column = "open_id", property = "openId", javaType = String.class)
            , @Result(column = "trade_no", property = "tradeNo", javaType = String.class)
            , @Result(column = "transaction_id", property = "transactionId", javaType = String.class)
            , @Result(column = "total_price", property = "totalPrice", javaType = Integer.class)
            , @Result(column = "refund_count", property = "refundCount", javaType = Integer.class)
            , @Result(column = "refund_price", property = "refundPrice", javaType = Integer.class)
            , @Result(column = "pay_status", property = "payStatus", javaType = Integer.class)
            , @Result(column = "crtTime", property = "crtTime", javaType = Date.class)
            , @Result(column = "id", property = "assistList"
            , many = @Many(select = "com.mujugroup.wx.mapper.WxRecordAssistMapper.getListByMid",
            fetchType = FetchType.EAGER))
    })
    WxRecordMain findById(Integer id);

    @Select("SELECT * FROM t_wx_record_main")
    @ResultMap("wxRecordMain")
    List<WxRecordMain> findListAll();

    @Select("SELECT * FROM t_wx_record_main WHERE trade_no= #{orderNo}")
    @ResultMap("wxRecordMain")
    WxRecordMain findMainRecordByNo(@Param(value = "orderNo") String orderNo);

    @SelectProvider(type = WxRecordMainSqlProvider.class, method = "getPayRecordList")
    @Results(id = "listVo", value = {
            @Result(column = "did", property = "did", javaType = Long.class)
            , @Result(column = "aid", property = "aid", javaType = Integer.class)
            , @Result(column = "hid", property = "hid", javaType = Integer.class)
            , @Result(column = "oid", property = "oid", javaType = Integer.class)
            , @Result(column = "open_id", property = "openId", javaType = String.class)
            , @Result(column = "trade_no", property = "tradeNo", javaType = String.class)
            , @Result(column = "transaction_id", property = "transactionId", javaType = String.class)
            , @Result(column = "gid", property = "gid", javaType = Integer.class)
            , @Result(column = "price", property = "price", javaType = Integer.class)
            , @Result(column = "type", property = "type", javaType = Integer.class)
    })
    List<ListVo> getPayRecordList(@Param(value = "orderNo") String orderNo);
}