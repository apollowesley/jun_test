package com.staticClass;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapToJson {
	/**
	 * hibernate返回值为List类型，但是数据为Map类型
	 * queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
	 * 
	 * 这里接收List，返回Json
	 * 
	 * @param List
	 * @return String Json格式
	 */
	public static String parseJson(List list) {
		String result = "{\"type\":[";
		int y = list.size();
		if (y > 0) {
			Map map = (Map) list.get(0);
			Set mapkey = map.keySet();
			Object[] mapkeyA = mapkey.toArray();
			for (int i = 0; i < y; i++) {
				map = (Map) list.get(i);
				int x = mapkeyA.length;
				result += "{";
				for (int j = 0; j < x; j++) {
					result += "\"" + mapkeyA[j] + "\":\"";
					if (map.get(mapkeyA[j]) != null) {
						result += map.get(mapkeyA[j]).toString().replace("\"", "\\\"") + "\",";
					} else {
						result += map.get(mapkeyA[j]) + "\",";
					}
				}
				result = result.substring(0, result.length() - 1);
				result += "},";

			}
			result = result.substring(0, result.length() - 1);
		}
		result += "]}";
		result = parseHtml(result);
		return result;
	}

	public static String parseJsonAndroid(List list) {
		String result = "{\"type\":[";
		int y = list.size();
		if (y > 0) {
			Map map = (Map) list.get(0);

			Set mapkey = map.keySet();
			Object[] mapkeyA = mapkey.toArray();
			for (int i = 0; i < y; i++) {
				map = (Map) list.get(i);
				int x = mapkeyA.length;
				result += "{";
				for (int j = 0; j < x; j++) {
					result += "\"" + mapkeyA[j] + "\":\"" + Const.filterHTML(Const.StringToString(map.get(mapkeyA[j]))) + "\",";
				}
				result = result.substring(0, result.length() - 1);
				result += "},";

			}
			result = result.substring(0, result.length() - 1);
		}
		result += "]}";
		return result;
	}

	/**
	 * 替换换行字符为html标签
	 * 
	 * @param html
	 * @return
	 * @createTime 2014-11-23 上午9:31:01
	 */
	public static String parseHtml(String html) {
		if (html == null) {
			return html;
		}
		html = html.replace("\r", "").replace("\\r", "").replace("\n", "<br/>").replace("\\n", "<br/>");// 把换行字符替换成HTML页面的换行标签

		String check = "((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?";
		Pattern p = Pattern.compile(check);
		String g = "";
		String candidate = html;
		while (true) {
			Matcher matcher = p.matcher(candidate);
			if (matcher.find()) {
				g = matcher.group(0);
				html = html.replace(g, "<a href=\\\\\"" + g + "\\\\\">网页链接</a>");
			} else {
				break;
			}
			candidate = candidate.replace(g, "");
		}
		return html;
	}
}
