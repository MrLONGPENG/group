package com.mujugroup.data.service;


import com.mujugroup.data.utils.ExcelData;

import java.util.List;

public interface ExcelService {

    List<ExcelData> getExcelDataList(int aid, int hid, int grain, int startTime, int stopTime);

}
