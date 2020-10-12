package com.qxzl.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

import com.qxzl.exception.BusinessException;
import com.qxzl.platform.web.entity.estate.EstateBase;
import com.qxzl.platform.web.entity.estate.EstateBaseBuffer;

/**
 * 解析excle
 * 
 * @author fc
 *
 *
 */
public class POIExcel {
/**

 <!-- poi Excel解析包 -->
 <dependency>
 <groupId>org.apache.poi</groupId>
 <artifactId>poi</artifactId>
 <version>3.14</version>
 </dependency>

 <dependency>
 <groupId>org.apache.poi</groupId>
 <artifactId>poi-ooxml</artifactId>
 <version>3.14</version>
 </dependency>
 <dependency>
 <groupId>org.apache.poi</groupId>
 <artifactId>poi-ooxml-schemas</artifactId>
 <version>3.14</version>
 </dependency>


 *
 */
	/**
	 * 测试方法
	 * 
	 * @param args
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 * @throws IOException
	 * @throws BusinessException 
	 */
	public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException, BusinessException {
		/*List<List<String>> lists =null; //readExcel("d:/江北区.xlsx");
		List<EstateBase> estateBases =null ;//convertModel(lists, 500000, 500001, 500112);
		for (EstateBase estateBase : estateBases) {
			System.out.print(estateBase.getName());
			System.out.print("\t");
			System.out.print(estateBase.getAddress());
			System.out.print("\t");
			System.out.print(estateBase.getDeveloper());
			System.out.print("\t");
			System.out.print(estateBase.getCompany());
			System.out.print("\t");
			System.out.print(estateBase.getNo());
			System.out.print("\t");
			System.out.print(estateBase.getHouseholdsNum());
			System.out.print("\t");
			System.out.print(estateBase.getCreate());
			System.out.print("\t");
			System.out.print(estateBase.getType());
			System.out.print("\t");
			System.out.print(estateBase.getPrice());
			System.out.print("\t");
			System.out.print(estateBase.getProvinceId());
			System.out.print("\t");
			System.out.print(estateBase.getCityId());
			System.out.print("\t");
			System.out.print(estateBase.getAreaId());
			System.out.println();
		}*/
	}

	/**
	 * 读取excel数据
	 * 
	 * @param fileUrl
	 *            文件地址
	 * @throws EncryptedDocumentException
	 *             异常
	 * @throws InvalidFormatException
	 *             异常
	 * @throws IOException
	 *             异常
	 */
	public static List<List<String>> readExcel(MultipartFile fileUrl) throws BusinessException, IOException, EncryptedDocumentException, InvalidFormatException {

		List<List<String>> list = new ArrayList<List<String>>();

		// 读取excel数据
		InputStream inputStream;
		try {
			inputStream = fileUrl.getInputStream();

			Workbook workbook;
			
				workbook = WorkbookFactory.create(inputStream);
				Sheet sheet = workbook.getSheetAt(0);
				// 为跳过第一行目录设置count
				int count = 0;
				// 总行数
				//System.out.println("该数据总行数为:" + (sheet.getLastRowNum()));

				for (Row row : sheet) {
					// 行
					List<String> hang = new ArrayList<String>();
					// 跳过第一行的目录
					if (count == 0) {
						count++;
						continue;
					}
					// 如果当前行没有数据，跳出循环
					if (row.getCell(0).toString().equals("")) {
						return null;
					}

					// 为跳过第8列目录设置count
					int lie_count = 0;

					for (Cell cell : row) {

						// 列

						if (lie_count == 13) {
							lie_count++;
							continue;
						}

						// 获取值并自己格式化
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:// 字符串型
							hang.add(cell.getRichStringCellValue().getString());
							break;
						case Cell.CELL_TYPE_NUMERIC:// 数值型
							if (DateUtil.isCellDateFormatted(cell)) { // 如果是date类型则 ，获取该cell的date值

								hang.add(cell.getDateCellValue().toString());
							} else {// 纯数字
								hang.add(cell.getNumericCellValue() + "");
							}
							break;
						case Cell.CELL_TYPE_BOOLEAN:// 布尔
							hang.add(cell.getBooleanCellValue() + "");
							break;
						case Cell.CELL_TYPE_FORMULA:// 公式型
							hang.add(cell.getCellFormula() + "");
							break;
						case Cell.CELL_TYPE_BLANK:// 空值
							hang.add("");
							break;
						case Cell.CELL_TYPE_ERROR: // 故障
							hang.add("");
							break;
						default:
							hang.add("");
						}
					}
					list.add(hang);
				}
			} catch (BusinessException e) {
				throw new BusinessException("ERROR", "excel解析失败");
			}

		
		return list;

	}

	/***
	 * 转换成实体类集合
	 * 
	 * @param lists excel转换集合
	 */
	public static List<EstateBaseBuffer> convertModel(List<List<String>> lists) {

		List<EstateBaseBuffer> estateBases = new ArrayList<EstateBaseBuffer>();

		for (List<String> list2 : lists) {
			EstateBaseBuffer base = new EstateBaseBuffer();
			int i = 0;
			for (String str : list2) {
				if (i == 0) {
					base.setProvince(str);
				}
				if (i == 1) {
					base.setCity(str);
				}
				if (i == 2) {
					base.setArea(str);
				}
				if (i == 3) {
					base.setStreet(str);
				}
				if (i == 4) {
					base.setName(str);
				}
				if (i == 5) {
					base.setHouseType(str);
				}
				if (i == 6) {
					base.setAddress(str);
				}
				if (i == 7) {
					base.setCompany(str);
				}
				if (i == 8) {
					base.setDeveloper(str);
				}
				if (i == 9) {
					base.setNo(str);
				}
				if (i == 10) {
					base.setHouseholdsNum(str);
				}
				if (i == 11) {
					base.setType(str);
				}
				if (i == 12) {
					base.setCreate(str);
				}
				if (i == 13) {
					base.setPropertyFee(str);
				}
				i++;
			}
			estateBases.add(base);
		}

		return estateBases;
	}

}
