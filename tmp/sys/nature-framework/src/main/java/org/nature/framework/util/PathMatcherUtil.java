package org.nature.framework.util;

public class PathMatcherUtil {
	private PathMatcherUtil(){}
	public static boolean matches(String pattern, String source) {
		if (pattern == null || source == null) {
			return false;
		}
		pattern = pattern.trim();
		source = source.trim();
		if (pattern.endsWith("*")) {
			// pattern: /druid* source:/druid/index.html
			int length = pattern.length() - 1;
			if (source.length() >= length) {
				if (pattern.substring(0, length).equals(source.substring(0, length))) {
					return true;
				}
			}
		} else if (pattern.startsWith("*")) {
			// pattern: *.html source:/xx/xx.html
			int length = pattern.length() - 1;
			if (source.length() >= length&& source.endsWith(pattern.substring(1))) {
				return true;
			}
		} else if (pattern.contains("*")) {
			// pattern:  /druid/*/index.html source:/druid/admin/index.html
			int start = pattern.indexOf("*");
			int end = pattern.lastIndexOf("*");
			if (source.startsWith(pattern.substring(0, start))&& source.endsWith(pattern.substring(end + 1))) {
				return true;
			}
		} else {
			// pattern: /druid/index.html source:/druid/index.html
			if (pattern.equals(source)) {
				return true;
			}
		}
		return false;
	}
}
