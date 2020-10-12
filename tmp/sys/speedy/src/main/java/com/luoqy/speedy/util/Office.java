package com.luoqy.speedy.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;

/**
 *  对office工作文档的操作
 * */
public class Office {
    public static void main(String[] args) throws  Exception{
        excelExport();
    }
    /**
     *  excel 导出功能
     * */
    public static void excelExport() throws  Exception{
        //创建HSSFWorkbook对象
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建HSSFSheet对象
        HSSFSheet sheet = wb.createSheet("sheet0");
        //创建HSSFRow对象
        HSSFRow row = sheet.createRow(0);
        //创建HSSFCell对象
        HSSFCell cell=row.createCell(0);
        //设置单元格的值
        cell.setCellValue("单元格中的中文");
        //输出Excel文件
        FileOutputStream output=new FileOutputStream("d:\\workbook.xls");
        wb.write(output);
        output.flush();
    }
}
