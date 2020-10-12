/**
 * Copyright 2013 ABSir's Studio
 * 
 * All right reserved
 *
 * Create on 2013-9-27 下午4:22:42
 */
package com.absir.appserv.configure.xls;

import java.io.Serializable;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;

import com.absir.core.kernel.KernelClass;
import com.absir.core.kernel.KernelLang.ObjectTemplate;

/**
 * @author absir
 * 
 */
@SuppressWarnings("unchecked")
public class XlsAccessorContext extends XlsAccessorBean {

	private Class<? extends Serializable> idType;

	/**
	 * @param beanClass
	 * @param xlsBase
	 */
	public XlsAccessorContext(Class<?> beanClass, XlsBase xlsBase) {
		super(null, beanClass);
		// TODO Auto-generated constructor stub
		this.beanClass = beanClass;
		accessors = getXlsAccessors(beanClass, xlsBase);
		if (XlsBean.class.isAssignableFrom(beanClass)) {
			idType = KernelClass.componentClass(beanClass);

		} else {
			accessors.remove(0);
			idType = Integer.class;
		}
	}

	/**
	 * @return the idType
	 */
	public Class<? extends Serializable> getIdType() {
		return idType;
	}

	/**
	 * @param cell
	 * @param xlsBase
	 * @return
	 */
	public Object newInstance(Object cell, XlsBase xlsBase, int index) {
		Object obj = KernelClass.newInstance(beanClass);
		if (obj instanceof XlsBean) {
			if (accessors != null && accessors.size() > 0) {
				Object id = xlsBase.read(((List<HSSFCell>) cell).get(0), idType);
				accessors.get(0).getAccessor().set(obj, id);
			}

		} else {
			((XlsBase) obj).id = index;
		}

		return obj;
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
		if (accessors != null) {
			List<?> cells = (List<?>) cell;
			int size = accessors.size();
			for (int i = 0; i < size; i++) {
				accessors.get(i).setObject(obj, cells.get(i), xlsBase, empty);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.absir.appserv.configure.xls.XlsAccessorUtils.XlsAccessorBean#
	 * getHeader()
	 */
	@Override
	public XlsCell getHeader() {
		XlsCell xlsCell = super.getHeader();
		if (xlsCell.getChildren() != null && xlsCell.getChildren().size() > 0) {
			List<XlsCell> cells = xlsCell.getChildren().get(0);
			int rowCount = 0;
			int rowOffset = 0;
			for (XlsCell cell : cells) {
				int iRowCount = cell.getRowCount();
				if (rowCount < iRowCount) {
					rowCount = iRowCount;
					rowOffset++;
				}
			}

			if (rowOffset > 1) {
				int size = cells.size();
				for (int i = 0; i < size; i++) {
					XlsCell cell = cells.get(i);
					rowOffset = rowCount - cell.getRowCount();
					if (rowOffset > 0) {
						XlsCellMerged cellMerged = new XlsCellMerged(cell);
						cellMerged.setBasicRow(cellMerged.getBasicRow() + rowOffset);
						cells.set(i, cellMerged);
					}
				}
			}
		}

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
		writeXlsCells(xlsCells, obj, accessors, xlsBase);
	}
}
