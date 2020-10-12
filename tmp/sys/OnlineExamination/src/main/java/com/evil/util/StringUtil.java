package com.evil.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import com.evil.pojo.BaseEntity;

/**
 * ���ַ������д���
 */
public class StringUtil {

	/**
	 * ���ַ�������tag���Ϊ�ַ�����
	 */
	public static String[] strSplit(String str, String tag) {
		if (!ValidateUtil.isNull(str)) {
			return str.split(tag);
		}
		return null;
	}

	/**
	 * ���ַ�������","���ӷ���
	 */
	public static String arr2Str(Object[] values) {
		String temp = "";
		if (!ValidateUtil.isNull(values)) {
			for (Object s : values) {
				temp = temp + s + ",";
			}
			return temp.substring(0, temp.length() - 1);
		}
		return temp;
	}
	/**
	 * ��������","���ӷ��� ÿ���ַ�����''sql����
	 */
	public static String arr2SqlStr(Object[] values) {
		String temp = "";
		if (!ValidateUtil.isNull(values)) {
			for (Object s : values) {
				temp = temp + "'"+s +"'"+ ",";
			}
			return temp.substring(0, temp.length() - 1);
		}else{
			temp="''";
		}
		return temp;
	}

	public static String getDescString(String meg) {
		if (meg != null && meg.length() > 30) {
			return meg.substring(0, 30);
		}
		return meg;
	}

	public static String arr2Str(int[] values) {
		String temp = "";
		if (values != null && values.length > 0) {
			for (Object s : values) {
				temp = temp + s + ",";
			}
			return temp.substring(0, temp.length() - 1);
		}
		return temp;
	}

	/**
	 * �����Ͳ�������ַ���
	 * 
	 * @param objects
	 * @return
	 */
	public static String ObjectArrayToString(Object... objects) {
		StringBuilder str = new StringBuilder();
		if (!ValidateUtil.isNull(objects)) {
			for (Object oo : objects) {
				if (oo instanceof Object[]) {
					if (ValidateUtil.isNull((Object[]) oo)) {
						continue;
					}
					str.append("[");
					for (Object o : (Object[]) oo) {
						str.append(o + ",");
					}
					str.deleteCharAt(str.length() - 1);
					str.append("],");
					continue;
				}
				str.append(oo + ",");
			}
			str.deleteCharAt(str.length() - 1);
		}
		return str.toString();
	}

	/**
	 * �����ַ�������ʱ��
	 * 
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static Date constructionTime(String startTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateNowStr = sdf.format(new Date());
		startTime = dateNowStr + " " + startTime;
		sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date d = null;
		try {
			d = sdf.parse(startTime);
		} catch (ParseException e) {
			e.printStackTrace();
			d = null;
			return d;
		}
		return d;
	}
	
	/**
	 *��ȡʵ���б��id���ӳ��ַ���
	 * @param entitys
	 * @return
	 */
	public static String extractEntityIds(Set<? extends BaseEntity> entitys) {
		String temp = "";
		if (!ValidateUtil.isNull(entitys)) {
			for (BaseEntity r : entitys) {
				temp = temp +"'"+r.getId() + "',";
			}
			return temp.substring(0,temp.length()-1);
		}
		return temp;
	}
	
	/**s
	 * ���� namespace��actionName ���Ȩ�޵�ַ
	 */
	public static String getUrl(String ns, String actionName) {
		if ("/".equals(ns) || ValidateUtil.isNull(ns)) {
			ns = "";
		}
		// ���������ȥ������
		if (actionName.contains("?")) {
			actionName = actionName.substring(0, actionName.indexOf("?"));
		}
		String url = ns + "/" + actionName;
		return url;
	}

}
