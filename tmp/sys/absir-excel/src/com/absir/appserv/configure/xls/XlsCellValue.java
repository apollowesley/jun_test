/**
 * Copyright 2013 ABSir's Studio
 * 
 * All right reserved
 *
 * Create on 2013-9-25 下午3:39:39
 */
package com.absir.appserv.configure.xls;

import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * @author absir
 * 
 */
public class XlsCellValue extends XlsCellBase {

	/** value */
	private String value;

	/**
	 * @param value
	 */
	public XlsCellValue(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.absir.appserv.configure.xls.XlsCell#wirteHssfCell(org.apache.poi.
	 * hssf.usermodel.HSSFCell)
	 */
	@Override
	public void wirteHssfCell(HSSFCell hssfCell) {
		// TODO Auto-generated method stub
		hssfCell.setCellValue(value);
	}
}
