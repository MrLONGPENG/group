package com.mujugroup.wx.mapper;

import com.mujugroup.wx.model.WxRecordAssist;
import com.mujugroup.wx.sql.WxRecordAssistSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 支付记录辅表,数据库操作接口类
 * 类名:WxRecordAssistMapper
 *
 * @author leolaurel
 * 创建时间:20181127
 */
@Mapper
@Component(value = "wxRecordAssistMapper")
public interface WxRecordAssistMapper {

    @InsertProvider(type = WxRecordAssistSqlProvider.class, method = "insert")
    boolean insert(WxRecordAssist wxRecordAssist);

    @UpdateProvider(type = WxRecordAssistSqlProvider.class, method = "update")
    boolean update(WxRecordAssist wxRecordAssist);

    @Delete("delete from t_wx_record_assist where id= #{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_wx_record_assist WHERE id = #{id}")
    @Results(id = "wxRecordAssist", value = {
            @Result(id = true, column = "id", property = "id", javaType = Long.class)
            , @Result(column = "mid", property = "mid", javaType = Long.class)
            , @Result(column = "gid", property = "gid", javaType = Integer.class)
            , @Result(column = "price", property = "price", javaType = Integer.class)
            , @Result(column = "crtTime", property = "crtTime", javaType = Date.class)
            , @Result(column = "type", property = "type", javaType = Integer.class)
    })
    WxRecordAssist findById(Integer id);

    @Select("SELECT * FROM t_wx_record_assist")
    @ResultMap("wxRecordAssist")
    List<WxRecordAssist> findListAll();

    @Select("Select * from t_wx_record_assist where mid= #{id}")
    List<WxRecordAssist> getListByMid(@Param(value = "id") long id);

}