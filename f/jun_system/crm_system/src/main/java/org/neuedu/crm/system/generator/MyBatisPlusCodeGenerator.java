package org.neuedu.crm.system.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.Test;

/**
 * @description
 * @auther: CDHONG.IT
 * @date: 2019/12/13-19:18
 **/
public class MyBatisPlusCodeGenerator {

    @Test
    public void codeGenerator() {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("CDHong");
        gc.setOpen(false);
        gc.setSwagger2(true); //实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql:///crm_system?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        //父级公用包名，就是自动生成的文件放在项目路径下的那个包中
        pc.setParent("org.neuedu.crm.system");
        mpg.setPackageInfo(pc);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null); //是否在mapper接口处生成xml文件
        mpg.setTemplate(templateConfig);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());  //设置模板引擎

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel); //Entity文件名称命名规范
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//Entity字段名称命名规范
        strategy.setEntityLombokModel(true);  //是否使用lombok完成Entity实体标注
        strategy.setRestControllerStyle(true);  //Controller注解使用是否RestController标注
        strategy.setControllerMappingHyphenStyle(true); //Controller注解名称，使用连字符（—）
        //strategy.setInclude("sys_user","sys_role","sys_permission");//生成的表名,不写默认所有
        //strategy.setTablePrefix("sys_");//表前缀，添加该表示，则生成的实体，不会有表前缀
        //strategy.setFieldPrefix("sys_");  //字段前缀
        mpg.setStrategy(strategy);
        mpg.execute();
    }

}
