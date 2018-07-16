package com.mujugroup.wx.sql;

import com.mujugroup.wx.model.WxRepair;
import org.apache.ibatis.jdbc.SQL;

/**
 * 保修信息表,SQL语句组装类
 * 类名:WxRepairSqlProvider
 * 创建人:LEOLAUREL
 * 创建时间:20180707
 */
public class WxRepairSqlProvider {

    public String insert(WxRepair wxRepair){
        return new SQL(){{
            INSERT_INTO("t_wx_repair");
            if(wxRepair.getId()!= null) VALUES("`id`", "#{id}");
            if(wxRepair.getDid()!= null) VALUES("`did`", "#{did}");
            if(wxRepair.getOpenId()!= null) VALUES("`open_id`", "#{openId}");
            if(wxRepair.getFaultCause()!= null) VALUES("`fault_cause`", "#{faultCause}");
            if(wxRepair.getFaultDescribe()!= null) VALUES("`fault_describe`", "#{faultDescribe}");
            if(wxRepair.getRestorer()!= null) VALUES("`restorer`", "#{restorer}");
            if(wxRepair.getRepairStatus()!= null) VALUES("`repair_status`", "#{repairStatus}");
            if(wxRepair.getCrtTime()!= null) VALUES("`crtTime`", "#{crtTime}");
            if(wxRepair.getUpdTime()!= null) VALUES("`updTime`", "#{updTime}");
        }}.toString();
    }



    public String update(WxRepair wxRepair){
        return new SQL(){{
            UPDATE("t_wx_repair");
            if(wxRepair.getId()!= null) SET("`id` = #{id}");
            if(wxRepair.getDid()!= null) SET("`did` = #{did}");
            if(wxRepair.getOpenId()!= null) SET("`open_id` = #{openId}");
            if(wxRepair.getFaultCause()!= null) SET("`fault_cause` = #{faultCause}");
            if(wxRepair.getFaultDescribe()!= null) SET("`fault_describe` = #{faultDescribe}");
            if(wxRepair.getRestorer()!= null) SET("`restorer` = #{restorer}");
            if(wxRepair.getRepairStatus()!= null) SET("`repair_status` = #{repairStatus}");
            if(wxRepair.getCrtTime()!= null) SET("`crtTime` = #{crtTime}");
            if(wxRepair.getUpdTime()!= null) SET("`updTime` = #{updTime}");
            WHERE("id = #{id}");
        }}.toString();
    }
}
