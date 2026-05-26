package com.cts.mfrp.Anvay.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.IOException;
import java.io.InputStream;

public class ExcelUtils {

    public static Object[][] getTestData(String filePath, String sheetName) throws IOException {
    	
        InputStream fis = ExcelUtils.class.getClassLoader().getResourceAsStream("testdata/" + filePath);
        Workbook wb = new XSSFWorkbook(fis);
        Sheet sheet = wb.getSheet(sheetName);

        if (sheet == null) {
            wb.close();
            fis.close();
            throw new RuntimeException("Sheet not found: '" + sheetName
                    + "' in file: " + filePath);
        }

        int totalRows = sheet.getLastRowNum();
        int totalCols = sheet.getRow(0).getLastCellNum();

        Object[][] data = new Object[totalRows][totalCols];

        for (int r = 1; r <= totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null) continue;

            for (int c = 0; c < totalCols; c++) {
                Cell cell = row.getCell(c);
                data[r - 1][c] = getCellValueAsString(cell);
            }
        }

        wb.close();
        fis.close();
        return data;
    }
    
    private static String getCellValueAsString(Cell cell) {
        
    	if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();

            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue().toString();
                }
                double d = cell.getNumericCellValue();
                if (d == (long) d) return String.valueOf((long) d);
                return String.valueOf(d);

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            case FORMULA:
                return cell.getCellFormula();

            case BLANK:
            default:
                return "";
        }
    }
}