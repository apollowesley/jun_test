package x.javaplus.util.templet;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import x.javaplus.collections.Lists;
import x.javaplus.mysql.generator.gen.Dto;
import x.javaplus.util.Resources;
import x.javaplus.util.Util;


public class Config {
	private static Map<String, String> templets;

	static {
		
		try {
			resolveTemplets();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private static void resolveTemplets() {
		templets = new HashMap<String, String>();
		URL url = Resources.getUrl("templets");
		String content = Util.File.getContent(url);
		String[] split = content.split("\\-{3,100}");

		for (String group : split) {

			put(group);
		}
	}

	private static void put(String group) {

		String name = getName(group);

		group = group.replaceAll("TempletName:.*", "");

		group = group.replaceAll("\r{3,50}", "\r");
		
		templets.put(name, group);
	}

	private static String getName(String group) {
		Pattern p = Pattern.compile("TempletName:.*");
		Matcher m = p.matcher(group);
		if (!m.find()) {
			throw new RuntimeException( "模板名字没有定义! 请用:    TempletName:.* 来定义模板的名字:\r" + group );
		}
		String bound = m.group();
		return bound.replaceAll("TempletName:", "").trim();
	}

	/**
	 * @param templetName
	 *            模板名字
	 * @return
	 */
	public static Templet getTemplet(String templetName) {
		String templetText = templets.get(templetName);
		if (templetText == null) {
			throw new RuntimeException("没有找到对应的模板:" + templetName);
		}
		return Templet.createByContent(templetText);
	}

	/**
	 * 取得所有的Dto对象
	 * @param dtoPath 
	 * 
	 * @return
	 */
	public static List<Dto> getDtos(String dtoPath) {
		List<File> all = Util.File.getFiles(dtoPath);

		List<Dto> allDto = Lists.newArrayList();
		for (File file : all)
			allDto.add(new Dto(file));
		return allDto;
	}
	
}
