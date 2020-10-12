package com.evil.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class PoiTest {
	public static void main(String[] args) throws IOException {
		questionsExcel();
	}
	
	public static void poitest() throws IOException{
		HSSFWorkbook wb=new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("new sheet"); //建立新的sheet对象
		HSSFRow row = sheet.createRow((short)0);
		//在sheet里创建一行，参数为行号（第一行，此处可想象成数组）
		HSSFCell cell = row.createCell(0);
		//在row里建立新cell（单元格），参数为列号（第一列）
		cell.setCellValue(1);//设置cell的整数类型的值
		row.createCell(1).setCellValue(1.2); //设置cell浮点类型的值
		row.createCell(2).setCellValue("test"); //设置cell字符类型的值
		row.createCell(3).setCellValue(true); //设置cell布尔类型的值 
		HSSFCellStyle cellStyle = wb.createCellStyle(); //建立新的cell样式
		cellStyle.setDataFormat(HSSFDataFormat. getBuiltinFormat("m/d/yy h:mm"));
		//设置cell样式为定制的日期格式
		HSSFCell dCell =row.createCell(4);
		dCell.setCellValue(new Date()); //设置cell为日期类型的值
		dCell.setCellStyle(cellStyle); //设置该cell日期的显示格式
		HSSFCell csCell =row.createCell(5);
//		csCell.setEncoding(HSSFCell.ENCODING_UTF_16);
		//设置cell编码解决中文高位字节截断
		csCell.setCellValue("中文测试_Chinese Words Test"); //设置中西文结合字符串
		row.createCell(6).setCellType(HSSFCell.CELL_TYPE_ERROR);
		//建立错误cell
		FileOutputStream fileOut = new FileOutputStream("workbook.xls");
		wb.write(fileOut);
		fileOut.close();
	}
	private static void cteateCell(HSSFWorkbook wb,HSSFRow row,int col,short align,Object val){
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(val==null?"":val.toString());
		HSSFCellStyle cellstyle = wb.createCellStyle();
		cellstyle.setAlignment(align);
		cell.setCellStyle(cellstyle);
		}
	public static void questionsExcel() throws IOException{
		HSSFWorkbook wb=new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("单选题"); //建立新的sheet对象
		HSSFRow row = sheet.createRow(0);
		//在sheet里创建一行，参数为行号（第一行，此处可想象成数组）
		cteateCell(wb, row, 0, HSSFCellStyle.ALIGN_LEFT, "序号");
		cteateCell(wb, row, 1, HSSFCellStyle.ALIGN_LEFT, "模板名称");
		cteateCell(wb, row, 2, HSSFCellStyle.ALIGN_LEFT, "题目");
		cteateCell(wb, row, 3, HSSFCellStyle.ALIGN_LEFT, "正确答案");
		cteateCell(wb, row, 4, HSSFCellStyle.ALIGN_LEFT, "选项A");
		cteateCell(wb, row, 5, HSSFCellStyle.ALIGN_LEFT, "选项B");
		cteateCell(wb, row, 6, HSSFCellStyle.ALIGN_LEFT, "选项C");
		cteateCell(wb, row, 7, HSSFCellStyle.ALIGN_LEFT, "选项D");
		cteateCell(wb, row, 7, HSSFCellStyle.ALIGN_LEFT, "试题解析");
		row=sheet.createRow(1);
		cteateCell(wb, row, 0, HSSFCellStyle.ALIGN_LEFT, 1);
		cteateCell(wb, row, 1, HSSFCellStyle.ALIGN_LEFT, "测试数据");
		cteateCell(wb, row, 2, HSSFCellStyle.ALIGN_LEFT, "以下属于成型零件的有");
		cteateCell(wb, row, 3, HSSFCellStyle.ALIGN_LEFT, "A");
		cteateCell(wb, row, 4, HSSFCellStyle.ALIGN_LEFT, "螺纹型芯");
		cteateCell(wb, row, 5, HSSFCellStyle.ALIGN_LEFT, "螺纹型环");
		cteateCell(wb, row, 6, HSSFCellStyle.ALIGN_LEFT, "凹模");
		cteateCell(wb, row, 7, HSSFCellStyle.ALIGN_LEFT, "浇口套");
		cteateCell(wb, row, 7, HSSFCellStyle.ALIGN_LEFT, "螺纹型芯&nbsp; 螺纹型环 凹模 镶件<br />");
		System.out.println(sheet.getLastRowNum());
/*		FileOutputStream fileOut = new FileOutputStream("questions.xls");
		wb.write(fileOut);
		fileOut.close();*/
	}

}
