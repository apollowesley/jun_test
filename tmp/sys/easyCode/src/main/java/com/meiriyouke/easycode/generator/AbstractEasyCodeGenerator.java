package com.meiriyouke.easycode.generator;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;

import com.meiriyouke.easycode.config.Column;
import com.meiriyouke.easycode.config.Table;
import com.meiriyouke.easycode.config.Task;
import com.meiriyouke.easycode.context.EasyCodeContext;
import com.meiriyouke.easycode.plugins.EasyCodePlugin;
import com.meiriyouke.easycode.utils.FileUtils;
import com.meiriyouke.easycode.utils.VelocityUtils;

/**
 * 代码生成接口抽象实现
 * 
 * User: liyd
 * Date: 13-12-6
 * Time: 下午4:56
 */
public abstract class AbstractEasyCodeGenerator implements EasyCodeGenerator {

    /**
     * 代码生成方法
     *
     * @param table the table
     * @param task  the task
     * @param context the context
     */
    @Override
    public void doGenerate(Table table, Task task, VelocityContext context) {

        StringBuilder sbTemp = new StringBuilder(FileUtils.getTemplate(task.getTemplate()));

        //运行插件
        this.executePlugin(table, task, context, sbTemp);

        this.generate(table, task, context, sbTemp);

        String template = VelocityUtils.parseString(sbTemp.toString(), context);

        String targetDir = EasyCodeContext.getConstant("targetDir");
        targetDir = StringUtils.isBlank(targetDir) ? "" : targetDir + "/";
        String moduleDir = task.getModuleDir();
        moduleDir = StringUtils.isBlank(moduleDir) ? "" : moduleDir + "/";
        String srcDir = task.getSrcDir();
        srcDir = StringUtils.isBlank(srcDir) ? "" : srcDir + "/";
        String packageFileDir = task.getGeneratedFileName(table.getName());
        String filePath = targetDir + moduleDir + srcDir + packageFileDir;
        FileUtils.writeFile(filePath, template);
    }

    /**
     * 运行插件
     * 
     * @param table
     * @param task
     * @param context
     * @param sbTemp
     */
    private void executePlugin(Table table, Task task, VelocityContext context, StringBuilder sbTemp) {
        Map<String, EasyCodePlugin> pluginMap = task.getPluginMap();
        if (pluginMap == null || pluginMap.size() == 0) {
            return;
        }

        for (EasyCodePlugin easyCodePlugin : pluginMap.values()) {
            easyCodePlugin.execute(table, task, sbTemp, context);
        }
    }

    /**
     * 添加字段类型需要导入的包
     *
     * @param columns the columns
     * @return the columns import class
     */
    protected Set<String> getColumnsImportClass(List<Column> columns) {

        Set<String> importSet = new HashSet<String>();
        for (Column column : columns) {
            if (EasyCodeContext.getDataConvertType(column.getDbType()) != null) {
                addImportClass(importSet, EasyCodeContext.getDataConvertType(column.getDbType())
                    .getJavaClass());
            } else {
                addImportClass(importSet, column.getJavaClass());
            }
        }
        return importSet;
    }

    /**
     * 添加引用的类
     *
     * @param importSet
     * @param className
     */
    private void addImportClass(Set<String> importSet, String className) {

        if (StringUtils.startsWith(className, "java.lang")) {
            return;
        }
        importSet.add(className);
    }

    /**
     * 代码生成方法
     *
     * @param table the table
     * @param task the task
     * @param context the context
     * @param template the template
     */
    public abstract void generate(Table table, Task task, VelocityContext context,
                                  StringBuilder template);

}
