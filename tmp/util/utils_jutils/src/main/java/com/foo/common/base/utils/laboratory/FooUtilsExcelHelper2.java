package com.foo.common.base.utils.laboratory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.foo.common.base.pojo.FooGoodsModel;
import com.foo.common.base.utils.FooUtils;
import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;

/**
 * HSSF is the POI Project's pure Java implementation of the Excel '97(-2007)
 * file format. XSSF is the POI Project's pure Java implementation of the Excel
 * 2007 OOXML (.xlsx) file format.
 * 
 * @author Steve
 *
 */
public class FooUtilsExcelHelper2 {
	public void getExcels() {

	}

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		readExcel_2003(null);
	}

	public static void readExcel_2003(String file)
			throws FileNotFoundException, IOException {

		final int rowStartIndex = 0;
		int dataStartIndex = rowStartIndex + 1;
		final int cellStartIndex = 0;
		final int supplierNameCellnum = 7;// locate at column: H
		final int brandNameCellnum = 8;// locate at column: I

		Row tmpRow = null;
		Cell tmpCell = null;
		int tmpIndex = 0;
		int tmpCellnum = 0;
		HSSFSheet tmpSheet = null;

		// HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(new File(
		// FooUtils.global_test_file_working_directory_str
		// + "\\型号导入模板.xls")));
		HSSFWorkbook wb = new HSSFWorkbook(
				FooUtils.getClassPathResourceInputStream("com/foo/common/base/template/file/型号导入模板.xls"));
		FileOutputStream stream = new FileOutputStream(
				FooUtils.getTestExcel2003File());

		// check template version,Start
		boolean isVersionCorrect = false;
		tmpSheet = wb.getSheet("version");
		if (tmpSheet == null || tmpSheet.getRow(0) == null
				|| tmpSheet.getRow(0).getCell(0) == null) {
			isVersionCorrect = false;
		} else {
			if (MoreObjects
					.firstNonNull(
							tmpSheet.getRow(0).getCell(0)
									.getRichStringCellValue(), "").toString()
					.equals("02ac516f-5edb-47f5-b24f-79f4981a3b56")) {
				isVersionCorrect = true;
			} else {
				isVersionCorrect = false;
			}
		}

		System.out.println("isVersionCorrect is:" + isVersionCorrect);

		// check template version,End

		Set<String> supplierNameSet = Sets.newTreeSet();
		supplierNameSet.add("smc");
		supplierNameSet.add("misumi");
		supplierNameSet.add("huawei");
		supplierNameSet.add("sony");

		Set<String> brandNameSet = Sets.newTreeSet();
		brandNameSet.add("品牌1");
		brandNameSet.add("品牌2");
		brandNameSet.add("品牌3");

		HSSFSheet sheet = wb.getSheet("data");
		int maxRowNum = Ints.max(supplierNameSet.size(), brandNameSet.size());
		for (int i = 1; i < maxRowNum + 1; i++) {
			sheet.createRow(i);
		}

		tmpRow = null;
		tmpCell = null;
		tmpIndex = dataStartIndex;
		tmpCellnum = cellStartIndex + supplierNameCellnum;
		for (String myValue : supplierNameSet) {
			tmpRow = sheet.getRow(tmpIndex++);
			tmpCell = tmpRow.createCell(tmpCellnum);
			tmpCell.setCellValue(myValue);
		}

		tmpRow = null;
		tmpCell = null;
		tmpIndex = dataStartIndex;
		tmpCellnum = cellStartIndex + brandNameCellnum;
		for (String myValue : brandNameSet) {
			tmpRow = sheet.getRow(tmpIndex++);
			tmpCell = tmpRow.createCell(tmpCellnum);
			tmpCell.setCellValue(myValue);
		}
		wb.write(stream);
		stream.close();
	}

	public static void writeExcel_2003(String file)
			throws FileNotFoundException, IOException {

		final int rowStartIndex = 0;
		int dataStartIndex = rowStartIndex + 1;
		final int cellStartIndex = 0;
		final int supplierNameCellnum = 7;// locate at column: H
		final int brandNameCellnum = 8;// locate at column: I

		Row tmpRow = null;
		Cell tmpCell = null;
		int tmpIndex = 0;
		int tmpCellnum = 0;
		HSSFSheet tmpSheet = null;

		// HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(new File(
		// FooUtils.global_test_file_working_directory_str
		// + "\\型号导入模板.xls")));
		HSSFWorkbook wb = new HSSFWorkbook(
				FooUtils.getClassPathResourceInputStream("com/foo/common/base/template/file/型号导入模板.xls"));
		FileOutputStream stream = new FileOutputStream(
				FooUtils.getTestExcel2003File());

		// check template version,Start
		boolean isVersionCorrect = false;
		tmpSheet = wb.getSheet("version");
		if (tmpSheet == null || tmpSheet.getRow(0) == null
				|| tmpSheet.getRow(0).getCell(0) == null) {
			isVersionCorrect = false;
		} else {
			if (MoreObjects
					.firstNonNull(
							tmpSheet.getRow(0).getCell(0)
									.getRichStringCellValue(), "").toString()
					.equals("02ac516f-5edb-47f5-b24f-79f4981a3b56")) {
				isVersionCorrect = true;
			} else {
				isVersionCorrect = false;
			}
		}

		System.out.println("isVersionCorrect is:" + isVersionCorrect);

		// check template version,End

		Set<String> supplierNameSet = Sets.newTreeSet();
		supplierNameSet.add("smc");
		supplierNameSet.add("misumi");
		supplierNameSet.add("huawei");
		supplierNameSet.add("sony");

		Set<String> brandNameSet = Sets.newTreeSet();
		brandNameSet.add("品牌1");
		brandNameSet.add("品牌2");
		brandNameSet.add("品牌3");

		HSSFSheet sheet = wb.getSheet("data");
		int maxRowNum = Ints.max(supplierNameSet.size(), brandNameSet.size());
		for (int i = 1; i < maxRowNum + 1; i++) {
			sheet.createRow(i);
		}

		tmpRow = null;
		tmpCell = null;
		tmpIndex = dataStartIndex;
		tmpCellnum = cellStartIndex + supplierNameCellnum;
		for (String myValue : supplierNameSet) {
			tmpRow = sheet.getRow(tmpIndex++);
			tmpCell = tmpRow.createCell(tmpCellnum);
			tmpCell.setCellValue(myValue);
		}

		tmpRow = null;
		tmpCell = null;
		tmpIndex = dataStartIndex;
		tmpCellnum = cellStartIndex + brandNameCellnum;
		for (String myValue : brandNameSet) {
			tmpRow = sheet.getRow(tmpIndex++);
			tmpCell = tmpRow.createCell(tmpCellnum);
			tmpCell.setCellValue(myValue);
		}
		wb.write(stream);
		stream.close();
	}

	public static void main2(String[] args) throws InvalidFormatException,
			FileNotFoundException, IOException {

		String myResultFileName = "d:\\result.xls";
		String myFileDirectory = "d:\\/temp/";

		List<FooGoodsModel> myResultList = Lists.newArrayList();
		File myDirectory = new File(myFileDirectory);
		for (File file : myDirectory.listFiles()) {

			// 保存结果数据集的list
			List<FooGoodsModel> myList = Lists.newArrayList();

			FooGoodsModel headModel = new FooGoodsModel();
			headModel = new FooGoodsModel();
			headModel.setSellsRank("NAME");
			headModel.setGoodsLabel("ACCESS_NUMBER");
			headModel.setGoodsName("MAC");
			headModel.setGoodsUnit("PASSWORD");
			myList.add(headModel);

			Workbook wb = WorkbookFactory.create(new FileInputStream(new File(
					myFileDirectory + file.getName())));
			// Sheet sheet = wb.getSheetAt(0);

			// 计算出需要统计的write的行数

			Row row = null;
			Cell cell = null;
			// 第一行为标题栏，跳过

			for (int mySheet = 0; mySheet < wb.getNumberOfSheets(); mySheet++) {

				Sheet sheet = wb.getSheetAt(mySheet);

				for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
					row = sheet.getRow(i);

					headModel = new FooGoodsModel();
					for (int k = 0; k < row.getLastCellNum(); k++) {
						if (k >= 4) {
							break;
						}
						cell = row.getCell(k);

						if (cell == null) {
							break;
						}

						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:
							if (k == 0) {
								headModel.setSellsRank(cell
										.getRichStringCellValue().getString());
							} else if (k == 1) {
								headModel.setGoodsLabel(cell
										.getRichStringCellValue().getString());
							} else if (k == 2) {
								headModel.setGoodsName(cell
										.getRichStringCellValue().getString());
							} else if (k == 3) {
								headModel.setGoodsUnit(cell
										.getRichStringCellValue().getString());
							} else {

							}
							break;
						case Cell.CELL_TYPE_NUMERIC:
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							// System.out.print(cell.getBooleanCellValue() +
							// "-");
							break;
						case Cell.CELL_TYPE_FORMULA:
							// System.out.print(cell.getCellFormula() + "-");
							break;
						default:
							// System.out.print("");
						}
					}
					String xxx = Strings.nullToEmpty(headModel.getSellsRank())
							.trim();
					if (xxx.contains("资阳")) {
						myResultList.add(headModel);
					}
				}
			}// 结束一个工作表的读取
		}// 结束读取全部的excel表格数据

		System.out.println(myResultList.size());

		// 数据准备完毕，开始写excel表格
		int rowIndex = 0;
		InputStream inp = new FileInputStream(myResultFileName);
		Workbook wb = WorkbookFactory.create(inp);
		Sheet sheet = wb.getSheetAt(0);
		for (FooGoodsModel fooGoodsModel : myResultList) {
			Row row = sheet.createRow(rowIndex++);
			Cell cell = null;
			cell = row.createCell(0);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(fooGoodsModel.getSellsRank());
			cell = row.createCell(1);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(fooGoodsModel.getGoodsLabel());
			cell = row.createCell(2);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(fooGoodsModel.getGoodsName());
			cell = row.createCell(3);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(fooGoodsModel.getGoodsUnit());
		}
		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream(myResultFileName);
		wb.write(fileOut);
		fileOut.close();
	}
}
