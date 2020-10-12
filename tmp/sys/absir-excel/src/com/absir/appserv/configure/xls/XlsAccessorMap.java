/**
 * Copyright 2013 ABSir's Studio
 * 
 * All right reserved
 *
 * Create on 2013-9-27 下午4:23:42
 */
package com.absir.appserv.configure.xls;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.absir.appserv.configure.xls.value.XaReferenced;
import com.absir.core.dyna.DynaBinder;
import com.absir.core.kernel.KernelLang.ObjectTemplate;
import com.absir.core.kernel.KernelString;

/**
 * @author absir
 * 
 */
@SuppressWarnings("unchecked")
public class XlsAccessorMap extends XlsAccessorBean {

	/** keyClass */
	protected Class<?> keyClass;

	/** keyAccessors */
	protected List<XlsAccessor> keyAccessors;

	/**
	 * @param field
	 * @param beanClass
	 */
	public XlsAccessorMap(Field field, Class<?> keyClass, Class<?> beanClass, XlsBase xlsBase) {
		super(field, beanClass);
		// TODO Auto-generated constructor stub
		this.keyClass = keyClass;
		this.beanClass = beanClass;
		XaReferenced xaReferenced = field.getAnnotation(XaReferenced.class);
		if (!xlsBase.is(keyClass) && (xaReferenced == null || xaReferenced.key())) {
			keyAccessors = getXlsAccessors(keyClass, xlsBase);
		}

		if (!xlsBase.is(beanClass) && (xaReferenced == null || xaReferenced.value())) {
			accessors = getXlsAccessors(beanClass, xlsBase);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.absir.appserv.configure.xls.XlsAccessor#isMulti()
	 */
	@Override
	public boolean isMulti() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.absir.appserv.configure.xls.XlsAccessor#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		int column = 0;
		if (keyAccessors == null) {
			column += 1;

		} else {
			for (XlsAccessor accessor : keyAccessors) {
				column += accessor.getColumnCount();
			}
		}

		if (accessors == null) {
			column += 1;

		} else {
			for (XlsAccessor accessor : accessors) {
				column += accessor.getColumnCount();
			}
		}

		return column;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.absir.appserv.configure.xls.XlsAccessor#readHssfSheet(org.apache
	 * .poi.hssf.usermodel.HSSFSheet, java.util.List, int, int, int)
	 */
	@Override
	public int readHssfSheet(HSSFSheet hssfSheet, List<Object> cells, int firstRow, int firstColumn, int lastRow) {
		List<Object> list = new ArrayList<Object>();
		while (firstRow < lastRow) {
			int kColumn = firstColumn;
			int iLastRow = lastRow;
			if (keyAccessors == null) {
				kColumn++;

			} else {
				for (XlsAccessor accessor : keyAccessors) {
					if (!accessor.isMulti()) {
						String value = XlsAccessorUtils.getCellValue(hssfSheet.getRow(firstRow).getCell(kColumn));
						for (int i = firstRow + 1; i < lastRow; i++) {
							String next = XlsAccessorUtils.getCellValue(hssfSheet.getRow(i).getCell(kColumn));
							if (!(KernelString.isEmpty(next) || next.equals(value))) {
								iLastRow = i;
							}
						}
					}

					kColumn += accessor.getColumnCount();
				}
			}

			int vColumn = kColumn;
			if (accessors != null) {
				for (XlsAccessor accessor : accessors) {
					if (!accessor.isMulti()) {
						String value = XlsAccessorUtils.getCellValue(hssfSheet.getRow(firstRow).getCell(vColumn));
						for (int i = firstRow + 1; i < lastRow; i++) {
							String next = XlsAccessorUtils.getCellValue(hssfSheet.getRow(i).getCell(vColumn));
							if (!(KernelString.isEmpty(next) || next.equals(value))) {
								iLastRow = i;
							}
						}
					}

					vColumn += accessor.getColumnCount();
				}
			}

			List<Object> map = new ArrayList<Object>();
			vColumn = readHssfSheet(hssfSheet, map, keyAccessors, firstRow, firstColumn, iLastRow);
			firstRow = readHssfSheet(hssfSheet, map, accessors, firstRow, kColumn, iLastRow);
			if (firstRow < vColumn) {
				firstRow = vColumn;
			}

			list.add(map);
		}

		cells.add(list);
		return lastRow;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.absir.appserv.configure.xls.XlsAccessorUtils.XlsAccessorBean#
	 * setObject(java.lang.Object, java.lang.Object,
	 * com.absir.appserv.configure.xls.XlsBase,
	 * com.absir.core.kernel.KernelLang.ObjectTemplate)
	 */
	@Override
	public void setObject(Object obj, Object cell, XlsBase xlsBase, ObjectTemplate<Boolean> empty) {
		List<List<?>> cells = (List<List<?>>) cell;
		int size = cells.size();
		Map<Object, Object> beanMap = new HashMap<Object, Object>(size);
		for (int i = 0; i < size; i++) {
			List<?> entry = cells.get(i);
			ObjectTemplate<Boolean> isEmpty = new ObjectTemplate<Boolean>(true);
			Object keyBean = readObject(keyClass, entry.get(0), keyAccessors, xlsBase, isEmpty);
			if (!isEmpty.object) {
				isEmpty.object = true;
				Object valueBean = readObject(beanClass, entry.get(1), accessors, xlsBase, isEmpty);

				if (!isEmpty.object) {
					beanMap.put(keyBean, valueBean);
				}
			}
		}

		if (empty != null && empty.object) {
			if (beanMap.size() > 0) {
				empty.object = false;
			}
		}

		getAccessor().set(obj, DynaBinder.INSTANCE.binder(beanMap, null, getField().getGenericType()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.absir.appserv.configure.xls.XlsAccessorUtils.XlsAccessorBean#
	 * getHeader()
	 */
	@Override
	public XlsCell getHeader() {
		List<XlsAccessor> valueAccessors = accessors;
		accessors = null;
		XlsCell xlsCell = super.getHeader();
		accessors = valueAccessors;
		List<XlsCell> cells = new ArrayList<XlsCell>();
		if (keyAccessors == null) {
			cells.add(new XlsCellValue("KEY"));

		} else {
			for (XlsAccessor accessor : keyAccessors) {
				cells.add(accessor.getHeader());
			}
		}

		if (accessors == null) {
			cells.add(new XlsCellValue("VALUE"));

		} else {
			for (XlsAccessor accessor : accessors) {
				cells.add(accessor.getHeader());
			}
		}

		xlsCell.addColumnList(cells);
		return xlsCell;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.absir.appserv.configure.xls.XlsAccessor#writeXlsCells(java.util
	 * .List, java.lang.Object, com.absir.appserv.configure.xls.XlsBase)
	 */
	@Override
	public void writeXlsCells(List<XlsCell> xlsCells, Object obj, XlsBase xlsBase) {
		if (obj != null) {
			obj = getAccessor().get(obj);
		}

		XlsCell xlsCell = new XlsCell();
		xlsCells.add(xlsCell);
		if (obj == null) {
			List<XlsCell> cells = xlsCell.addColumnList(null);
			super.writeXlsCells(cells, null, keyAccessors, xlsBase);
			super.writeXlsCells(cells, null, accessors, xlsBase);

		} else {
			for (Entry<?, ?> entry : ((Map<?, ?>) obj).entrySet()) {
				List<XlsCell> cells = xlsCell.addColumnList(null);
				super.writeXlsCells(cells, entry.getKey(), keyAccessors, xlsBase);
				super.writeXlsCells(cells, entry.getValue(), accessors, xlsBase);
			}
		}
	}
}
