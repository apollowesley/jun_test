package com.ly.utils.io.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ly.utils.dynamic.Reflect;
import com.ly.utils.io.FileUtil;

/**
 * 	Excel文件导出为“xlsx”或“xls”
 * 	<br>
 *  <em style="color:green">
 *  例：
 *  <ul >
 *		<li>ExcelExport.set(标题,路径,[列表],List数据)</li>
 *		<li>ExcelExport.xlsx_write()</li>
 *  </ul>
 *  </em>
 * 
 * @Version 1.2.3
 */
public class ExcelExport  {
	
	private static Workbook workbook;
	private static Integer columnWidth = 20;
	private static String titleName;
	private static String folderPath;
	private static Map<String,String[]> column = null;
	private static Map<String,List<?>> dataMap = null;
	private static String pattern = "yyyy-MM-dd";
	
	/**
	 * 设置excel属性
	 * @param title			标题
	 * @param folderPath	文件路径
	 * @param column		列表名（可以为空）
	 * @param data			数据
	 * @throws NullPointerException
	 * @throws IOException
	 */
	public static void setExcel(String title, String folderPath,String[] column, List<?> data) throws NullPointerException, IOException{
		titleName = title;
		setFolderPath(folderPath);
		Map<String,String[]> cl = new HashMap<String,String[]>(); 
		if(column != null){
			new HashMap<String, String[]>();
			cl.put("1", column);
		}	
		Map<String,List<?>> da = new HashMap<String, List<?>>();
		da.put("1", data);
		ExcelExport.column = cl;
		ExcelExport.dataMap = da;
	}
	
	/**
	 * 设置excel属性
	 * @param title			标题
	 * @param folderPath	文件路径
	 * @param column		列表名（可以为空）
	 * @param data			数据
	 * @throws NullPointerException
	 * @throws IOException
	 */
	public static void setExcel(String title, String folderPath,Map<String,String[]> column, Map<String, List<?>> data) throws NullPointerException, IOException{
		titleName = title;
		setFolderPath(folderPath);
		ExcelExport.column = column;
		ExcelExport.dataMap = data;
	}
	
	/**
	 * 导出为xlsx文件
	 * @throws Exception
	 */
	public static void xlsx_write() throws Exception{
		workbook = new XSSFWorkbook();
		writeObjectAllData();
		write(titleName+".xlsx");
	}	
	
	/**
	 * 导出为xls文件
	 * @throws Exception
	 */
	public static void xls_write()throws Exception{
		workbook = new HSSFWorkbook();
		writeObjectAllData();
		write(titleName+".xls");
	}	
	/**
	 * 导出
	 * @param fileName
	 * @throws Exception
	 */
	private static void write(String fileName) throws Exception{
		FileOutputStream outputStream = new FileOutputStream(folderPath+"\\"+fileName);
		workbook.write(outputStream);
		outputStream.close();
	}
	/**
	 * 导出全部数据
	 * @throws Exception
	 */
	private static void writeObjectAllData() throws Exception{
		Set<String> sheetNames = dataMap.keySet();
		for(String sheetName : sheetNames){
			Sheet sheet = workbook.createSheet(sheetName);
			sheet.setDefaultColumnWidth(columnWidth);
	        List<?> data = dataMap.get(sheetName);
	        if(data.size()==0){
	        	return;
	        }
	        Field[] fields = data.get(0).getClass().getDeclaredFields();

	        // 创建列首-增加样式-赋值
	        Row row = sheet.createRow(0);
	        for (short i = 0; i < fields.length; i++) {
	            Cell cell = row.createCell(i);
	            if(column != null && column.get(sheetName) != null && i<column.get(sheetName).length){
	            	cell.setCellValue(column.get(sheetName)[i]);
	            }else{
            		cell.setCellValue(fields[i].getName());
	            }
	        }
	        
	        //通过反射写入数据
	        for(int objIndex=0;objIndex<data.size();objIndex++){
		        Row dataRow = sheet.createRow(objIndex+1);
		        for(int i=0;i<fields.length;i++){
		        	Cell cell = dataRow.createCell(i);
		        	cell.setCellStyle(cellStyle());
		        	
		        	Object value = Reflect.invokeGetMethod(data.get(objIndex), fields[i].getName());
		        	
		        	String textValue = null;
		        	if(value != null){
			        	if(value instanceof Date){
		        			Date date = (Date) value;
							SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		                    textValue = sdf.format(date);
		        		}else{
		        			textValue = value.toString();
		        		}
			        	Pattern p = Pattern.compile("^\\d+(\\.\\d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            // 不是数字做普通处理
                            cell.setCellValue(textValue);
                        }
		        	}
		        }
	        }
		}
	}
	
	/**
	 * 设置文件路径
	 * @param folderPath
	 * @throws NullPointerException
	 * @throws IOException
	 */
	private static void setFolderPath(String folderPath) throws NullPointerException, IOException{
		ExcelExport.folderPath = FileUtil.getMkdirFile(folderPath).getPath();
	}
	
	/**
	 * 设置导出表格样式
	 * @return
	 */
	private static CellStyle cellStyle(){
		CellStyle style = workbook.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		return style;
	}
	
	/**
	 * 设置表格宽度
	 * @param columnWidth
	 */
	public static void setColumnWidth(Integer columnWidth) {
		ExcelExport.columnWidth = columnWidth;
	}
}