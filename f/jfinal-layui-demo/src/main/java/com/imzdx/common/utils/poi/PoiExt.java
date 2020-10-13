package com.imzdx.common.utils.poi;

import com.jfinal.plugin.activerecord.Db;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.sun.tools.javac.jvm.ByteCodes.ret;

public class PoiExt {
    public static Map<String, Object> ReadExcel(String filename, String sql,
                                                int ColCount) {
        Map<String, Object> map = null;
        try {
            @SuppressWarnings("resource")
            HSSFWorkbook wookbook = new HSSFWorkbook(new FileInputStream(
                    filename));
            HSSFSheet sheet = wookbook.getSheet("Sheet1");
            int rows = sheet.getPhysicalNumberOfRows();
            Object[][] paras = new Object[rows][ColCount];
            for (int i = 1; i < rows; i++) {
                HSSFRow row = sheet.getRow(i);
                if (row != null) {
                    int cells = row.getPhysicalNumberOfCells();
                    // 2017/12/28 0028 此处修改了源码，用于解决数组越界，可能会导致部分文档读不出来
                    cells = Math.min(cells, ColCount);
                    for (int j = 0; j < cells; j++) {
                        HSSFCell cell = row.getCell(j);
                        if (cell != null) {
                            switch (cell.getCellType()) {
                                case HSSFCell.CELL_TYPE_FORMULA:
                                    break;
                                case HSSFCell.CELL_TYPE_NUMERIC:
                                    DecimalFormat df = new DecimalFormat("#.##");
                                    String value = df.format(cell.getNumericCellValue());
                                    paras[i][j] = value;
                                    break;
                                case HSSFCell.CELL_TYPE_STRING:
                                    paras[i][j] = cell.getStringCellValue();
                                    break;
                                default:
                                    paras[i][j] = null;
                                    break;
                            }
                        }
                    }
                }
            }
            Object[][] result = new Object[rows][ColCount];
            int j = 0;
            for (int i = 0; i < paras.length; i++) {
                if (paras[i] != null && paras[i][0] != null) { // id不为null的才算取其值
                    result[j] = paras[i];
                    j++;
                }
            }
            result = Arrays.copyOf(result, j);
            int[] ret = Db.batch(sql, result, 100);
            int s = 0, l = 0;
            for (int i = 0; i < ret.length; i++)
                if (ret[i] == 1)
                    s++;
                else
                    l++;
            map = new HashMap<String, Object>();
            map.put("success", s);
            map.put("lost", l);
            map.put("count", ret.length);
        } catch (IOException e) {
            map.put("error", e.getMessage());
            e.printStackTrace();
        }
        return map;
    }
}