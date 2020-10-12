package org.nature4j.framework.helper;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nature4j.framework.util.CastUtil;
import org.nature4j.framework.util.CodecUtil;
import org.nature4j.framework.util.PropsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class I18NHelper {
	private static Logger LOGGER = LoggerFactory.getLogger(I18NHelper.class);
	private static Map<String, String> map = new HashMap<String, String>();

	public static void initI18nMap() {
		if (ConfigHelper.getI18n()) {

			try {
				Enumeration<URL> urls = I18NHelper.class.getClassLoader().getResources("");
				while (urls.hasMoreElements()) {
					URL url = urls.nextElement();
					if ("file".equals(url.getProtocol())) {
						String path = url.getPath().replace("%20", " ");
						File file = new File(path);
						String[] files = file.list(new FilenameFilter() {
							public boolean accept(File dir, String name) {
								return name.matches("(i18n|I18N)_\\w+_\\w+\\.properties");
							}
						});
						for (String f : files) {
							String prefix = getPrefix(f);
							Properties loadProps = PropsUtil.loadProps(f);
							Enumeration<Object> keys = loadProps.keys();
							while (keys.hasMoreElements()) {
								Object key = keys.nextElement();
								String valueString = CastUtil.castString(loadProps.get(key));
								String valueFinal = CodecUtil.ISO2UTF8(valueString);
								map.put(prefix + key, valueFinal);
							}
						}
					}
				}

			} catch (IOException e) {
				LOGGER.error("initI18nMap error ");
				throw new RuntimeException(e);
			}

		}
	}

	private static String getPrefix(String f) {
		String prefix = "";
		Pattern pattern = Pattern.compile("[i18n|I18N]_(\\w+)_(\\w+)\\.properties");
		Matcher matcher = pattern.matcher(f);
		while (matcher.find()) {
			prefix = matcher.group(1) + "_" + matcher.group(2) + "_";
		}
		return prefix;
	}

	public static String getValue(String key) {
		return map.get(key);
	}
	
	public static void clear(){
		map.clear();
	}

}
