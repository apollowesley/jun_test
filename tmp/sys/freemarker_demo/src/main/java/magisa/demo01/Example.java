package magisa.demo01;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class Example {

    // 设置输出路径
	private static String FILE_DIR = "D:\\test\\demo01\\";

	public static void main(String[] args) {
		
		File file = new File(FILE_DIR);
		if (!file.isDirectory()) {
			file.mkdirs();
		}
		
		Example example = new Example();
		
		//计算式
		example.process("T101.ftl");

		//输出一个值
		HashMap t2root = new HashMap<String, String>();
		t2root.put("user", "RenSanNing");
		example.process("T102.ftl", t2root);

		//输出一个列表
		Map<String, Object> t3root = new HashMap<String, Object>();
        List<Food> menu = new ArrayList<Food>();
        menu.add(new Food("iText in Action", 98));
        menu.add(new Food("iBATIS in Action", 118));
        menu.add(new Food("Lucene in Action", 69));
        t3root.put("menu", menu);
		example.process("T103.ftl", t3root);

		//逻辑判断（IF,SWITCH)
		Map<String, Object> t4root = new HashMap<String, Object>();
		t4root.put("x", 2);
		t4root.put("y", "medium");
		example.process("T104.ftl", t4root);
		
		//自定义函数
		example.process("T105.ftl");
		
		//定义变量
		example.process("T106.ftl");

		//定义宏macro
		example.process("T107.ftl");
		
		//include
		example.process("T108.ftl");

		//名字空间
		example.process("T109.ftl");
		
		//自定义指令Directive
		Map<String, Object> t10root = new HashMap<String, Object>();
		t10root.put("systemdate", new SystemDateDirective());
		t10root.put("text_cut", new TextCutDirective());
		example.process("T110.ftl", t10root);
		
	}

	public void process(String templatePath, Map<String, ?> data){
		try {
			Configuration configuration = new Configuration();
			configuration.setDirectoryForTemplateLoading(new File("ftl/demo01"));
			configuration.setObjectWrapper(new DefaultObjectWrapper());
			
			//设置字符集
			configuration.setDefaultEncoding("UTF-8");
			
			//设置尖括号语法和方括号语法,默认是自动检测语法
			//  自动 AUTO_DETECT_TAG_SYNTAX
			//  尖括号 ANGLE_BRACKET_TAG_SYNTAX
			//  方括号 SQUARE_BRACKET_TAG_SYNTAX
			configuration.setTagSyntax(Configuration.AUTO_DETECT_TAG_SYNTAX);
	
			Writer writer = new OutputStreamWriter(new FileOutputStream(FILE_DIR + templatePath + ".txt"),"UTF-8");
			Template template = configuration.getTemplate(templatePath);
			template.process(data, writer);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void process(String templatePath) {
		process(templatePath, null);
	}

}
