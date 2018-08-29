package com.mujugroup.data.utils;

import com.mujugroup.data.objeck.bo.ExcelBO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

public class ExcelUtils {


    public static void exportExcel(HttpServletResponse response, String fileName, List<ExcelData> data) throws Exception {
        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(fileName, "utf-8"));
        exportExcel(data, response.getOutputStream());
    }

    public static void exportExcel(List<ExcelData> list, OutputStream out) throws Exception {
        XSSFWorkbook wb = new XSSFWorkbook();
        try {
            for (ExcelData data: list) {
                String sheetName = data.getName();
                if (null == sheetName) {
                    sheetName = "Sheet1";
                }
                XSSFSheet sheet = wb.createSheet(sheetName);
                writeExcel(wb, sheet, data);
            }
            wb.write(out);
        } finally {
            wb.close();
        }
    }

    private static void writeExcel(XSSFWorkbook wb, XSSFSheet sheet, ExcelData data) {
        int rowIndex = 0;
        rowIndex = writeTitlesToExcel(wb, sheet, data.getTitles());
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
        //titleFont.setColor(IndexedColors.BLACK.index);

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

    private static void writeRowsToExcel(XSSFWorkbook wb, Sheet sheet, List<ExcelBO> rows, int rowIndex) {
        XSSFFont dataFont = wb.createFont();
        dataFont.setFontName("宋体");
        // dataFont.setFontHeightInPoints((short) 14);
        dataFont.setColor(IndexedColors.BLACK.index);
        XSSFCellStyle dataStyle = wb.createCellStyle();
        dataStyle.setAlignment(HorizontalAlignment.CENTER);
        dataStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dataStyle.setFont(dataFont);
        for (ExcelBO bo : rows) {
            writeCellToExcel(sheet.createRow(rowIndex), dataStyle, new String[]{bo.getRefDate(), bo.getAgent(), bo.getProvince(), bo.getCity()
                    , bo.getHospital(), bo.getActive(), bo.getUsage(), bo.getUsageRate(), bo.getProfit()});
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
}
