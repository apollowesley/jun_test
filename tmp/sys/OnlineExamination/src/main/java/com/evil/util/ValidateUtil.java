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
				if ("��ѡ��".equals(sheet.getSheetName())
						|| "��ѡ��".equals(sheet.getSheetName())) {
					if (rowNum > 0) {
						int cellNum = sheet.getRow(0)
								.getPhysicalNumberOfCells();
						if (!(cellNum > -1 && cellNum < 10)) {
							return false;
						}
					} else {
						return false;
					}
				} else if ("�ж���".equals(sheet.getSheetName())) {
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
	 * ����url��ַ�жϵ�½�û��Ƿ�߱�Ȩ��
	 * 
	 * @param url
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean hasRight(String url) {
		HttpSession session = Struts2Utils.getSession();
		Map<String, Right> rightMap = (Map<String, Right>) session
				.getServletContext().getAttribute("all_rights_map");
		Right right = rightMap.get(url);// ͨ��URL���Right����
		if (right == null || right.isCommon()) {
			return true;
		} else {
			AdminUser adminUser = (AdminUser) session.getAttribute("adminUser");
			if (adminUser != null) {
				// �ж��Ƿ��ǳ�������Ա
				if (adminUser.isSuperAdmin()) {
					return true;
				} else {
					// �Ƿ�߱�Ȩ��
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
