package cc.xulu.utils;

public class StringUtil {

	public static String toUpperCaseTheFristChar(String tableName) {
		return tableName.substring(0, 1).toUpperCase()
				+ replace(tableName.substring(1));
	}

	private static String replace(String s) {
		if (s.contains("_")) {
			s = s.substring(0, s.indexOf("_"))
					+ s.substring(s.indexOf("_") + 1, s.indexOf("_") + 2)
							.toUpperCase() + s.substring(s.indexOf("_") + 2);
			return replace(s);
		} else {
			return s;
		}
	}

	public static void main(String[] args) {
		String s = "abc_def_ghi";

		System.out.println(toUpperCaseTheFristChar(s));
	}

	public static String toLowerCaseTheFristChar(String tableName) {

		return tableName.toLowerCase();
	}

}
