package nivalsoul.kettle.plugins.util;

import nivalsoul.kettle.plugins.common.PluginType;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;

public class PluginUtil {
	
	public static String getDefaultConfig(String pluginType) {
		PluginType type = PluginType.parse(pluginType);
		String config = "{}";
		try {
			InputStream is = PluginUtil.class.getClassLoader().getResourceAsStream(type+".json");
			config = IOUtils.toString(is);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return config;
	}
	
	public static String getClassName(String pluginType) {
		return PluginType.parse(pluginType).getClassName();
	}
	
	public static void main(String[] args) {
		System.out.println(getDefaultConfig("输出到dbf文件"));
	}

}
