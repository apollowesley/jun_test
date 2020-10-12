package priv.mdc.index.dumper.util;

import java.io.IOException;
import java.util.Properties;

/**
 * 读配置文件
 * @author xuhuahai
 *
 */
public class ConfigInfo {

	private static Properties config_prop = new Properties();
	
    static { 
        try {
        	Properties prop = new Properties();
            prop.load(ConfigInfo.class.getClassLoader().getResourceAsStream( 
                    "conf.properties")); 
            config_prop.putAll(prop);
        } catch (IOException e) { 
            //
        }
        try {
        	Properties prop = new Properties();
            prop.load(ConfigInfo.class.getClassLoader().getResourceAsStream( 
                    "profile.properties")); 
            config_prop.putAll(prop);
        } catch (IOException e) { 
            //
        }
    } 
	
    public static String getString(String key){
    	return config_prop.getProperty(key);
    }
    
    public static String getString(String key, String defaultValue){
    	return config_prop.getProperty(key, defaultValue);
    }
    
    public static int getInt(String key){
    	return Integer.parseInt(getString(key,"0"));
    }
    
}
