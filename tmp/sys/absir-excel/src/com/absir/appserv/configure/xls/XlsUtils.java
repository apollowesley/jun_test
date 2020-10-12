/**
 * Copyright 2013 ABSir's Studio
 * 
 * All right reserved
 *
 * Create on 2013-9-24 下午12:05:11
 */
package com.absir.appserv.configure.xls;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.absir.appserv.configure.xls.value.XaSheet;
import com.absir.core.kernel.KernelClass;
import com.absir.core.kernel.KernelString;

/**
 * @author absir
 * 
 */
@SuppressWarnings({ "unchecked" })
// @Resourced
// @Component
public abstract class XlsUtils {

	static {
		// EntityBoost.getBasicTypes().add(new XlsBasicType(XlsBase.class));
	}

	// @Value("${resource.xls.path:xls/}")
	// private static String Resource_Xls_Path;

	/**
	 * @return
	 */
	public static String getXlsPath() {
		// return ResourceServiceUtils.getMvnResoucePath() + Resource_Xls_Path;
		return XlsUtils.class.getClassLoader().getResource("").getFile();
	}

	/**
	 * @return
	 */
	public static <T extends XlsBase> XlsDao<T, Serializable> getXlsDao(Class<T> xlsClass) {
		XlsDao<T, Serializable> xlsDao = XlsAccessorUtils.getXlsDao(xlsClass);
		if (xlsDao == null) {
			synchronized (xlsClass) {
				xlsDao = XlsAccessorUtils.getXlsDao(xlsClass);
				if (xlsDao == null) {
					try {
						reloadXlsDao(xlsClass);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						return null;
					}

					xlsDao = XlsAccessorUtils.getXlsDao(xlsClass);
				}
			}
		}

		return xlsDao;
	}

	/**
	 * @param xlsClass
	 * @return
	 * @throws IOException
	 */
	public static <T extends XlsBase> void reloadXlsDao(Class<T> xlsClass) throws IOException {
		synchronized (xlsClass) {
			XlsBase xlsBase = KernelClass.newInstance(xlsClass);
			XaSheet xaSheet = xlsClass.getAnnotation(XaSheet.class);
			String workbook = xaSheet == null || KernelString.isEmpty(xaSheet.workbook()) ? xlsClass.getSimpleName() : xaSheet.workbook();
			int sheet = xaSheet == null ? 0 : xaSheet.sheet();
			getXlsBeans(xlsBase.getHssfSheet(workbook, sheet), xlsClass, xlsBase);
		}
	}

	/**
	 * @param xlsClass
	 */
	public static <T extends XlsBase> void clearXlsDao(Class<T> xlsClass) {
		XlsAccessorUtils.clearXlsDao(xlsClass);
	}

	/**
	 * @param xlsClass
	 * @param id
	 * @return
	 */
	public static <T extends XlsBase> T getXlsBean(Class<T> xlsClass, Serializable id) {
		return getXlsDao(xlsClass).get(id);
	}

	/**
	 * @param xlsClass
	 * @param id
	 * @return
	 */
	public static <T extends XlsBase> T findXlsBean(Class<T> xlsClass, Object id) {
		return getXlsDao(xlsClass).find(id);
	}

	/**
	 * @param hssfSheet
	 * @return
	 */
	public static <T extends XlsBase> Collection<T> getXlsBeans(Class<T> xlsClass) {
		return getXlsDao(xlsClass).getAll();
	}

	/** XLS_BASE */
	private static final XlsBase XLS_BASE = new XlsBase();

	/**
	 * @param hssfSheet
	 * @param beanClass
	 * @return
	 */
	public static <T extends XlsBase> Collection<T> getXlsBeans(HSSFSheet hssfSheet, Class<T> beanClass) {
		return getXlsBeans(hssfSheet, beanClass, XLS_BASE);
	}

	/**
	 * @param beanClass
	 * @param xlsBase
	 * @return
	 */
	public static <T extends XlsBase> Collection<T> getXlsBeans(HSSFSheet hssfSheet, Class<T> beanClass, XlsBase xlsBase) {
		return getXlsBeans(hssfSheet, beanClass, xlsBase, true);
	}

	/**
	 * @param hssfSheet
	 * @param beanClass
	 * @param xlsBase
	 * @param cacheable
	 * @return
	 */
	public static <T> List<T> getXlsBeans(HSSFSheet hssfSheet, Class<T> beanClass, XlsBase xlsBase, boolean cacheable) {
		int[] accessors = XlsAccessorUtils.accessorsSheet(hssfSheet);
		return XlsAccessorUtils.getXlsBeans(hssfSheet, beanClass, xlsBase, accessors[0], accessors[1], accessors[2], accessors[3], cacheable);
	}

	/**
	 * @param beanClass
	 * @return
	 */
	public static HSSFWorkbook getWorkbook(Class<? extends XlsBase> beanClass) {
		return getWorkbook(null, beanClass, null, KernelClass.newInstance(beanClass));
	}

	/**
	 * @param beans
	 * @return
	 */
	public static HSSFWorkbook getWorkbook(List<? extends XlsBase> beans) {
		return getWorkbook(null, beans);
	}

	/**
	 * @param beanName
	 * @param beans
	 * @return
	 */
	public static <T extends XlsBase> HSSFWorkbook getWorkbook(String beanName, Collection<T> beans) {
		T xlsBase = null;
		for (Object bean : beans) {
			if (bean != null) {
				xlsBase = (T) bean;
				break;
			}
		}

		if (xlsBase == null) {
			return null;
		}

		return getWorkbook(beanName, (Class<T>) xlsBase.getClass(), beans, xlsBase);
	}

	/**
	 * @param beans
	 * @param xlsBase
	 * @return
	 */
	public static HSSFWorkbook getWorkbook(Collection<Object> beans, XlsBase xlsBase) {
		return getWorkbook(null, beans, xlsBase);
	}

	/**
	 * @param beanName
	 * @param beans
	 * @param xlsBase
	 * @return
	 */
	public static <T> HSSFWorkbook getWorkbook(String beanName, Collection<T> beans, XlsBase xlsBase) {
		Class<T> beanClass = null;
		for (Object bean : beans) {
			if (bean != null) {
				beanClass = (Class<T>) bean.getClass();
				break;
			}
		}

		if (beanClass == null) {
			return null;
		}

		return getWorkbook(beanName, beanClass, beans, xlsBase);
	}

	/**
	 * @param beanName
	 * @param beanClass
	 * @param beans
	 * @param xlsBase
	 * @return
	 */
	public static <T> HSSFWorkbook getWorkbook(String beanName, Class<T> beanClass, Collection<T> beans, XlsBase xlsBase) {
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		writeHssfSheet(hssfWorkbook, beanName, beanClass, beans, xlsBase);
		return hssfWorkbook;
	}

	/**
	 * @param workbook
	 * @param beanName
	 * @param beanClass
	 * @param beanList
	 * @param xlsBase
	 */
	public static <T> void writeHssfSheet(HSSFWorkbook workbook, String beanName, Class<T> beanClass, Collection<T> beanList, XlsBase xlsBase) {
		HSSFSheet hssfSheet = workbook.createSheet(beanName == null ? beanClass.getSimpleName() : beanName);
		XlsAccessorUtils.writeHssfSheet(hssfSheet, beanClass, beanList, xlsBase);
	}
}
