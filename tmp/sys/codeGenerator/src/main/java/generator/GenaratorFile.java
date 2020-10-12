package generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;
import java.util.Map;

/**
 * Created by Administrator on 2017-05-31.
 */
public class GenaratorFile {

    /***
     * freemarker 模板生成单个文件
     * @param templatePath 模板路径
     * @param root  字段集合
     * @param path
     * @param fileName 文件名
     * @throws IOException
     */
    public  static void  generatorSigleFile(String templatePath, Map root, String path, String fileName)throws IOException{
        Configuration cfg = new Configuration();
        File directory = new File("");//设定为当前文件夹
        try {

            cfg.setDirectoryForTemplateLoading(new File(directory.getCanonicalPath()+"/template"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        Template template = cfg.getTemplate(templatePath);

        OutputStream fos = new FileOutputStream(new File(path, fileName));
        Writer out = new OutputStreamWriter(fos);
        try {
            template.process(root, out);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        fos.flush();
        fos.close();
    }
}
