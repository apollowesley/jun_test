package org.neuedu.his.geneator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * @author :     CDHONG.IT
 * @description :
 * @date :    2020/3/30 10:39
 */
public class CodeGenerator {

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        //配置 GlobalConfig
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("CDHong");  //生成类对应的作者注释
        gc.setOpen(false);
        gc.setSwagger2(true);  //实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        //配置 DataSourceConfig
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/neuedu_his?useUnicode=true&serverTimezone=UTC&useSSL=false&characterEncoding=utf8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("org.neuedu.his");  //父包 entity,service,controller
        mpg.setPackageInfo(pc);

        //Mapper XML 模板配置
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null); //是否在mapper接口处生成xml文件
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);  //数据库表映射到实体的命名策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel); //数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
        strategy.setEntityLombokModel(true);  //是否使用 lombok 生成 getting  setting
        strategy.setRestControllerStyle(true);  //是否使用 @RestController  如果是false 则使用 @Controller
        //strategy.setInclude("his_");  //表名，多个英文逗号分割
        strategy.setControllerMappingHyphenStyle(true);  //  驼峰转连字符
        strategy.setTablePrefix("his_"); //指定表前缀，如果不指定，则默认生成的实体会带上 TBL

        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}
