package com.evil.util;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.evil.pojo.Questions;

public class QuestionsUtil {
	public final static int typeNum = 3;// �������͵�����

	public enum QuestionType {
		single {
			public String getName() {
				return "��ѡ��";
			}
		},
		multiple {
			public String getName() {
				return "��ѡ��";
			}
		},
		judge {
			public String getName() {
				return "�ж���";
			}
		};
		public abstract String getName();
	}

	public static void crateQuestionExcel(HSSFWorkbook wb,
			List<Questions> questions) {
		crateQuestionsExcelHead(wb);
		for (Questions q : questions) {
			witeQuestionExcel(wb, q);
		}

	}

	private static void cteateCell(HSSFWorkbook wb, HSSFRow row, int col,
			short align, Object val) {
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(val == null ? "" : val.toString());
		HSSFCellStyle cellstyle = wb.createCellStyle();
		cellstyle.setAlignment(align);
		cell.setCellStyle(cellstyle);
	}

	private static void crateQuestionsExcelHead(HSSFWorkbook wb) {
		HSSFSheet sheet = wb.createSheet("��ѡ��"); // �����µ�sheet����
		HSSFRow row = sheet.createRow(0);
		// ��sheet�ﴴ��һ�У�����Ϊ�кţ���һ�У��˴�����������飩
		cteateCell(wb, row, 0, HSSFCellStyle.ALIGN_LEFT, "���");
		cteateCell(wb, row, 1, HSSFCellStyle.ALIGN_LEFT, "ģ������");
		cteateCell(wb, row, 2, HSSFCellStyle.ALIGN_LEFT, "��Ŀ");
		cteateCell(wb, row, 3, HSSFCellStyle.ALIGN_LEFT, "��ȷ��");
		cteateCell(wb, row, 4, HSSFCellStyle.ALIGN_LEFT, "ѡ��A");
		cteateCell(wb, row, 5, HSSFCellStyle.ALIGN_LEFT, "ѡ��B");
		cteateCell(wb, row, 6, HSSFCellStyle.ALIGN_LEFT, "ѡ��C");
		cteateCell(wb, row, 7, HSSFCellStyle.ALIGN_LEFT, "ѡ��D");
		cteateCell(wb, row, 8, HSSFCellStyle.ALIGN_LEFT, "�������");
		sheet = wb.createSheet("��ѡ��"); // �����µ�sheet����
		row = sheet.createRow(0);
		cteateCell(wb, row, 0, HSSFCellStyle.ALIGN_LEFT, "���");
		cteateCell(wb, row, 1, HSSFCellStyle.ALIGN_LEFT, "ģ������");
		cteateCell(wb, row, 2, HSSFCellStyle.ALIGN_LEFT, "��Ŀ");
		cteateCell(wb, row, 3, HSSFCellStyle.ALIGN_LEFT, "��ȷ��");
		cteateCell(wb, row, 4, HSSFCellStyle.ALIGN_LEFT, "ѡ��A");
		cteateCell(wb, row, 5, HSSFCellStyle.ALIGN_LEFT, "ѡ��B");
		cteateCell(wb, row, 6, HSSFCellStyle.ALIGN_LEFT, "ѡ��C");
		cteateCell(wb, row, 7, HSSFCellStyle.ALIGN_LEFT, "ѡ��D");
		cteateCell(wb, row, 8, HSSFCellStyle.ALIGN_LEFT, "�������");
		sheet = wb.createSheet("�ж���"); // �����µ�sheet����
		row = sheet.createRow(0);
		cteateCell(wb, row, 0, HSSFCellStyle.ALIGN_LEFT, "���");
		cteateCell(wb, row, 1, HSSFCellStyle.ALIGN_LEFT, "ģ������");
		cteateCell(wb, row, 2, HSSFCellStyle.ALIGN_LEFT, "��Ŀ");
		cteateCell(wb, row, 3, HSSFCellStyle.ALIGN_LEFT, "��ȷ��");
		cteateCell(wb, row, 4, HSSFCellStyle.ALIGN_LEFT, "�������");
	}

	private static void witeQuestionExcel(HSSFWorkbook wb, Questions q) {
		HSSFSheet sheet = wb.getSheetAt(q.getQuestionsType() == null ? 0 : q
				.getQuestionsType());
		int rowNum = sheet.getLastRowNum() + 1;
		HSSFRow row = sheet.createRow(rowNum);
		cteateCell(wb, row, 0, HSSFCellStyle.ALIGN_LEFT, rowNum);
		cteateCell(wb, row, 1, HSSFCellStyle.ALIGN_LEFT, q
				.getQuestionTemplate().getName());
		cteateCell(wb, row, 2, HSSFCellStyle.ALIGN_LEFT, q.getTitle());
		cteateCell(wb, row, 3, HSSFCellStyle.ALIGN_LEFT, q.getAnswer());
		if (q.getQuestionsType() > 1) {
			cteateCell(wb, row, 4, HSSFCellStyle.ALIGN_LEFT, q.getContent());
		} else {
			cteateCell(wb, row, 4, HSSFCellStyle.ALIGN_LEFT, q.getOptionA());
			cteateCell(wb, row, 5, HSSFCellStyle.ALIGN_LEFT, q.getOptionB());
			cteateCell(wb, row, 6, HSSFCellStyle.ALIGN_LEFT, q.getOptionC());
			cteateCell(wb, row, 7, HSSFCellStyle.ALIGN_LEFT, q.getOptionD());
			cteateCell(wb, row, 8, HSSFCellStyle.ALIGN_LEFT, q.getContent());
		}
	}

	@SuppressWarnings("static-access")
	public static String getHSSFCellValue(HSSFCell hssfCell) {
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			// ���ز������͵�ֵ
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			// ������ֵ���͵�ֵ
			return String.valueOf(hssfCell.getNumericCellValue());
		} else {
			// �����ַ������͵�ֵ
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}

}
