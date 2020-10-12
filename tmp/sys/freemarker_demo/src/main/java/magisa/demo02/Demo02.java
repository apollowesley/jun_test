package magisa.demo02;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * FreeMarker 综合示例
 * Created by magisa on 2014/8/27.
 */
public class Demo02 {
    // 模板目录
    public static final String templatePath = "ftl/demo02";

	public String hello() {
		return "HelloWorld";
	}

    public static void main(String[] args) throws IOException, TemplateException {
        // 创建配置
        Configuration configuration = new Configuration();
        configuration.setDirectoryForTemplateLoading(new File(templatePath));
        configuration.setObjectWrapper(new DefaultObjectWrapper());

        // 获取模板
        Template template = configuration.getTemplate("test.ftl");
        // 创建数据模型
        Map root = new HashMap();
	    root.put("demo02", new Demo02());

        // 合并模板和数据模型
        Writer writer = new OutputStreamWriter(System.out);
        template.process(root, writer);
        writer.flush();
    }
}















