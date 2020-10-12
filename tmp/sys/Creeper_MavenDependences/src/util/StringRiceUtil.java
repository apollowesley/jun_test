/**
 * @author:稀饭
 * @time:下午4:59:44
 * @filename:StringUtil.java
 */
package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringRiceUtil {
	/**
	 * 	 * @Title: replaceBlank
	 * @Description: 去掉字符串中所有格式符
	 * @param @param str
	 * @param @return
	 * @return String
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	// 将传过来的字符串修改为符合文件夹以及文件的命名格式
	public static String fixDir(String dirName) {
		dirName = dirName.replace(":", "：");
		dirName = dirName.replace("\\", "");
		dirName = dirName.replace("*", "");
		dirName = dirName.replace("?", "");
		dirName = dirName.replace("'", "");
		dirName = dirName.replace("<", "");
		dirName = dirName.replace(">", "");
		dirName = dirName.replace("|", "");
		return dirName;
	}
}
