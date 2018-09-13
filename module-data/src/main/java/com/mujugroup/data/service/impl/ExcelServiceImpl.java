package com.mujugroup.data.service.impl;

import com.lveqia.cloud.common.exception.ParamException;
import com.mujugroup.data.objeck.bo.ExcelBO;
import com.mujugroup.data.service.ExcelService;
import com.mujugroup.data.service.StaBOService;
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
    private final StaBOService staBOService;
    @Autowired
    public ExcelServiceImpl(ModuleCoreService moduleCoreService, StaBOService staBOService) {
        this.moduleCoreService = moduleCoreService;
        this.staBOService = staBOService;
    }

    @Override
    public List<ExcelData> getExcelDataList(int aid, int hid, int grain, int startTime, int stopTime) {
        List<ExcelData> list = new ArrayList<>();
        if(hid == 0){
            Map<Integer, String> map = moduleCoreService.getHospitalByAid(aid);
            for (Integer key : map.keySet()) {
                list.add(getExcelData(map.get(key), aid , key, grain, startTime, stopTime));
            }
        }else{
            Map<String, String> map = moduleCoreService.getHospitalById(String.valueOf(hid));
            list.add(getExcelData(map.get(String.valueOf(hid)), aid , hid, grain, startTime, stopTime));
        }
        return list;
    }


    /**
     * 构建单个Sheet页面
     */
    private ExcelData getExcelData(String name, int aid, int hid, int grain, int startTime, int stopTime) {
        String[] titles = new String[]{"时间", "代理商", "省份", "地区", "医院", "激活数", "使用数", "使用率", "收益"};
        ExcelData data = new ExcelData();
        data.setName(name);
        data.setTitles(titles);
        try {
            List<ExcelBO> list = staBOService.getExcelBO(name, aid, hid, grain, startTime, stopTime);
            data.setRows(ExcelUtils.toRows(list));
        } catch (ParamException e) {
            e.printStackTrace();
        }
        return data;
    }
}
