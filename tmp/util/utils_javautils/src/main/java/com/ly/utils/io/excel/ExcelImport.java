package com.ly.utils.io.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ly.utils.dynamic.Reflect;
import com.ly.utils.io.FileUtil;

/**
 * Excel文件导入为对象
 * 
 * @Version 1.3
 */
public class ExcelImport {
	//excel对象
	private static Workbook workbook;
	//时间格式
	private static String dateFormat = "yyyy-MM-dd";
	/**
	 * 导出全部
	 * @param excelPath
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static Map< String, List<?>> outAll(String excelPath ,Class<?> clazz) throws Exception{
		Map<String,List<?>> map = new HashMap<String, List<?>>();
		setExcelFile(excelPath);
		for(int i=0;i<workbook.getNumberOfSheets();i++){
			String sheetName = workbook.getSheetName(i);
			map.put(sheetName, execute(sheetName, excelPath, clazz));
		}
		return map;
	}
	/**
	 * 导出第一个sheet
	 * @param excelPath
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static List<?> outOne( String excelPath, Class<?> clazz) throws Exception{
		setExcelFile(excelPath);
		return execute(null, excelPath, clazz);
	}
	/**
	 * 按照sheet名字导出
	 * @param sheetName
	 * @param excelPath
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static List<?> out(String sheetName, String excelPath, Class<?> clazz) throws Exception{
		setExcelFile(excelPath);
		return execute(sheetName, excelPath, clazz);
	}
	/**
	 * 执行导出
	 * @param sheetName
	 * @param excelPath
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	private static List<?> execute(String sheetName, String excelPath, Class<?> clazz) throws Exception{
		Sheet sheet = getSheet(sheetName);
		Integer lastRowNum = sheet.getLastRowNum();
		//获取第一行
		Row firstRow = sheet.getRow(0);
		short cellNum = firstRow.getLastCellNum();
		//获取所有列名
		String[] attributes = new String[cellNum];
		for (short i=0;i<cellNum;i++) {
			attributes[i] = firstRow.getCell(i).getStringCellValue();
		}
		//获取Set方法
		Map<String, Method> setMethods = Reflect.getSetMethods(clazz, attributes);
		//为对象设置数据
		List<Object> list = new ArrayList<Object>();
		Row row = null;
		for(int i=1;i<=lastRowNum;i++){
			row = sheet.getRow(i);
			Object obj = clazz.newInstance();
			Cell cell = null;
			for (short j=0;j<cellNum;j++) {
				cell = row.getCell(j);
				Method method = setMethods.get(attributes[j]);
				Reflect.invokeSetValue(method, obj,  getCellValue(cell), dateFormat);
			}
			list.add(obj);
		}
		return list;
	}
	
	/**
	 * 获取单元格的值
	 * @param cell
	 * @return
	 */
	private static String getCellValue(Cell cell) {
		Object result = null;
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				result = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				result = cell.getNumericCellValue();
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				result = cell.getBooleanCellValue();
				break;
			case Cell.CELL_TYPE_FORMULA:
				result = cell.getCellFormula();
				break;
			case Cell.CELL_TYPE_ERROR:
				result = cell.getErrorCellValue();
				break;
			case Cell.CELL_TYPE_BLANK:
				break;
			default:
				break;
			}
		}
		return result!=null?result.toString():null;
	}
	
	/**
	 * 获取Excel文件
	 * @param excelPath
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	private static void setExcelFile(String excelPath) throws IOException{
		File file = FileUtil.getFile(excelPath);
		String postfix = FileUtil.getPostfix(file);
		FileInputStream fileInputStream = new FileInputStream(file);
		if(postfix.equals("xlsx")){
			workbook = new XSSFWorkbook(fileInputStream);
		}else if(postfix.equals("xls")){
			workbook = new HSSFWorkbook(fileInputStream);
		}else{
			throw new IOException("文件类型不符！");
		}
	}
	
	/**
	 * 获取sheet
	 * @param sheetName	如果是空就是第一个sheet
	 * @return
	 */
	private static Sheet getSheet(String sheetName){
		if(sheetName == null){
			return workbook.getSheetAt(0);
		}else{
			return workbook.getSheet(sheetName);
		}
	}
}
