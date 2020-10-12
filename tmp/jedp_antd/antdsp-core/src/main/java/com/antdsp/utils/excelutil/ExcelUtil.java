package com.antdsp.utils.excelutil;

import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.util.StringUtils;

public class ExcelUtil {

	public static <T> void dataToExcel(List<Column> columns , List<T> data, HSSFWorkbook hwb) throws Exception {
		
		HSSFSheet sheet = hwb.createSheet("sheet1");
		HSSFRow rowhead = sheet.createRow( 0);
		
		//header
		for (int i = 0; i < columns.size(); i++) {
			String titleText = columns.get(i).getTitle();
			rowhead.createCell(i).setCellValue(titleText);
		}
		
		Class<?> dtoClass ;
		if(data!=null && data.size()>0) {
			dtoClass = data.get(0).getClass();
		}else {
			return ;
		}
		
		//行
		for(int j = 0 ; j<data.size() ; j++) {
			T obj = data.get(j);
			HSSFRow row = sheet.createRow(j+1);
			//列
			for(int i = 0 ; i < columns.size() ; i++) {
				Column column = columns.get(i);
				String cellValue ;
				if(StringUtils.isEmpty(column.getField())) {
					cellValue = "";
				}else {
					cellValue = dtoClass.getMethod(column.getGetterName()).invoke(obj).toString();
				}
				if(column.getRender() != null) {
					cellValue = column.getRender().render(cellValue , obj);
				}
				row.createCell(i).setCellValue(cellValue);
			}
		}
		return ;
	}
	
	public static <T> void dataToResponse(List<Column> columns , List<T> data, OutputStream output) throws Exception {
		HSSFWorkbook hwb = new HSSFWorkbook();
		dataToExcel(columns, data, hwb);
		hwb.write(output);
	}
	
	
}
