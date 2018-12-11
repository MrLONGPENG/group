package com.mujugroup.wx.mapper;

import com.mujugroup.wx.model.WxDeductionRecord;
import com.mujugroup.wx.objeck.vo.deduction.ListVo;
import com.mujugroup.wx.sql.WxDeductionRecordSqlProvider;
import com.mujugroup.wx.sql.WxDepositSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 扣费记录表,数据库操作接口类
 * 类名:WxDeductionRecordMapper
 *
 * @author leolaurel
 * 创建时间:20181203
 */
@Mapper
@Component(value = "wxDeductionRecordMapper")
public interface WxDeductionRecordMapper {

    @InsertProvider(type = WxDeductionRecordSqlProvider.class, method = "insert")
    boolean insert(WxDeductionRecord wxDeductionRecord);

    @UpdateProvider(type = WxDeductionRecordSqlProvider.class, method = "update")
    boolean update(WxDeductionRecord wxDeductionRecord);

    @Delete("delete from t_wx_deduction_record where id= #{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_wx_deduction_record WHERE id = #{id}")
    @Results(id = "wxDeductionRecord", value = {
            @Result(id = true, column = "id", property = "id", javaType = Long.class)
            , @Result(column = "open_id", property = "openId", javaType = String.class)
            , @Result(column = "trade_no", property = "tradeNo", javaType = String.class)
            , @Result(column = "did", property = "did", javaType = Long.class)
            , @Result(column = "explain", property = "explain", javaType = String.class)
            , @Result(column = "day", property = "day", javaType = String.class)
            , @Result(column = "forfeit", property = "forfeit", javaType = Integer.class)
            , @Result(column = "timeout", property = "timeout", javaType = Integer.class)
            , @Result(column = "type", property = "type", javaType = Integer.class)
            , @Result(column = "crtTime", property = "crtTime", javaType = Date.class)
    })
    WxDeductionRecord findById(Integer id);

    @Select("SELECT * FROM t_wx_deduction_record")
    @ResultMap("wxDeductionRecord")
    List<WxDeductionRecord> findListAll();

    @Select("SELECT * FROM t_wx_deduction_record WHERE `day`=#{day} AND `open_id`= #{openId} AND `did`= #{did} LIMIT 1")
    @ResultMap("wxDeductionRecord")
    WxDeductionRecord isExist(@Param(value = "day") String date, @Param(value = "openId") String openId, @Param(value = "did") long did);

    @SelectProvider(type = WxDeductionRecordSqlProvider.class, method = "getDeductionRecordList")
    @ResultMap("wxDeductionRecord")
    List<WxDeductionRecord> getDeductionRecordList(@Param(value = "openId") String openId);
}