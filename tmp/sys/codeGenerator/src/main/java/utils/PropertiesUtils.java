package utils;


import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class  PropertiesUtils{

    public final static String recource="/properties/config.peoperties";
    public static PropertiesUtils propertiesUtils;
    private static Properties properties =new Properties();

    private PropertiesUtils(){}
    public static PropertiesUtils PropertiesUtilsInstance(){
        if(propertiesUtils == null){
            propertiesUtils =new PropertiesUtils();
            //propertiesUtils.get(path);
        }
        return  propertiesUtils;
    }

    public static void getPropertiesReader(){
        try{
           // Properties properties =new Properties();
            File directory = new File("");//设定为当前文件夹
            File dir = new File(directory.getCanonicalPath()+recource);
            properties.load(new FileInputStream(dir.getPath()));
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public static String getConf(String key){
        return properties.getProperty(key);
    }
}