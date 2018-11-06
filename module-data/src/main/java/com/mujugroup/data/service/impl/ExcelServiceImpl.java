package com.mujugroup.data.service.impl;

import com.google.gson.JsonObject;
import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.util.JsonUtil;
import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.data.objeck.bo.ExcelBo;
import com.mujugroup.data.service.ExcelService;
import com.mujugroup.data.service.StaBoService;
import com.mujugroup.data.service.feign.ModuleCoreService;
import com.mujugroup.data.utils.ExcelData;
import com.mujugroup.data.utils.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("excelService")
public class ExcelServiceImpl implements ExcelService {


    private final ModuleCoreService moduleCoreService;
    private final StaBoService staBoService;
    @Autowired
    public ExcelServiceImpl(ModuleCoreService moduleCoreService, StaBoService staBoService) {
        this.moduleCoreService = moduleCoreService;
        this.staBoService = staBoService;
    }


    @Override
    public JsonObject getHospitalJson(String hid) {
        Map<String, String> map = moduleCoreService.getHospitalJson(hid);
        if(map == null || !map.containsKey(hid)) return null;
        return JsonUtil.toJsonObject(map.get(hid));
    }

    @Override
    public List<ExcelData> getExcelDataList(String[] ids, int grain, int startTime, int stopTime) {
        List<ExcelData> list = new ArrayList<>();
        if(ids!=null){
            Map<String, String> map = moduleCoreService.getHospitalJson(StringUtil.toLink((Object[]) ids));
            for (String hid: ids) {
                if(StringUtil.isEmpty(hid) || map == null ||!map.containsKey(hid)) continue;
                list.add(getExcelData(JsonUtil.toJsonObject(map.get(hid)), grain, startTime, stopTime));
            }
        }
        return list;
    }


    /**
     * 构建单个Sheet页面
     */
    private ExcelData getExcelData(JsonObject info, int grain, int startTime, int stopTime) {
        String[] titles = new String[]{"时间", "代理商", "省份", "地区", "医院", "激活数", "使用数", "使用率", "收益"};
        ExcelData data = new ExcelData();
        data.setName(info.get("hospital").getAsString());
        data.setTitles(titles);
        try {
            List<ExcelBo> list = staBoService.getExcelBO(info, grain, startTime, stopTime);
            data.setRows(ExcelUtils.toRows(list));
        } catch (ParamException e) {
            e.printStackTrace();
        }
        return data;
    }
}
