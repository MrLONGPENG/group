package com.mujugroup.data.utils;

import com.lveqia.cloud.common.StringUtil;
import com.mujugroup.data.objeck.bo.ExcelBO;
import com.mujugroup.data.objeck.bo.OrderBO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {

    public static void exportExcel(HttpServletResponse response, String fileName, ExcelData data)
            throws Exception {
        ArrayList<ExcelData> list = new ArrayList<>();
        list.add(data);
        exportExcel(response, fileName, list);
    }


    public static void exportExcel(HttpServletResponse response, String fileName, List<ExcelData> data)
            throws Exception {
        // 告诉浏览器用什么软件可以打开此文件. 且设置下载默认名称
        String encodeName = URLEncoder.encode(fileName , "utf-8");
        response.setHeader("content-Type","application/vnd.ms-excel");
        response.setHeader("Content-Disposition","attachment;filename=" + encodeName);
        exportExcel(data, response.getOutputStream());
    }


    /**
     * 写入多个Sheet页面
     */
    private static void exportExcel(List<ExcelData> list, OutputStream out) throws Exception {
        try (XSSFWorkbook wb = new XSSFWorkbook()) {
            int count = 1;
            for (ExcelData data : list) {
                String sheetName = data.getName();
                if (StringUtil.isEmpty(sheetName)) {
                    sheetName = "Sheet"+ count++;
                }
                XSSFSheet sheet = wb.createSheet(sheetName);
                writeExcel(wb, sheet, data);
            }
            wb.write(out);
        }
    }

    /**
     * 写单个Sheet页面-- 包括标题以及内容
     */
    private static void writeExcel(XSSFWorkbook wb, XSSFSheet sheet, ExcelData data) {
        int rowIndex = writeTitlesToExcel(wb, sheet, data.getTitles());
        writeRowsToExcel(wb, sheet, data.getRows(), rowIndex);
        autoSizeColumns(sheet, data.getTitles().length + 1);

    }

    private static int writeTitlesToExcel(XSSFWorkbook wb, XSSFSheet sheet, String[] titles) {
        int rowIndex = 0;
        int colIndex = 0;
        XSSFFont titleFont = wb.createFont();
        titleFont.setFontName("宋体");
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 14);

        XSSFCellStyle titleStyle = wb.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setFont(titleFont);

        XSSFRow titleRow = sheet.createRow(rowIndex);

        for (String field : titles) {
            XSSFCell cell = titleRow.createCell(colIndex);
            cell.setCellValue(field);
            cell.setCellStyle(titleStyle);
            colIndex++;
        }

        rowIndex++;
        return rowIndex;
    }

    private static void writeRowsToExcel(XSSFWorkbook wb, Sheet sheet, List<String[]> rows, int rowIndex) {
        XSSFFont dataFont = wb.createFont();
        dataFont.setFontName("宋体");
        dataFont.setColor(IndexedColors.BLACK.index);
        XSSFCellStyle dataStyle = wb.createCellStyle();
        dataStyle.setAlignment(HorizontalAlignment.CENTER);
        dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dataStyle.setFont(dataFont);
        for (String[] arr : rows) {
            writeCellToExcel(sheet.createRow(rowIndex), dataStyle, arr);
            rowIndex++;
        }
    }

    private static void writeCellToExcel(Row dataRow, XSSFCellStyle dataStyle, String[] array) {
        Cell cell;
        int colIndex = 0;
        for (String value : array) {
            cell = dataRow.createCell(colIndex);
            cell.setCellValue(value);
            cell.setCellStyle(dataStyle);
            colIndex++;
        }
    }

    private static void autoSizeColumns(Sheet sheet, int columnNumber) {
        for (int i = 0; i < columnNumber; i++) {
            int orgWidth = sheet.getColumnWidth(i);
            sheet.autoSizeColumn(i, true);
            int newWidth = sheet.getColumnWidth(i) + 100;
            if (newWidth > orgWidth) {
                sheet.setColumnWidth(i, newWidth);
            } else {
                sheet.setColumnWidth(i, orgWidth);
            }
        }
    }


    public static List<String[]> toRows(List<ExcelBO> list) {
        List<String[]>  result  = new ArrayList<>();
        for (ExcelBO bo: list) {
            result.add(new String[]{bo.getRefDate(), bo.getAgent(), bo.getProvince(), bo.getCity(), bo.getHospital()
                    , bo.getActive(), bo.getUsage(), bo.getUsageRate(), StringUtil.changeF2Y(bo.getProfit())});
        }
        return result;
    }

    public static List<String[]> toRowsByOrderBO(List<OrderBO> list) {
        List<String[]>  result  = new ArrayList<>();
        for (OrderBO bo: list) {
            result.add(new String[]{bo.getPayTime(), bo.getTradeNo() , bo.getAgent() , bo.getHospital()
                    , bo.getDepartment() , bo.getBedInfo(), bo.getDid(), bo.getOrderType() ,bo.getPayPrice()
                    , bo.getPayStatus() == 2 ? "已完成": "已退款"});
        }
        return result;
    }
}
