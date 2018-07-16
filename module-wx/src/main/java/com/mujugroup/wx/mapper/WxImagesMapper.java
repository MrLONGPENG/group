package com.mujugroup.wx.mapper;

import com.mujugroup.wx.model.WxImages;
import com.mujugroup.wx.sql.WxImagesSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 保修图片表,数据库操作接口类
 * 类名:WxImagesMapper
 * @author leolaurel
 * 创建时间:20180707
 */
@Mapper
@Component(value ="wxImagesMapper")
public interface WxImagesMapper {

    @InsertProvider(type = WxImagesSqlProvider.class, method = "insert")
    boolean insert(WxImages wxImages);

    @UpdateProvider(type = WxImagesSqlProvider.class, method = "update")
    boolean update(WxImages wxImages);

    @Delete("delete from t_wx_images where id= #{id}")
    boolean deleteById(int id);

    @Select("SELECT * FROM t_wx_images WHERE id = #{id}")
    @Results(id = "wxImages", value = {
         @Result(id=true, column="id",property="id",javaType=Integer.class)
             ,@Result(column="pid",property="pid",javaType=Integer.class)
             ,@Result(column="type",property="type",javaType=Integer.class)
             ,@Result(column="image_url",property="imageUrl",javaType=String.class)
             ,@Result(column="crtTime",property="crtTime",javaType=Date.class)
    })
    WxImages findById(Integer id);

    @Select("SELECT * FROM t_wx_images limit 1000")
    @ResultMap("wxImages")
    List<WxImages> findListAll();

}