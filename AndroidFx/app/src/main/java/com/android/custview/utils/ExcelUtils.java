package com.android.custview.utils;
import android.text.TextUtils;
import android.util.Log;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Map;

public class ExcelUtils {
    private static final String TAG = "ExcelUtils";
    public static void readExcel(File file,Map<String,String> map) throws FileNotFoundException {
        if(file == null) {
            Log.e(TAG,"file is null");
            return;
        }
        InputStream stream = new FileInputStream(file);
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(stream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowsCount = sheet.getPhysicalNumberOfRows();
            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            for (int r = 0; r<rowsCount; r++) {
                Row row = sheet.getRow(r);
                int cellsCount = row.getPhysicalNumberOfCells();
                KLog.logE("cellsCount: " + cellsCount);
                //target value is cell 4
                String target = getCellAsString(row, cellsCount - 1, formulaEvaluator);
                for (int c = 0; c < cellsCount - 1; c++) {
                    String value = getCellAsString(row, c, formulaEvaluator);
                    if (!TextUtils.isEmpty(value)) {
                        map.put(value, target);
                    }
                }
            }
        } catch (Exception e) {
        }
        KLog.logE("------------------------------------------------------------------");
    }

    private static String getCellAsString(Row row, int c, FormulaEvaluator formulaEvaluator) {
        String value = "";
        try {
            Cell cell = row.getCell(c);
            CellValue cellValue = formulaEvaluator.evaluate(cell);
            switch (cellValue.getCellType()) {
                case Cell.CELL_TYPE_BOOLEAN:
                    value = ""+cellValue.getBooleanValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    double numericValue = cellValue.getNumberValue();
                    if(HSSFDateUtil.isCellDateFormatted(cell)) {
                        double date = cellValue.getNumberValue();
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
                        value = formatter.format(HSSFDateUtil.getJavaDate(date));
                    } else {
                        value = ""+numericValue;
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    value = ""+cellValue.getStringValue();
                    break;
                default:
                    break;
            }
        } catch (NullPointerException e) {
        }
        return value;
    }

    public static boolean checkIfExcelFile(File file){
        if(file == null) {
            return false;
        }
        String name = file.getName();
        String[] list = name.split("\\.");
        if(list.length < 2) {
            return false;
        }
        String  typeName = list[list.length - 1];
        return "xls".equals(typeName) || "xlsx".equals(typeName);
    }
}