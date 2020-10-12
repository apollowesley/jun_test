package cn.uncode.dal.generator.support;


public class StringUtil {

	/**
	 * 首字母大写
	 * 
	 * @param src
	 * @return
	 */
	public static String upperFirestChar(String src) {
		// 如果全大写就转换成全小写
		if (isAcronym(src)) {
			src = src.toLowerCase();
		}
		StringBuffer strBuf = new StringBuffer("");
		if (src.contains("_")) {
			// 处理末尾带下划线
			String append2016 = "";
			if (src.endsWith("_")) {
				append2016 = "_";
			}
			String[] ss = src.split("_");
			if (ss != null) {
				for (int i = 0; i < ss.length; i++) {
					String s = ss[i];
					if (s != null) {
						if (s.length() > 1) {
							strBuf.append(s.substring(0, 1).toUpperCase().concat(s.substring(1)));
						} else {
							if (i < ss.length - 1) {
								i++;
								s = s + ss[i];
							}
							strBuf.append(s.substring(0, 1).toUpperCase().concat(s.substring(1)));
						}
					}
				}
			}
			// append2016
			strBuf.append(append2016);
		} else {
			strBuf.append(src.substring(0, 1).toUpperCase().concat(src.substring(1)));
		}
		return strBuf.toString();
	}

	/**
	 * 首字母转小写
	 * 
	 * @param src
	 * @return
	 */
	public static String lowerFirestChar(String src) {
		return src.substring(0, 1).toLowerCase().concat(src.substring(1));

	}

	/**
	 * 判断是否全大写
	 * 
	 * @param word
	 * @return
	 */
	public static boolean isAcronym(String word) {
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (Character.isLowerCase(c)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param field
	 * @param type
	 * @param remarks
	 * @return
	 */
	private String getFieldStr(String srcField, String field, String type, String remarks) {
		StringBuilder sb = new StringBuilder();
		sb.append("\t/**\n");
		sb.append("\t * ").append(remarks).append("\n");
		sb.append("\t */\n");
		sb.append("\t").append("private ").append(type).append(" ").append(field).append(";");
		sb.append("\n");
		return sb.toString();
	}
	/**
	 * 
	 * @param field
	 * @return
	 */
	private String getStaticStr(String srcField) {
		String staticStr = srcField;
		staticStr = staticStr.toUpperCase();
		StringBuilder sb = new StringBuilder();
		sb.append("\t").append("public final static String ").append(staticStr).append(" = \"").append(srcField).append("\";").append("\n");
		return sb.toString();
	}

	public static void main(String[] args) {
		StringUtil bu = new StringUtil();
		String src = "language_";
		String aaa = bu.upperFirestChar(src);
		System.out.println(aaa);
	}
}
