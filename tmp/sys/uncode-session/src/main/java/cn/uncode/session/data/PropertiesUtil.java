package cn.uncode.session.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

public class PropertiesUtil extends PropertyPlaceholderConfigurer{
	
	public static String FILE_PREFIX= "file:";
	
	public static final String COMMON_CONFIG_FILE_PATH= "file:/datum/data/conf/config.properties";
	
	public static final String SYSTEM_CONFIG_FILE_PATH_KEY = "systemConfigLocation";
	
	private static final Map<String, String> PROPERTIES_MAP = new HashMap<String, String>();
	
	private static Resource[] locations;
	
	private volatile static boolean systemPropertiesLoaded = false;
	
	public void setLocation(Resource location) {
		super.setLocation(location);
		PropertiesUtil.locations = new Resource[]{location};
	}

	public void setLocations(Resource[] locations) {
		super.setLocations(locations);
		PropertiesUtil.locations = locations;
	}
	
	@Override
    protected String convertProperty(String propertyName, String propertyValue) {

//        if (encryptedProps.contains(propertyName)) { //如果在加密属性名单中发现该属性
//            final Matcher matcher = encryptedPattern.matcher(propertyValue);  //判断该属性是否已经加密
//            if (matcher.matches()) {      //已经加密，进行解密
//                String encryptedString = matcher.group(1);    //获得加密值
//                String decryptedPropValue = AesUtils.decrypt(propertyName + SEC_KEY, encryptedString);  //调用AES进行解密，SEC_KEY与属性名联合做密钥更安全
//
//                if (decryptedPropValue != null) {  //!=null说明正常
//                    propertyValue = decryptedPropValue; //设置解决后的值
//                } else {//说明解密失败
//                    logger.error("Decrypt " + propertyName + "=" + propertyValue + " error!");
//                }
//            }
//        }

        return super.convertProperty(propertyName, propertyValue);  //将处理过的值传给父类继续处理
    }
	
	@Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        super.postProcessBeanFactory(beanFactory);    //正常执行属性文件加载
        for (Resource location : locations) {    //加载完后，遍历location，对properties进行加密
            try {
                final File file = location.getFile();
                if (file.isFile()) {  //如果是一个普通文件
                    if (file.canWrite()) { //如果有写权限
//                        encrypt(file);   //调用文件加密方法
                    } else {
                        if (logger.isWarnEnabled()) {
                            logger.warn("File '" + location + "' can not be write!");
                        }
                    }

                } else {
                    if (logger.isWarnEnabled()) {
                        logger.warn("File '" + location + "' is not a normal file!");
                    }
                }

            } catch (IOException e) {
                if (logger.isWarnEnabled()) {
                    logger.warn("File '" + location + "' is not a normal file!");
                }
            }
        }

    }


	 
    @Override 
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException { 
        super.processProperties(beanFactoryToProcess, props); 
        for (Object key : props.keySet()) { 
            String keyStr = key.toString(); 
            String value = props.getProperty(keyStr); 
            PROPERTIES_MAP.put(keyStr, value);
        }
    }
    
    
    public static void loadPorperties(Resource resource){
    	try {
    		if (null != resource) {
    			Properties prop = new Properties();
    			prop.load(resource.getInputStream());
    			for (Object key : prop.keySet()) {
    				PROPERTIES_MAP.put(key.toString(), String.valueOf(prop.get(key)));
    			}
    		}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private static void loadProperties(){
		if(!systemPropertiesLoaded){
			String filePath = System.getProperty(SYSTEM_CONFIG_FILE_PATH_KEY);
			if(StringUtils.isBlank(filePath)){
				filePath = COMMON_CONFIG_FILE_PATH;
			}
			if(StringUtils.isNotBlank(filePath)){
				Properties properties = new Properties();
				FileInputStream fis = null;
				InputStreamReader isr = null;
				BufferedReader br = null;
				try{
					UrlResource urlResource = new UrlResource(filePath);
					fis = new FileInputStream(urlResource.getFile());
					isr = new InputStreamReader(fis);
					br = new BufferedReader(isr);  
					properties.load(br);
					for (Object key : properties.keySet()) {
						PROPERTIES_MAP.put(key.toString(), String.valueOf(properties.get(key)));
					}
					systemPropertiesLoaded = true;
				}catch(Exception e){
					// TODO: handle exception
				} finally{
					try{
						if(fis!=null){
							fis.close();
						}
						if(isr!=null){
							isr.close();
						}
						if(br!=null){
							br.close();
						}
					}catch(Exception e){
					}
				}
			}
		}
	}
 
    public static String getProperty(String name) {
    	loadProperties();
    	String newName = name.replace("${", "");
    	newName = newName.replace("}", "");
        return PROPERTIES_MAP.get(newName.trim());
    }
    
    public static String getProperty(String name, String defaultValue) {
    	String newName = getProperty(name);
    	if(StringUtils.isNotBlank(newName)){
    		return newName;
    	}
        return defaultValue;
    }
    
    public static int getProperty4Int(String name, int defalutValue){
    	String val = getProperty(name);
    	if(StringUtils.isNotBlank(val)){
    		return Integer.parseInt(val);
    	}
    	return defalutValue;
    }
    
    public static long getProperty4Long(String name, long defalutValue){
    	String val = getProperty(name);
    	if(StringUtils.isNotBlank(val)){
    		return Long.parseLong(val);
    	}
    	return defalutValue;
    }

}
