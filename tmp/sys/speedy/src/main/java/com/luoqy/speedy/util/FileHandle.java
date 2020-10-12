package com.luoqy.speedy.util;

import com.luoqy.speedy.config.WebConfig;
import com.luoqy.speedy.data.MySqldbUtil;

import java.io.File;
import java.io.InputStream;
import java.util.*;
/**
 *  对文件进行操作得工具类
 * */
public class FileHandle {
    public static List<Map<String, String>> listFile(String path) {
        if (null == path || "".equals(path)) {
            Properties props = new Properties();
            InputStream in = null;
            in = WebConfig.class.getResourceAsStream("/properties/path.properties");
            try {
                props.load(in);
                path = props.getProperty("resourcesPath");
            } catch (Exception e) {
            }
        }
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        int fileNum = 0, folderNum = 0;
        File file = new File(path);
        LinkedList<File> list = new LinkedList<>();

        if (file.exists()) {
            if (null == file.listFiles()) {
                return result;
            }
            list.addAll(Arrays.asList(file.listFiles()));
            while (!list.isEmpty()) {
                File[] files = list.removeFirst().listFiles();
                if (null == files) {
                    continue;
                }
                for (File f : files) {
                    if (f.isDirectory()) {
                        System.out.println("文件夹:" + f.getAbsolutePath());
                        list.add(f);
                        folderNum++;
                    } else {
                        Map<String, String> map = new HashMap<String, String>();
                        String filePath = f.getAbsolutePath();
                        String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
                        map.put("path", filePath);
                        map.put("name", fileName);
                        if (fileName.contains("_")) {
                            String table = fileName.substring(0, fileName.indexOf("_"));
                            String content = fileName.substring(fileName.indexOf("_") + 1);
                            table = table.replaceFirst("speedy", "speedy_");
                            List<Object> lists = (List<Object>) MySqldbUtil.mysqlSelect("select id from " + table + " where content like '%" + content + "%'", "list", null);
                            if (null!=lists&&lists.size() > 0) {
                                map.put("inuse", "是");
                            } else {
                                map.put("inuse", "否");
                            }
                        } else {
                            map.put("inuse", "是");
                        }

                        result.add(map);
                        fileNum++;
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
        System.out.println("文件夹数量:" + folderNum + ",文件数量:" + fileNum);
        return result;
    }
}
