package com.evil.util;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.evil.pojo.system.AdminUser;
import com.evil.pojo.system.Right;

@SuppressWarnings("rawtypes")
public class ValidateUtil {

	public static boolean isNull(Collection list) {
		if (list == null || list.isEmpty())
			return true;
		return false;
	}

	public static boolean isNull(String str) {
		if (str == null || "".equals(str.trim()))
			return true;
		return false;
	}

	public static boolean isNull(Object[] values) {
		if (values == null || values.length <= 0)
			return true;
		return false;
	}

	public static boolean isQuestionExcel(HSSFWorkbook wb) {
		int sheetNum = wb.getNumberOfSheets();
		HSSFSheet sheet = null;
		if (sheetNum == 3) {
			for (int i = 0; i < sheetNum; i++) {
				sheet = wb.getSheetAt(i);
				int rowNum = sheet.getLastRowNum();
				if ("单选题".equals(sheet.getSheetName())
						|| "多选题".equals(sheet.getSheetName())) {
					if (rowNum > 0) {
						int cellNum = sheet.getRow(0)
								.getPhysicalNumberOfCells();
						if (!(cellNum > -1 && cellNum < 10)) {
							return false;
						}
					} else {
						return false;
					}
				} else if ("判断题".equals(sheet.getSheetName())) {
					if (rowNum > 0) {
						int cellNum = sheet.getRow(0)
								.getPhysicalNumberOfCells();
						if (!(cellNum >= -1 && cellNum < 6)) {
							return false;
						}
					} else {
						return false;
					}
				} else {
					return false;
				}

			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * 根据url地址判断登陆用户是否具备权限
	 * 
	 * @param url
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean hasRight(String url) {
		HttpSession session = Struts2Utils.getSession();
		Map<String, Right> rightMap = (Map<String, Right>) session
				.getServletContext().getAttribute("all_rights_map");
		Right right = rightMap.get(url);// 通过URL获得Right对象
		if (right == null || right.isCommon()) {
			return true;
		} else {
			AdminUser adminUser = (AdminUser) session.getAttribute("adminUser");
			if (adminUser != null) {
				// 判断是否是超级管理员
				if (adminUser.isSuperAdmin()) {
					return true;
				} else {
					// 是否具备权限
					if (adminUser.hasRight(right)) {
						return true;
					} else {
						return false;
					}
				}
			}
			return false;
		}
	}
}
