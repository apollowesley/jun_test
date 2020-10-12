package com.evil.util;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.evil.pojo.Questions;

public class QuestionsUtil {
	public final static int typeNum = 3;// 试题类型的数量

	public enum QuestionType {
		single {
			public String getName() {
				return "单选题";
			}
		},
		multiple {
			public String getName() {
				return "多选题";
			}
		},
		judge {
			public String getName() {
				return "判断题";
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
		HSSFSheet sheet = wb.createSheet("单选题"); // 建立新的sheet对象
		HSSFRow row = sheet.createRow(0);
		// 在sheet里创建一行，参数为行号（第一行，此处可想象成数组）
		cteateCell(wb, row, 0, HSSFCellStyle.ALIGN_LEFT, "序号");
		cteateCell(wb, row, 1, HSSFCellStyle.ALIGN_LEFT, "模板名称");
		cteateCell(wb, row, 2, HSSFCellStyle.ALIGN_LEFT, "题目");
		cteateCell(wb, row, 3, HSSFCellStyle.ALIGN_LEFT, "正确答案");
		cteateCell(wb, row, 4, HSSFCellStyle.ALIGN_LEFT, "选项A");
		cteateCell(wb, row, 5, HSSFCellStyle.ALIGN_LEFT, "选项B");
		cteateCell(wb, row, 6, HSSFCellStyle.ALIGN_LEFT, "选项C");
		cteateCell(wb, row, 7, HSSFCellStyle.ALIGN_LEFT, "选项D");
		cteateCell(wb, row, 8, HSSFCellStyle.ALIGN_LEFT, "试题解析");
		sheet = wb.createSheet("多选题"); // 建立新的sheet对象
		row = sheet.createRow(0);
		cteateCell(wb, row, 0, HSSFCellStyle.ALIGN_LEFT, "序号");
		cteateCell(wb, row, 1, HSSFCellStyle.ALIGN_LEFT, "模板名称");
		cteateCell(wb, row, 2, HSSFCellStyle.ALIGN_LEFT, "题目");
		cteateCell(wb, row, 3, HSSFCellStyle.ALIGN_LEFT, "正确答案");
		cteateCell(wb, row, 4, HSSFCellStyle.ALIGN_LEFT, "选项A");
		cteateCell(wb, row, 5, HSSFCellStyle.ALIGN_LEFT, "选项B");
		cteateCell(wb, row, 6, HSSFCellStyle.ALIGN_LEFT, "选项C");
		cteateCell(wb, row, 7, HSSFCellStyle.ALIGN_LEFT, "选项D");
		cteateCell(wb, row, 8, HSSFCellStyle.ALIGN_LEFT, "试题解析");
		sheet = wb.createSheet("判断题"); // 建立新的sheet对象
		row = sheet.createRow(0);
		cteateCell(wb, row, 0, HSSFCellStyle.ALIGN_LEFT, "序号");
		cteateCell(wb, row, 1, HSSFCellStyle.ALIGN_LEFT, "模板名称");
		cteateCell(wb, row, 2, HSSFCellStyle.ALIGN_LEFT, "题目");
		cteateCell(wb, row, 3, HSSFCellStyle.ALIGN_LEFT, "正确答案");
		cteateCell(wb, row, 4, HSSFCellStyle.ALIGN_LEFT, "试题解析");
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
			// 返回布尔类型的值
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			// 返回数值类型的值
			return String.valueOf(hssfCell.getNumericCellValue());
		} else {
			// 返回字符串类型的值
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}

}
