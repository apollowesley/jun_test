package com.qxzl.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 阿里fastJson解析json通用方法
 */
public class FAST {
    /**
     * 解析JsonObject数据
     *
     * @param jsonString
     *            Json格式字符串
     * @param cls
     *            封装类
     *
     */
    public static <T> T parseObject(String jsonString, Class<T> cls) {
        T t = null;
        try {
            t = JSON.parseObject(jsonString, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 解析JsonArray数据
     *
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> List<T> parseArray(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            list = JSON.parseArray(jsonString, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 解析JsonArray数据，返回Map类型的List
     *
     * @param jsonString
     * @return
     */
    public static List<Map<String, Object>> parseObjectListKeyMaps(
            String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = JSON.parseObject(jsonString,
                    new TypeReference<List<Map<String, Object>>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 存储文件
     * @param file  地址
     * @param json  json字符
     * @throws FileNotFoundException
     */
    public static void writeDataToFile(File file,String json) throws FileNotFoundException {

        PrintWriter out = new PrintWriter(new FileOutputStream(file, false));
        out.println(json.toString());
        out.close();
    }


    /**
     *  写入文件
     * @param pathName 文件地址   xxxx/xx.json
     * @param jsonstr  json字符串
     * @return
     */
    public static boolean writeJson(String pathName,String jsonstr) {

        File file=new File(pathName);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FAST.writeDataToFile(file,jsonstr);
            return  true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  false;

    }


    /**
     * 读取文件
     * @param fileUrl
     * @return
     */
    public static String jsonData(String fileUrl){//filename assets目录下的json文件名
        String  jsonDate=null;
        try {
            InputStreamReader inputStreamReader=new InputStreamReader(new FileInputStream(fileUrl),"UTF-8");
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String line;
            StringBuilder stringBuilder=new StringBuilder();
            while ((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();

            jsonDate=stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonDate;
    }

}
