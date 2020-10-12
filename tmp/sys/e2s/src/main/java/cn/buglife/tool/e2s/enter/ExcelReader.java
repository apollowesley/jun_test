package cn.buglife.tool.e2s.enter;

import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 读取Excel文件的数据
 */
public class ExcelReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelReader.class);

    private static final ExcelReader reader = new ExcelReader();

    public static List<HSSFSheet> listSheet(File file){
        List<HSSFSheet> sheets = new ArrayList<HSSFSheet>();
        try {
            FileInputStream inputStream = new FileInputStream(file);
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);

            //获取sheet的数目
            int sheetNum = workbook.getNumberOfSheets();
            for (int i = 0; i < sheetNum; i++) {
                sheets.add(workbook.getSheetAt(i));
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("文件未找到:"+e.getMessage());
        } catch (IOException e) {
            LOGGER.error("读取excel出错："+e.getMessage());
        }

        return sheets;
    }
    /**
     * 读取Excel表格表头的内容
     */
    public static List<String> getExcelHeader(HSSFSheet sheet) {
        HSSFRow row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();

        LOGGER.info("列数:" + colNum);
        List<String> header = new ArrayList<String>();
        for (int i = 0; i < colNum; i++) {
            header.add(reader.getCellFormatValue(row.getCell((short) i)));
        }
        return header;
    }

    /**
     * 获取一个sheet里的所有数据
     *
     */
    public static List<String> getData(HSSFSheet sheet) {
        List<String> content = new ArrayList<String>();
        String str = "";
        int rowNum = sheet.getLastRowNum();

        //根据
        HSSFRow row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        for (int i = 1; i <= rowNum; i++) {
            row = sheet.getRow(i);
            int j = 0;
            while (j < colNum) {
                str += "'"+reader.getCellFormatValue(row.getCell((short) j)).trim() + "'";
                if (j!=colNum-1){
                    str +=",";
                }
                j++;
            }
            content.add(str);
            str = "";
        }
        return content;
    }

    /**
     * 获取单元格字符串类型的数据
     */
    private String getStringCellValue(HSSFCell cell) {
        String strCell = "";
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
                strCell = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                strCell = String.valueOf(cell.getNumericCellValue());
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                strCell = "";
                break;
            default:
                strCell = "";
                break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        if (cell == null) {
            return "";
        }
        return strCell;
    }

    /**
     * 获取单元格日期类型的数据
     */
    private String getDateValue(HSSFCell cell) {
        String result = "";
        try {
            int cellType = cell.getCellType();
            if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                Date date = cell.getDateCellValue();
                result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)
                        + "-" + date.getDate();
            } else if (cellType == HSSFCell.CELL_TYPE_STRING) {
                String date = getStringCellValue(cell);
                result = date.replaceAll("[年月]", "-").replace("日", "").trim();
            } else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
                result = "";
            }
        } catch (Exception e) {
            System.out.println("日期格式不正确!");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据HSSFCell类型设置数据
     */
    private String getCellFormatValue(HSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC:
                case HSSFCell.CELL_TYPE_FORMULA: {
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        cellvalue = sdf.format(date);

                    } else {
                        cellvalue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case HSSFCell.CELL_TYPE_STRING:
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                default:
                    cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }

}
