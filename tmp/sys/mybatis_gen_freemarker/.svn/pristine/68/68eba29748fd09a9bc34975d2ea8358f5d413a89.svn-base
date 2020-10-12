package com.itmuch.gen.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerUtil {
    /**
     * 创建一个模板
     * @param name
     * @return
     * @throws IOException
     */
    public static Template getTemplate(String name) throws IOException {
        @SuppressWarnings("deprecation")
        Configuration conf = new Configuration();
        // 设定从哪里读取模板文件：从classpath下面的template目录中读取
        conf.setClassForTemplateLoading(FreemarkerUtil.class, "/template");
        Template template = conf.getTemplate(name);
        return template;
    }

    /**
     * 通过模板，将内容输出到控制台
     * @param name
     * @param root
     * @param fileName
     * @throws Exception
     */
    public static void print(String ftlName, Map<String, Object> root, String pathName, String fileName) throws Exception {
        Template template = getTemplate(ftlName);

        File path = new File(pathName);
        if (!path.exists()) {
            path.mkdirs();
        }
        FileWriter fw = new FileWriter(new File(pathName + fileName));
        template.process(root, new PrintWriter(System.out));
        template.process(root, fw);
        fw.close();
    }
}
