package com.foo.common.base.utils.laboratory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.foo.common.base.pojo.FooGoodsModel;
import com.foo.common.base.utils.FooUtils;
import com.google.common.collect.Lists;

public class FooUtilsExcelHelper {
	public void getExcels() {

	}

	public static void main(String[] args) throws InvalidFormatException,
			FileNotFoundException, IOException {

		String myResultFileName = "d:\\june.xls";
		String myFileDirectory = "d:\\/temp/june/";
		double myFactor;

		List<FooGoodsModel> myResultList = Lists.newArrayList();
		File myDirectory = new File(myFileDirectory);
		for (File file : myDirectory.listFiles()) {

			// 保存结果数据集的list
			List<FooGoodsModel> myList = Lists.newArrayList();

			FooGoodsModel headModel = new FooGoodsModel();
			headModel.setSellsRank("");// 这个值在所有list数据完成后回填
			headModel.setGoodsPrice("销售总额");
			headModel.setGoodsSales("");// 这个值在所有list数据完成后回填
			myList.add(headModel);
			headModel = new FooGoodsModel();
			headModel.setSellsRank("销售排名");
			headModel.setGoodsLabel("商品条码");
			headModel.setGoodsName("商品名称");
			headModel.setGoodsUnit("单位");
			headModel.setGoodsBid("进价");
			headModel.setGoodsPrice("售价");
			headModel.setGoodsSales("销量");
			myList.add(headModel);

			if (file.getName().contains("油") || file.getName().contains("面条")) {
				myFactor = 1.13;
			} else {
				myFactor = 1.17;
			}

			Workbook wb = WorkbookFactory.create(new FileInputStream(new File(
					myFileDirectory + file.getName())));
			Sheet sheet = wb.getSheetAt(0);

			// 计算出需要统计的write的行数
			int myNewRow = (int) (Math.ceil(sheet.getLastRowNum() * 0.2));
			double salesAll = 0;

			Row row = null;
			Cell cell = null;
			// 第一行为标题栏，跳过
			for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
				row = sheet.getRow(i);
				// if (row == null) {
				// continue;
				// }
				headModel = new FooGoodsModel();
				headModel.setSellsRank(String.valueOf(i));
				for (int k = 0; k < row.getLastCellNum(); k++) {
					// line1 :商品条码
					// line2：商品名称
					// line4: 进价
					// line5: 零售价
					// line6: 销售数量
					// line8: 销售金额
					if (k != 1 && k != 2 && k != 4 && k != 5 && k != 6
							&& k != 8) {
						continue;
					}

					cell = row.getCell(k);

					// if (cell == null) {
					// continue;
					// }

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						if (k == 1) {
							headModel.setGoodsLabel(cell
									.getRichStringCellValue().getString());
						} else if (k == 2) {
							headModel.setGoodsName(cell
									.getRichStringCellValue().getString());
						} else {
						}
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if (k == 4) {
							headModel.setGoodsBid(FooUtils.formatDouble(cell
									.getNumericCellValue() / myFactor));
						} else if (k == 5) {
							headModel.setGoodsPrice(FooUtils.formatDouble(cell
									.getNumericCellValue() / myFactor));
						} else if (k == 6) {
							headModel.setGoodsSales(String.valueOf((int) (cell
									.getNumericCellValue())));
						} else if (k == 8) {
							salesAll += cell.getNumericCellValue();
						} else {
						}
						if (DateUtil.isCellDateFormatted(cell)) {
						} else {
						}
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						// System.out.print(cell.getBooleanCellValue() + "-");
						break;
					case Cell.CELL_TYPE_FORMULA:
						// System.out.print(cell.getCellFormula() + "-");
						break;
					default:
						// System.out.print("");
					}
				}
				// 每一行结束时候，如果在myNewRow范围内，就把这个对象保存到list中
				if (i <= myNewRow) {
					myList.add(headModel);
				}
			}// 结束一个工作表的读取

			// 写入销售总额
			myList.get(0).setSellsRank(file.getName().replace(".xls", ""));
			myList.get(0).setGoodsSales(
					FooUtils.formatDouble(salesAll / myFactor));

			// 加入一行空白行
			List<FooGoodsModel> myEmpty = Lists.newArrayList();
			myEmpty.add(new FooGoodsModel());

			// 追加到已有的list后
			myResultList.addAll(myList);
			myResultList.addAll(myEmpty);
		}// 结束读取全部的excel表格数据

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
			cell = row.createCell(4);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(fooGoodsModel.getGoodsBid());
			cell = row.createCell(5);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(fooGoodsModel.getGoodsPrice());
			cell = row.createCell(6);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(fooGoodsModel.getGoodsSales());
			// System.out.println(fooGoodsModel.toString());
		}
		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream(myResultFileName);
		wb.write(fileOut);
		fileOut.close();
	}
}
