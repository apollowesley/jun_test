package com.evil.util;

import java.io.File;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ReadExcelUtil {
    public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
    public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";
    
    public HSSFWorkbook readExcel(File file) throws Exception {
        if (file!= null) {
            String fileName =file.getName();
            if(fileName.contains(".")){
            	String postfix=fileName.substring(fileName.indexOf(".")+1);
            	if (!"".equals(postfix)) {

            	} else {
            		throw new Exception("文件类型不对");
            	}
            }
        }
        return null;
    }

	private void readXlsx(File file) {
		
	}

	private void readXls(File file) {
		
	}

}
