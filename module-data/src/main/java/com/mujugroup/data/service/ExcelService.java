package com.mujugroup.data.service;


import com.google.gson.JsonObject;
import com.mujugroup.data.utils.ExcelData;

import java.util.List;

public interface ExcelService {

    JsonObject getHospitalJson(String hid);

    List<ExcelData> getExcelDataList(String[] hid, int grain, int startTime, int stopTime);


}
