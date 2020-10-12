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
		HSSFSheet sheet = wb.createSheet("new sheet"); //�����µ�sheet����
		HSSFRow row = sheet.createRow((short)0);
		//��sheet�ﴴ��һ�У�����Ϊ�кţ���һ�У��˴�����������飩
		HSSFCell cell = row.createCell(0);
		//��row�ｨ����cell����Ԫ�񣩣�����Ϊ�кţ���һ�У�
		cell.setCellValue(1);//����cell���������͵�ֵ
		row.createCell(1).setCellValue(1.2); //����cell�������͵�ֵ
		row.createCell(2).setCellValue("test"); //����cell�ַ����͵�ֵ
		row.createCell(3).setCellValue(true); //����cell�������͵�ֵ 
		HSSFCellStyle cellStyle = wb.createCellStyle(); //�����µ�cell��ʽ
		cellStyle.setDataFormat(HSSFDataFormat. getBuiltinFormat("m/d/yy h:mm"));
		//����cell��ʽΪ���Ƶ����ڸ�ʽ
		HSSFCell dCell =row.createCell(4);
		dCell.setCellValue(new Date()); //����cellΪ�������͵�ֵ
		dCell.setCellStyle(cellStyle); //���ø�cell���ڵ���ʾ��ʽ
		HSSFCell csCell =row.createCell(5);
//		csCell.setEncoding(HSSFCell.ENCODING_UTF_16);
		//����cell���������ĸ�λ�ֽڽض�
		csCell.setCellValue("���Ĳ���_Chinese Words Test"); //���������Ľ���ַ���
		row.createCell(6).setCellType(HSSFCell.CELL_TYPE_ERROR);
		//��������cell
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
		HSSFSheet sheet = wb.createSheet("��ѡ��"); //�����µ�sheet����
		HSSFRow row = sheet.createRow(0);
		//��sheet�ﴴ��һ�У�����Ϊ�кţ���һ�У��˴�����������飩
		cteateCell(wb, row, 0, HSSFCellStyle.ALIGN_LEFT, "���");
		cteateCell(wb, row, 1, HSSFCellStyle.ALIGN_LEFT, "ģ������");
		cteateCell(wb, row, 2, HSSFCellStyle.ALIGN_LEFT, "��Ŀ");
		cteateCell(wb, row, 3, HSSFCellStyle.ALIGN_LEFT, "��ȷ��");
		cteateCell(wb, row, 4, HSSFCellStyle.ALIGN_LEFT, "ѡ��A");
		cteateCell(wb, row, 5, HSSFCellStyle.ALIGN_LEFT, "ѡ��B");
		cteateCell(wb, row, 6, HSSFCellStyle.ALIGN_LEFT, "ѡ��C");
		cteateCell(wb, row, 7, HSSFCellStyle.ALIGN_LEFT, "ѡ��D");
		cteateCell(wb, row, 7, HSSFCellStyle.ALIGN_LEFT, "�������");
		row=sheet.createRow(1);
		cteateCell(wb, row, 0, HSSFCellStyle.ALIGN_LEFT, 1);
		cteateCell(wb, row, 1, HSSFCellStyle.ALIGN_LEFT, "��������");
		cteateCell(wb, row, 2, HSSFCellStyle.ALIGN_LEFT, "�������ڳ����������");
		cteateCell(wb, row, 3, HSSFCellStyle.ALIGN_LEFT, "A");
		cteateCell(wb, row, 4, HSSFCellStyle.ALIGN_LEFT, "������о");
		cteateCell(wb, row, 5, HSSFCellStyle.ALIGN_LEFT, "�����ͻ�");
		cteateCell(wb, row, 6, HSSFCellStyle.ALIGN_LEFT, "��ģ");
		cteateCell(wb, row, 7, HSSFCellStyle.ALIGN_LEFT, "������");
		cteateCell(wb, row, 7, HSSFCellStyle.ALIGN_LEFT, "������о&nbsp; �����ͻ� ��ģ ���<br />");
		System.out.println(sheet.getLastRowNum());
/*		FileOutputStream fileOut = new FileOutputStream("questions.xls");
		wb.write(fileOut);
		fileOut.close();*/
	}

}
