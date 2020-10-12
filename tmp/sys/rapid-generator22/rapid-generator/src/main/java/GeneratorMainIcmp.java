import cn.org.rapid_framework.generator.GeneratorFacade;

public class GeneratorMainIcmp {
    /**
     * 请直接修改以下代码调用不同的方法以执行相关生成任务.
     */
    public static void main(String[] args) throws Exception {
        GeneratorFacade g = new GeneratorFacade();
        g.getGenerator().setTemplateRootDir("classpath:/template-icmp");
        //  g.printAllTableNames(); // 打印数据库中的表名称

        g.deleteOutRootDir(); // 删除生成器的输出目录
//        g.generateByAllTable();
        g.generateByTable("t_user_info");
    }
}
