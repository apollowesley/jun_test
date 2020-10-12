package com.luoqy.speedy.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.map.HashedMap;

import com.luoqy.speedy.data.DataBase;
import com.luoqy.speedy.data.MySqldbUtil;

/**
 * @author luoqy
 * @date 2019年6月2日 配置文件操作工具
 */
public class ConfigManage {
    /**
     * 本地动态修改配置文件数据,
     */
    public static void updateProp(String fileName, Map<String, String> map) {
        Properties props = new Properties();
        InputStream in=null;
        String path="";
        if(fileName.contains("base")){
            in = MySqldbUtil.class.getResourceAsStream("/" + fileName+".properties");
            path=MySqldbUtil.class.getResource("/" + fileName+".properties").getFile();
        }else{
            in = MySqldbUtil.class.getResourceAsStream("/properties/" + fileName+".properties");
            path=MySqldbUtil.class.getResource("/properties/" + fileName+".properties").getFile();
        }
        try {
            props.load(in);
            Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, String> entry = entries.next();
                props.setProperty(entry.getKey(), entry.getValue());
            }
            FileWriter fw = new FileWriter(path);
            props.store(fw, "");
            //props.list(System.out);
            fw.close();
            in.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	/**
	 * 查询开发环境下的配置文件，
	 */
	public static Map<String, String> findProp(String propName) {
		Map<String, String> map = new HashMap<>();
		try {
			Properties prop = new Properties();
			String fileProp = "/"+propName + ".properties";
			if (!"base".equals(propName)) {
				fileProp = "/properties/" + propName + ".properties";
			}
			prop.load(ConfigManage.class.getResourceAsStream(fileProp));
			Enumeration fileName = prop.propertyNames();
			while (fileName.hasMoreElements()) {
				String strKey = (String) fileName.nextElement();
				String strValue = prop.getProperty(strKey);
				map.put(strKey, strValue);
			}
			return map;
		} catch (Exception e) {
			return null;
		}
	}

    /**
     * @param strPath
     * @return 所有配置文件名 可能无用？？？
     */
    public static List<String> getFileList(String strPath) {
        if (null == strPath) {
            strPath = MySqldbUtil.class.getResource("/properties").getPath();
        }
        File dir = new File(MySqldbUtil.class.getResource("/properties").getPath());
        List<String> filelist = new ArrayList<String>();
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                    getFileList(files[i].getAbsolutePath()); // 获取文件绝对路径
                } else {
                    // 文件绝对路径
                    String strFileName = files[i].getAbsolutePath();
                    // files[i]
                    filelist.add(fileName);
                }
            }
        }
        return filelist;
    }

    /**
     * 查询是否第一次登录
     *
     * @return 返回是否正则 布尔类型
     */
    public static boolean findFirst() {
        boolean flag = false;
        try {
            if(!FirstCreateBase.whetherJar()){
                Map<String,String> base= findProp("base");
                if("true".equals(base.get("first"))){
                    return true;
                }else{
                    return false;
                }
            }

            File filePath = new File("");
            String indexPath = filePath.getAbsolutePath();
            String path = indexPath + "/BOOT-INF/classes";
            File file = new File(path);
            Map<String,String> base= new HashedMap();
            base.put("first","true");
            base.put("localhost","true");
            if ( !file.exists()) {
                return true;
            }else{
                path+="/base.properties";
                file = new File(path);
            }
            // 2.通过file对象构建一个流对象
            @SuppressWarnings("resource")
            FileInputStream fileInput = new FileInputStream(file);
            // 3.设置字节数组，1024只是一个习惯
            byte[] data = new byte[1024];
            StringBuffer stb = new StringBuffer();
            int leng;// 用来存储read(byte[]
            // b)方法的返回值，代表每次读入的字节个数；当因为到达文件末尾而没有字节读入时，返回-1
            while ((leng = fileInput.read(data)) != -1) {
                stb.append(new String(data, 0, leng));
            }
            String string = new String(stb.toString().getBytes(), "GBK");
            String[] strs = string.split("\n|\r");
            for (String string2 : strs) {
                if ("true".equals(string2.replace("first=", ""))) {
                    flag = true;
                    base.put("first","false");
                    update("base",base);
                    break;
                } else {
                    flag = false;
                }
            }
            return flag;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return flag;
        }

    }

    /**
     * 查询文件方法组件
     */
    public static Map<String, String> find(String fileName) {
        Map<String, String> configMap = new HashMap<String, String>();
        String path = null;
        try {
            if(!FirstCreateBase.whetherJar()){
               return findProp(fileName);
            }
            File filePath = new File("");
            String indexPath = filePath.getAbsolutePath();
            if (fileName.contains("base")) {
                path = indexPath + "/BOOT-INF/classes/" + fileName + ".properties";
            } else {
                path = indexPath + "/BOOT-INF/classes/properties/" + fileName + ".properties";
            }
            File file = new File(path);
            if (!file.exists() && "base".equals(fileName)) {
                configMap.put("first", "true");
                configMap.put("localhost", "true");
                return configMap;
            }
            // 2.通过file对象构建一个流对象
            @SuppressWarnings("resource")
            FileInputStream fileInput = new FileInputStream(file);
            // 3.设置字节数组，1024只是一个习惯
            byte[] data = new byte[1024];
            StringBuffer stb = new StringBuffer();
            int leng;// 用来存储read(byte[]
            // b)方法的返回值，代表每次读入的字节个数；当因为到达文件末尾而没有字节读入时，返回-1
            while ((leng = fileInput.read(data)) != -1) {
                stb.append(new String(data, 0, leng));
            }
            String string = new String(stb.toString().getBytes(), "GBK");
            String[] strs = string.split("\n|\r");
            for (String string2 : strs) {
                string2.replaceFirst("=", "luoqy93");
                if (string2.contains("#")) {
                    continue;
                } else {
                    if (string2.contains("luoqy93")) {
                        configMap.put(string2.split("luoqy93")[0], string2.split("luoqy93")[1]);
                    }
                }
            }
            return configMap;
        } catch (Exception e) {
            // TODO: handle exception
            return configMap;
        }
    }

    /**
     * @param fileName 文件名(不包含后缀)
     * @return 根据配置文件名查找文件 返回Map
     */
    public static Map<String, String> findProperties(String fileName) {
        try {
            Map<String, String> dataConfig = new HashMap<String, String>();
            if (findFirst()) {
                return find(fileName);
            } else {
                dataConfig = findDataProperties(fileName);
                if (null == dataConfig) {
                    return find(fileName);
                } else {
                    return dataConfig;
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }

    }

    /**
     * 更新文件组件统一
     */
    private static boolean update(String fileName, Map<String, String> map) {
        String path = "";
        try {
            File filePath = new File("");
            String indexPath = filePath.getAbsolutePath();
            if (fileName.contains("base")) {
                path = indexPath + "/BOOT-INF/classes/" + fileName + ".properties";
            } else {
                path = indexPath + "/BOOT-INF/classes/properties/" + fileName + ".properties";
            }
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
                //file.mkdirs();
            }
            // props.load(in);
            Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();
            String text = "";
            while (entries.hasNext()) {
                Map.Entry<String, String> entry = entries.next();
                text += entry.getKey() + "=" + entry.getValue() + "\n";
            }
            FileWriter fw = new FileWriter(path);
            fw.write(text);
            fw.flush();
            fw.close();
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }

    }

    /**
     * @param fileName 文件名
     * @param map      参数Map
     * @return
     */
    public static boolean updatePropertie(String fileName, Map<String, String> map) {
        try {
            Map<String, String> dataConfig = new HashMap<String, String>();
            if (findFirst()) {
                return update(fileName, map);
            } else {
                dataConfig = findDataProperties(fileName);
                if (null == dataConfig) {
                    return update(fileName, map);
                } else {
                    updateDataPropertie(fileName, map, null);
                    return true;
                }
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    /**
     * String Map 转换Map
     */
    public static Map<String, String> json2map(String str_json) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            String[] strs = str_json.replace("{", "").replace("}", "").split(",");
            for (String string : strs) {
                String str = string.replaceFirst("=", "luoqy@");
                String[] strArray = str.split("luoqy@");
                map.put(strArray[0].trim(), strArray[1].trim());
            }
            return map;
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println("转换异常");
            return map;
        }

    }

    /**
     * @param fileName 文件名
     * @return 对数据库进行操作
     */
    private static Map<String, String> findDataProperties(String fileName) {
        try {
            fileName = fileName + ".properties";
            Object config = MySqldbUtil.mysqlSelect("select * from speedy_config where filename='" + fileName + "'",
                    "none", null);
            Map<String, String> configMap = (Map<String, String>) config;
            String data = configMap.get("data");
            Map<String, String> map = json2map(data);
            return map;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 动态修改配置文件数据, 数据库操作
     */
    private static boolean updateDataPropertie(String fileName, Map<String, String> map, DataBase dataBase) {
        try {
            fileName = fileName + ".properties";
            String dataStr = map.toString();
            String sql = " set data='" + dataStr + "',filename='" + fileName + "'";
            Object data = MySqldbUtil.mysqlSelect("select * from speedy_config where filename='" + fileName + "'",
                    "none", dataBase);
            if (null != data) {
                MySqldbUtil.mysqlExecute("update speedy_config" + sql + " where filename='" + fileName + "'", dataBase);
            } else {
                MySqldbUtil.mysqlExecute("insert into speedy_config" + sql, dataBase);
            }
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println("数据源信息出现异常");
            return false;
        }
    }
}
