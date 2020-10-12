/**
 * Copyright 2013 ABSir's Studio
 * 
 * All right reserved
 *
 * Create on 2013-9-25 下午3:57:49
 */
package com.absir.appserv.configure.xls;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;

import com.absir.core.kernel.KernelString;

/**
 * @author absir
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class XlsAccessorUtils {

	/** HSSF_DATA_FORMATTER */
	private static final HSSFDataFormatter HSSF_DATA_FORMATTER = new HSSFDataFormatter();

	/**
	 * @param hssfCell
	 * @param hssfDataFormatter
	 * @return
	 */
	public static String getCellValue(HSSFCell hssfCell) {
		if (hssfCell == null) {
			return null;
		}

		if (hssfCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			return HSSF_DATA_FORMATTER.formatCellValue(hssfCell);
		}

		return hssfCell.toString();
	}

	/**
	 * @param hssfSheet
	 * @return
	 */
	public static int[] accessorsSheet(HSSFSheet hssfSheet) {
		int[] accessors = new int[4];
		accessors[0] = 0;
		accessors[1] = 0;
		accessors[2] = hssfSheet.getPhysicalNumberOfRows();
		accessors[3] = hssfSheet.getRow(0).getPhysicalNumberOfCells();
		return accessors;
	}

	/** Base_Class_Map_Dao */
	private static final Map<Class, XlsDao> Base_Class_Map_Dao = new HashMap<Class, XlsDao>();

	/**
	 * @param xlsClass
	 * @return
	 */
	protected static <T extends XlsBase> XlsDao<T, Serializable> getXlsDao(Class<T> xlsClass) {
		return Base_Class_Map_Dao.get(xlsClass);
	}

	/**
	 * @param hssfSheet
	 * @param xlsClass
	 * @param xlsBase
	 * @return
	 */
	public static <T extends XlsBase> XlsDao<T, Serializable> getXlsDao(HSSFSheet hssfSheet, Class<T> xlsClass, XlsBase xlsBase, int firstRow, int firstColumn, int lastRow, int lastColumn) {
		XlsDao xlsDao = Base_Class_Map_Dao.get(xlsClass);
		if (xlsDao == null) {
			synchronized (xlsClass) {
				xlsDao = Base_Class_Map_Dao.get(xlsClass);
				if (xlsDao == null) {
					readXlsBeans(hssfSheet, xlsClass, xlsBase, firstRow, firstColumn, lastRow, lastColumn, true);
					xlsDao = Base_Class_Map_Dao.get(xlsClass);
				}
			}
		}

		return xlsDao;
	}

	/**
	 * @param hssfSheet
	 * @param beanClass
	 * @param xlsBase
	 * @param firstRow
	 * @param firstColumn
	 * @param lastRow
	 * @param lastColumn
	 * @param cacheable
	 * @return
	 */
	public static <T> List<T> getXlsBeans(HSSFSheet hssfSheet, Class<T> beanClass, XlsBase xlsBase, int firstRow, int firstColumn, int lastRow, int lastColumn, boolean cacheable) {
		if (cacheable && !XlsBase.class.isAssignableFrom(beanClass)) {
			cacheable = false;
		}

		return readXlsBeans(hssfSheet, beanClass, xlsBase, firstRow, firstColumn, lastRow, lastColumn, cacheable);
	}

	/**
	 * @param hssfSheet
	 * @param beanClass
	 * @param xlsBase
	 * @param firstRow
	 * @param firstColumn
	 * @param lastRow
	 * @param lastColumn
	 * @param cacheable
	 * @return
	 */
	private static <T> List<T> readXlsBeans(HSSFSheet hssfSheet, Class<T> beanClass, XlsBase xlsBase, int firstRow, int firstColumn, int lastRow, int lastColumn, boolean cacheable) {
		XlsAccessorContext xlsAccessorContext = new XlsAccessorContext(beanClass, xlsBase);
		int columnCount = xlsAccessorContext.getColumnCount();
		boolean[] headers = new boolean[columnCount];
		while (firstRow < lastRow) {
			int headerCount = 0;
			for (int column = firstColumn; column < lastColumn; column++) {
				if (headers[column]) {
					headerCount++;

				} else if (!KernelString.isEmpty(getCellValue(hssfSheet.getRow(firstRow).getCell(column)))) {
					headers[column] = true;
					headerCount++;
				}
			}

			firstRow++;
			if (headerCount >= columnCount) {
				break;
			}
		}

		List<Object> cells = new ArrayList<Object>();
		while (firstRow < lastRow) {
			firstRow = xlsAccessorContext.readHssfSheet(hssfSheet, cells, firstRow, firstColumn, lastRow);
		}

		int size = cells.size();
		final List<T> beans = new ArrayList(size);
		for (int i = 0; i < size; i++) {
			beans.add((T) xlsAccessorContext.newInstance(cells.get(i), xlsBase, i));
		}

		if (cacheable) {
			Class<? extends Serializable> idType = xlsAccessorContext.getIdType();
			if (XlsBean.class.isAssignableFrom(beanClass)) {
				final Map<Serializable, XlsBase> beanMap = new HashMap<Serializable, XlsBase>();
				for (XlsBean xlsBean : (List<XlsBean>) beans) {
					if (xlsBean.getId() != null) {
						beanMap.put(xlsBean.getId(), xlsBean);
					}
				}

				Base_Class_Map_Dao.put(beanClass, new XlsDao(idType) {

					@Override
					public XlsBase get(Serializable id) {
						// TODO Auto-generated method stub
						return beanMap.get(id);
					}

					@Override
					public Collection<? extends XlsBase> getAll() {
						// TODO Auto-generated method stub
						return beanMap.values();
					}

				});

			} else {
				Base_Class_Map_Dao.put(beanClass, new XlsDao(idType) {

					@Override
					public XlsBase get(Serializable id) {
						// TODO Auto-generated method stub
						Integer index = (Integer) id;
						return index < 0 || index >= beans.size() ? null : (XlsBase) beans.get(index);
					}

					@Override
					public Collection<? extends XlsBase> getAll() {
						// TODO Auto-generated method stub
						return (Collection<? extends XlsBase>) beans;
					}
				});
			}
		}

		/*
		boolean isXlsBase = XlsBase.class.isAssignableFrom(beanClass);
		Field updateTime = isXlsBase && JiUpdate.class.isAssignableFrom(beanClass) ? KernelReflect.declaredField(beanClass, "updateTime") : null;
		if (updateTime != null) {
			if (HelperAccessor.isAccessor(updateTime)) {
				updateTime = null;
			}
		}
		
		JEmbedSS embedSS = updateTime == null ? null : new JEmbedSS();
		
		long updateTimeMillis = System.currentTimeMillis();
		Object updateTimeValue = updateTime == null ? null : KernelDyna.to(updateTimeMillis, updateTime.getType());
		for (int i = 0; i < size; i++) {
			Object bean = beans.get(i);
			xlsAccessorContext.setObject(bean, cells.get(i), xlsBase, null);
			if (isXlsBase) {
				((XlsBase) bean).initializing();
			}

			if (updateTime != null) {
				embedSS.setEid(beanClass.getSimpleName());
				embedSS.setMid(KernelDyna.to(((XlsBase) bean).getId(), String.class));
				JUpdateXls updateXls = BeanServiceUtils.get().get(JUpdateXls.class, embedSS);
				String serialize = HelperJson.encodeNull(bean);
				if (updateXls == null) {
					updateXls = new JUpdateXls();
					updateXls.setId(embedSS);
				}

				if (!(updateXls.getSerialize() == null ? serialize == null : new String(updateXls.getSerialize()).equals(serialize))) {
					updateXls.setSerialize(serialize == null ? null : serialize.getBytes());
					updateXls.setUpdateTime(updateTimeMillis);
					BeanServiceUtils.get().merge(updateXls);
					KernelReflect.set(bean, updateTime, updateTimeValue);

				} else {
					KernelReflect.set(bean, updateTime, KernelDyna.to(updateXls.getUpdateTime(), updateTime.getType()));
				}
			}
		}
		*/

		return beans;
	}

	/**
	 * @param xlsClass
	 */
	public static <T extends XlsBase> XlsDao<T, ?> clearXlsDao(Class<T> xlsClass) {
		return Base_Class_Map_Dao.remove(xlsClass);
	}

	/**
	 * @param hssfSheet
	 * @param xlsCell
	 */
	public static void writeHssfSheet(HSSFSheet hssfSheet, XlsCell xlsCell) {
		int rows = hssfSheet.getPhysicalNumberOfRows();
		int rowCount = xlsCell.getRowCount();
		int columnCount = xlsCell.getColumnCount();
		for (int i = 0; i < rowCount; i++) {
			HSSFRow hssfRow = hssfSheet.createRow(i);
			for (int j = 0; j < columnCount; j++) {
				hssfRow.createCell(j);
			}
		}

		writeHssfSheet(hssfSheet, xlsCell, rows, 0, rowCount, columnCount);
	}

	/**
	 * @param hssfSheet
	 * @param xlsCell
	 * @param row
	 * @param column
	 * @param rowCount
	 * @param columnCount
	 */
	private static void writeHssfSheet(HSSFSheet hssfSheet, XlsCell xlsCell, int row, int column, int rowCount, int columnCount) {
		int basicRow = xlsCell.getBasicRow();
		if (basicRow > 0) {
			xlsCell.wirteHssfCell(hssfSheet.getRow(row).getCell(column));
		}

		if (xlsCell.getChildren() == null) {
			if (rowCount > 1 && columnCount > 0) {
				hssfSheet.addMergedRegion(new CellRangeAddress(row, row + rowCount - 1, column, column + columnCount - 1));
			}

		} else {
			if ((basicRow > 1 && columnCount > 0) || (basicRow > 0 && columnCount > 1)) {
				hssfSheet.addMergedRegion(new CellRangeAddress(row, row + basicRow - 1, column, column + columnCount - 1));
			}

			row += basicRow;
			for (List<XlsCell> xlsCells : xlsCell.getChildren()) {
				rowCount = XlsCell.getLineRowCount(xlsCells);
				int iColumn = column;
				for (XlsCell cell : xlsCells) {
					columnCount = cell.getColumnCount();
					writeHssfSheet(hssfSheet, cell, row, iColumn, rowCount, columnCount);
					iColumn += columnCount;
				}

				row += rowCount;
			}
		}
	}

	/**
	 * @param hssfSheet
	 * @param beanClass
	 * @param beans
	 */
	public static void writeHssfSheet(HSSFSheet hssfSheet, Class beanClass, Collection beans, XlsBase xlsBase) {
		XlsAccessorContext xlsAccessorContext = new XlsAccessorContext(beanClass, xlsBase);
		XlsCell xlsCell = xlsAccessorContext.getHeader();
		if (beans != null) {
			for (Object bean : beans) {
				xlsAccessorContext.writeXlsCells(xlsCell.addColumnList(null), bean, xlsBase);
			}
		}

		writeHssfSheet(hssfSheet, xlsCell);
	}
}
