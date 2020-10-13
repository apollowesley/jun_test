package cn.backflow.generator;

public class GeneratorMain {
    /**
     * 请直接修改以下代码调用不同的方法以执行相关生成任务.
     */
    public static void main(String[] args) throws Exception {
        GeneratorFacade g = new GeneratorFacade();

        g.deleteOutRootDir(); // 删除生成器的输出目录

        //通过数据库表生成文件,template为模板的根目录
        g.generateByTable("reset_record", "template");

        // g.generateByAllTable("template");

        // 打印数据库中的表名称
        // g.printAllTableNames();

        // g.generateByClass(Blog.class,"template_clazz");

        // 删除生成的文件
        // g.deleteByTable("table_name", "template");

        // 自动搜索数据库中的所有表并生成文件,template为模板的根目录
        // g.generateByAllTable("template");

        // 打开输出文件夹
        Runtime.getRuntime().exec(String.format("cmd.exe /c start %s%s",
                GeneratorProperties.getRequiredProperty("outRoot"), "src\\main\\webapp\\WEB-INF\\pages")
        );
    }
}