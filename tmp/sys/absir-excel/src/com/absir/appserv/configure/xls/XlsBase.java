/**
 * Copyright 2013 ABSir's Studio
 * 
 * All right reserved
 *
 * Create on 2013-9-24 下午6:21:25
 */
package com.absir.appserv.configure.xls;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.absir.appserv.dyna.DynaBinderUtils;
import com.absir.appserv.system.bean.proxy.JiBase;
import com.absir.appserv.system.bean.value.JaLang;
import com.absir.core.helper.HelperFile;
import com.absir.core.kernel.KernelObject;

/**
 * @author absir
 * 
 */
@SuppressWarnings({ "unchecked" })
public class XlsBase implements JiBase {

	/** id */
	@JaLang("编号")
	protected Serializable id;

	/**
	 * 初始化
	 */
	protected void initializing() {
	}

	/**
	 * @return
	 */
	public Serializable getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return KernelObject.hashCode(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object equal) {
		if (equal == null) {
			return false;
		}

		if (getClass().isAssignableFrom(equal.getClass())) {
			return KernelObject.equals(((XlsBean<?>) equal).getId(), id);

		} else {
			return equal.equals(id);
		}
	}

	/**
	 * @param cls
	 * @return
	 */
	protected boolean is(Class<?> cls) {
		return DynaBinderUtils.is(cls) || XlsBase.class.isAssignableFrom(cls);
	}

	/**
	 * @return
	 * @throws IOException
	 */
	protected HSSFSheet getHssfSheet(String workbook, int sheet) throws IOException {
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(HelperFile.openInputStream(new File(XlsUtils.getXlsPath() + workbook + ".xls")));
		return hssfWorkbook.getSheetAt(sheet);
	}

	/**
	 * @param obj
	 * @param toClass
	 * @return
	 */
	protected <T> T read(HSSFCell hssfCell, Class<T> toClass) {
		String value = XlsAccessorUtils.getCellValue(hssfCell);
		if (XlsBase.class.isAssignableFrom(toClass)) {
			return (T) XlsUtils.findXlsBean((Class<? extends XlsBase>) toClass, value);
		}

		return DynaBinderUtils.to(value, toClass);
	}

	/**
	 * @param hssfCell
	 * @param obj
	 */
	protected void write(HSSFCell hssfCell, Object obj) {
		if (obj == null) {
			return;
		}

		if (obj instanceof XlsBase) {
			obj = ((XlsBase) obj).getId();
		}

		hssfCell.setCellValue(DynaBinderUtils.to(obj, String.class));
	}
}
