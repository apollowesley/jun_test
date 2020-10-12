package com.kld.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelUtil {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

	/**
	 * 设置response
	 * 
	 * @param response
	 * @param fileName
	 * @throws UnsupportedEncodingException
	 */
	public static void setReponse(final HttpServletResponse response, final String fileName)
			throws UnsupportedEncodingException {
		// 设置contentType，用于导出excel
		response.reset();
		// 类型为application/vnd.ms-excel以使用Excel
		response.setContentType("application/vnd.ms-excel");
		// 要求浏览器询问用户将响应存储在磁盘上给定名称的文件中
		response.setHeader("Content-Disposition", "filename=" + fileName + ".xls");
	}

	/**
	 * @param res
	 * @param data
	 * @param propNames
	 * @param headStrs
	 * @return
	 * @throws Exception
	 */
	public static void writeHeaderToStream(final HttpServletResponse res, final String[] propNames,final String[] headStrs) throws Exception {
		ServletOutputStream os = null;
		try {
			setReponse(res, "QueryResult");
			os = res.getOutputStream();
			os.write(getHtmlBegin(propNames, headStrs).getBytes());

		} catch (final Exception e) {
			try {
				if (null != os) {
					os.close();
				}
			} catch (IOException e2) {
				throw new Exception("关闭输出流出错：" + e.getMessage(), e);
			}
			throw new Exception("导出ExcelBegin出错：" + e.getMessage(), e);
		}
	}

	/**
	 * @param out
	 * @param data
	 * @param propNames
	 * @param fieldIndexs
	 * @return
	 * @throws Exception
	 */
	public static void writeDataToOutputStream(final HttpServletResponse res, final List data, final String[] propNames,
			final int[] fieldIndexs) throws Exception {

		ServletOutputStream os = null;
		try {
			os = res.getOutputStream();
			for (Iterator it = data.iterator(); it.hasNext();) {
				Object obj = it.next();
				os.write(getDataHtml(obj, propNames, fieldIndexs, true).getBytes());
			}
		} catch (final Exception e) {
			try {
				if (null != os) {
					os.close();
				}
			} catch (IOException e2) {
				throw new Exception("关闭输出流出错：" + e.getMessage(), e);
			}
			throw new Exception("导出ExcelData出错：" + e.getMessage(), e);
		}
	}

	/**
	 * @param out
	 * @param obj
	 * @param propNames
	 * @param fieldIndexs
	 * @return
	 * @throws Exception
	 */
	public static void writeObjectToOutputStream(final HttpServletResponse res, final Object obj,
			final String[] propNames, final int[] fieldIndexs) throws Exception {

		ServletOutputStream os = null;
		try {
			os = res.getOutputStream();
			os.write(getDataHtml(obj, propNames, fieldIndexs, true).getBytes());
		} catch (final Exception e) {
			try {
				if (null != os) {
					os.close();
				}
			} catch (IOException e2) {
				throw new Exception("关闭输出流出错：" + e.getMessage(), e);
			}
			throw new Exception("导出ExcelData出错：" + e.getMessage(), e);
		}
	}

	/**
	 * @param out
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	public static void flushOutputStream(final HttpServletResponse res) throws Exception {

		ServletOutputStream os = null;
		try {
			os = res.getOutputStream();
			os.write(getHtmlEnd().getBytes());
			os.flush();
		} catch (final Exception e) {
			throw new Exception("导出ExcelEnd出错：" + e.getMessage(), e);
		} finally {
			try {
				if (null != os) {
					os.close();
				}
			} catch (IOException e) {
				throw new Exception("关闭输出流出错：" + e.getMessage(), e);
			}
		}
	}

	/**
	 * @param propNames
	 * @param headStrs
	 * @param fieldIndexs
	 * @return
	 */
	public static String getHtmlBegin(final String[] propNames, final String[] headStrs) {
		StringBuffer htmlstr = new StringBuffer();
		htmlstr.append("<html xmlns:o=\"urn:schemas-microsoft-com:office:office\"");
		htmlstr.append(" xmlns:x=\"urn:schemas-microsoft-com:office:excel\"");
		htmlstr.append(" xmlns=\"http://www.w3.org/TR/REC-html40\">\n");

		htmlstr.append("<head>\n");
		htmlstr.append("<meta http-equiv=Content-Type content=\"text/html; charset=gb2312\">\n");
		htmlstr.append("<meta name=ProgId content=Excel.Sheet>\n");
		htmlstr.append("<meta name=Generator content=\"Microsoft Excel 11\">\n");
		htmlstr.append("</head>\n ");
		htmlstr.append("<body>\n ");
		htmlstr.append("<table x:str border=1 ");
		htmlstr.append("style='border-collapse: collapse;table-layout:fixed;'>\n");
		htmlstr.append("<tr style='height:14.25pt'>\n");
		for (int i = 0; i < propNames.length; i++) {
			if (null == propNames[i] || "".equals(propNames[i])) {
				continue;
			}
			htmlstr.append("<td style='height:14.25pt;width:80pt'>");
			htmlstr.append(headStrs[i]);
			htmlstr.append("</td>\n");
		}
		htmlstr.append("</tr>\n");
		return htmlstr.toString();
	}

	public static String getDataHtml(final Object obj, final String[] fields, final int[] fieldIndexs,
			final boolean convertCode2Label)
					throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		StringBuffer htmlstr = new StringBuffer("<tr style='height:14.25pt'>\n");
		boolean isBean = true;
		if (obj instanceof Object[]) {
			isBean = false;
		}
		for (int col = 0; col < fields.length; col++) {
			final String field = fields[col];
			if (null == field || "".equals(field)) {
				continue;
			}
			Object value = null;
			if (isBean) {
				value = PropertyUtils.getSimpleProperty(obj, field);
			} else {
				int fidx = col;
				if (fieldIndexs != null && fieldIndexs.length > col && fieldIndexs[col] >= 0) {
					fidx = fieldIndexs[col];
				}
				value = ((Object[]) obj)[fidx];
			}
			// 代码表处理，代码值只充许String类型
			// if (convertCode2Label && value instanceof String) {
			// final ServletContext cache = LeafCache.getAppContext();
			// if (cache != null) {
			// final TreeMap list = CodeListUtil.getBizCode(cache, field);
			// if (null != list && null != value) {
			// final Object code = list.get(value);
			// if (code != null) {
			// value = code;
			// }
			// }
			// }
			// }
			if (value == null) {
				value = " ";
			}
			htmlstr.append("<td style='height:14.25pt;width:80pt'>");
			htmlstr.append(value);
			htmlstr.append("</td>\n");
		}
		htmlstr.append("</tr>\n");
		return htmlstr.toString();
	}

	public static String getHtmlEnd() {
		StringBuffer htmlstr = new StringBuffer();
		htmlstr.append("</table>\n");
		htmlstr.append(" </body>\n");
		htmlstr.append(" </html> ");
		return htmlstr.toString();
	}

	/**
	 * 通用查询导出Excel，支持Table标签
	 *
	 * @param res
	 * @param data
	 * @param headerMeta
	 * @return
	 * @throws Exception
	 */
	public static void exportDataAsExcel(final HttpServletResponse res, final List data, final String headerMeta)throws Exception {
		final String[] headers = headerMeta.split("[ ,]");
		final int count = headers.length / 3;
		final String[] headStrs = new String[count];
		final String[] propNames = new String[count];
		final int[] hIndexs = new int[count];
		for (int i = 0, j; i < count; i++) {
			j = i * 3;
			propNames[i] = headers[j];
			hIndexs[i] = Integer.parseInt(headers[j + 2]);
			headStrs[i] = headers[j + 1];
		}
	}

	/**
	 * @param res
	 * @param data
	 * @param propNames
	 * @param headStrs
	 * @param fieldIndexs
	 * @return
	 * @throws Exception
	 */
	public static void exportDataAs2007Excel(final HttpServletResponse res, final List data, final String[] propNames,final String[] headStrs, String title) throws Exception {
		res.reset();
		XSSFWorkbook book;
		try {
			book = new XSSFWorkbook();
			if (StringUtils.isBlank(title)) {
				title = "aaa";
			}else{
				title = title + DateUtils.currentDate("yyyyMMddHHmmss");
			}
			XSSFSheet xs = book.createSheet(title);
			XSSFRow xr;
			XSSFCell xc;
			xr = xs.createRow(0);
			for (int i = 0; i < propNames.length; i++) {
				if (null == propNames[i] || "".equals(propNames[i])) {
					continue;
				}
				xc = xr.createCell(i);
				xc.setCellValue(headStrs[i]);
			}
			final int dataEnd = data.size(), dataStart = 0;
			
			Field[] filed = data.get(0).getClass().getDeclaredFields();
			Map hm = new HashMap();
			
			for(Field f : filed){
				hm.put(f.getName(),f.getName());
			}
			
			Object value = null;
			
			int excelStartRow = 1;
			for (int datarow = dataStart; datarow < dataEnd; excelStartRow++, datarow++) {
				final Object bean = data.get(datarow);
				xr = xs.createRow(excelStartRow);
				for (int col = 0; col < propNames.length; col++) {
					xc = xr.createCell(col);
					final String field = propNames[col];
					if (null == field || "".equals(field)) {
						continue;
					}
					
					if (hm.containsKey(field)) {
						value = PropertyUtils.getSimpleProperty(bean, field);
						if (value == null) {
							value = " ";
						}
					}else value = "";
					
					if(value instanceof Date){
						xc.setCellValue(DateUtils.formatTime((Date)value));
					} else {
						xc.setCellValue(value.toString());
					}
				}
			}
			res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			res.setHeader("Content-disposition","attachment;filename=" + java.net.URLEncoder.encode(title, "UTF-8") + ".xlsx");

			book.write(res.getOutputStream());

		} catch (final Exception e) {
			logger.error("导出Excel出错：{}", e);
			throw new Exception("导出Excel出错：" + e.getMessage(), e);
		}
	}

	public static Object getCellValue(Cell cell) {
		Object cellValue = null;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			cellValue = StringUtils.trim(cell.getRichStringCellValue().getString());
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				// cellValue = cell.getDateCellValue();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				cellValue = sdf.format(cell.getDateCellValue());
			} else {
				double d = cell.getNumericCellValue();
				BigDecimal bd = new BigDecimal(d);
				cellValue = bd.toPlainString();
			}
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			cellValue = cell.getBooleanCellValue();
			break;
		case Cell.CELL_TYPE_FORMULA:
			// cell.getCellFormula();
			try {
				/*
				 * 此处判断使用公式生成的字符串有问题，因为HSSFDateUtil.isCellDateFormatted(cell) 判断过程中cell.getNumericCellValue();方法会抛出java.lang.NumberFormatException异常
				 */
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					cellValue = sdf.format(cell.getDateCellValue());
					break;
				} else {
					cellValue = String.valueOf(cell.getNumericCellValue());
				}
			} catch (Exception e) {
				cellValue = String.valueOf(cell.getRichStringCellValue());
			}
			break;
		default:
			cellValue = "";
		}
		return cellValue;
	}

	public static Object getCellValueNoFomat(Cell cell) {
		Object cellValue = null;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			cellValue = StringUtils.trim(cell.getRichStringCellValue().getString());
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				// cellValue = cell.getDateCellValue();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				cellValue = sdf.format(cell.getDateCellValue());
			} else {
				double d = cell.getNumericCellValue();
				BigDecimal bd = new BigDecimal(d);
				// cellValue = cell.getNumericCellValue();
				// java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
				// nf.setGroupingUsed(false);
				// cellValue = nf.format(cellValue);
				cellValue = bd.toPlainString();
			}
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			cellValue = cell.getBooleanCellValue();
			break;
		case Cell.CELL_TYPE_FORMULA:
			// cell.getCellFormula();
			try {
				/*
				 * 此处判断使用公式生成的字符串有问题，因为HSSFDateUtil.isCellDateFormatted(cell) 判断过程中cell.getNumericCellValue();方法会抛出java.lang.NumberFormatException异常
				 */
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					cellValue = sdf.format(cell.getDateCellValue());
					break;
				} else {
					cellValue = String.valueOf(cell.getNumericCellValue());
				}
			} catch (Exception e) {
				cellValue = String.valueOf(cell.getRichStringCellValue());
			}
			break;
		default:
			cellValue = "";
		}
		return cellValue;
	}

}
