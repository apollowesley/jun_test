package com.dd.plugin;

import cn.org.rapid_framework.generator.GeneratorFacade;
import cn.org.rapid_framework.generator.GeneratorProperties;
import cn.org.rapid_framework.generator.util.SystemHelper;

import com.dd.plugin.utils.ExtClasspathLoader;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.*;


/**
 * @goal codeAutoGene
 * @phase compile
 * @requiresProject false
 */
public class CodeAutoGenerateMojo extends AbstractMojo {

    /**
     * @parameter property="configLocation"
     */
    private String configLocation;

    private String templateDirs;
    public void execute() throws MojoExecutionException, MojoFailureException {
        initSystemConfig();
        //获取代码模板文件夹
        initTemplateLocation();
        //代码生成方式，默认按从数据表中获取相关代码
        String geneMethod = GeneratorProperties.getProperty("geneMethod");
        if(StringUtils.isEmpty(geneMethod)){
            geneMethod = "fromTable";
        }
        if("fromTable".equals(geneMethod)){
            try {
                geneCodeFormTable();
            } catch (Exception e) {
                e.printStackTrace();
                throw new MojoExecutionException("生成代码时发生错误！" + e.getMessage());
            }
        }else{
            this.getLog().info("很抱歉！暂未开发其他的代码生成方式!");
        }
    }
    /**
     * 初始化配置根文件夹位置;配置文件固定在config文件夹下
     */
    private void initSystemConfig() throws MojoFailureException {
        //初始化配置文件地址
        if(StringUtils.isEmpty(configLocation)){
            if(SystemHelper.isWindowsOS){
                configLocation = "C:/AutoGenerateCode";
            }else{
                configLocation = "/AutoGenerateCode";
            }
        }
        File configLocationFile = new File(configLocation);
        if(!configLocationFile.exists()){
            configLocationFile.mkdir();
        }
        String generateConfigFolder = configLocation + "/config/";
        this.getLog().info("----配置文件根目录----" + generateConfigFolder);
        File file = new File(generateConfigFolder);
        if(!file.exists()){
           throw new  MojoFailureException("配置目录["+ generateConfigFolder +"]不存在");
        }
        ExtClasspathLoader.addURL(file);
    }

    private void initTemplateLocation(){
        templateDirs = GeneratorProperties.getProperty("templateDirs");
        String[] templateRoots = templateDirs.split(",");
        String tempRootDirs = "";
        for(int i=0;i<templateRoots.length;i++){
            tempRootDirs += configLocation + "/template/" + templateRoots[i];
            if(i < templateRoots.length - 1){
                tempRootDirs += ",";
            }
        }
        templateDirs = tempRootDirs;
        this.getLog().info("------templateRootDirs -> " + templateDirs + " tableNames-> " + GeneratorProperties.getProperty("tableNames"));
    }

    /**
     * 直接从数据库表中生成代码
     */
    private void geneCodeFormTable() throws Exception {
        GeneratorFacade g = new GeneratorFacade();
        g.getGenerator().setTemplateRootDir(templateDirs);//设置模版目录
        g.getGenerator().setOutRootDir(configLocation + "/output/" + GeneratorProperties.getProperty("outRoot"));
        g.deleteOutRootDir();                            //删除生成器的输出目录
        String outRootDir = g.getGenerator().getOutRootDir();
        File file = new File(outRootDir);
        if(!file.exists()){
            file.mkdir();
        }
        String tableNames = GeneratorProperties.getProperty("tableNames");
        if(StringUtils.isBlank(tableNames)){
            throw new Exception("数据表名不能为空!");
        }
        g.generateByTable(tableNames.split(","));	//通过数据库表生成文件,多表之间用逗号隔开
        //		g.generateByAllTable("template_table");	//自动搜索数据库中的所有表并生成文件,template为模板的根目录
        //		g.generateByClass(MyBeanTable.class,"template");
        //		g.deleteByTable("table_name", "template_clazz"); //删除生成的文件
        //打开文件夹
        Runtime.getRuntime().exec("cmd.exe /c start " + g.getGenerator().getOutRootDir());
    }
}
