import cn.backflow.generator.GeneratorFacade;
import cn.backflow.generator.GeneratorProperties;

/**
 * 代码生成器入口
 * Created by backflow on 2016/1/5 0:18.
 */
public class Generator {

    /**
     * 请直接修改以下代码调用不同的方法以执行相关生成任务.
     */
    public static void main(String[] args) throws Exception {
        GeneratorFacade g = new GeneratorFacade();

        g.deleteOutRootDir(); // 删除生成器的输出目录

        String testClasspath = Generator.class.getResource("").getPath();
        String projectRoot = testClasspath.substring(1, testClasspath.indexOf("target/"));
        // 若读取不到类路径下的文件, 请改用绝对路径,如: "D:\\template"

        String templatePath = projectRoot + "src/test/template/";

        //通过数据库表生成文件,template为模板的根目录
        g.generateByTable("t_personal", templatePath);

        // g.generateByAllTable(templatePath);

        // 打印数据库中的表名称
        // g.printAllTableNames();

        // 指定类与模板
        // g.generateByClass(Blog.class,"template_clazz");

        // 删除生成的文件
        // g.deleteByTable("table_name", "template");

        // 自动搜索数据库中的所有表并生成文件,template为模板的根目录
        // g.generateByAllTable("template");

        // 打开输出文件夹
        Runtime.getRuntime().exec(
                String.format("cmd.exe /c start %s",
                        GeneratorProperties.getRequiredProperty("outRoot")
                )
        );
    }
}
