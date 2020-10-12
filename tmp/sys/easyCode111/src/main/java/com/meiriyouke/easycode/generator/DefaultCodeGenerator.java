package com.meiriyouke.easycode.generator;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;

import com.meiriyouke.easycode.config.Table;
import com.meiriyouke.easycode.config.Task;
import com.meiriyouke.easycode.utils.NameUtils;

/**
 * 默认代码生成实现类
 * 
 * User: liyd
 * Date: 13-12-16
 * Time: 下午4:28
 */
public class DefaultCodeGenerator extends AbstractEasyCodeGenerator {

    /**
     * 代码生成方法
     *
     * @param table
     * @param task
     * @param context
     * @param template
     */
    @Override
    public void generate(Table table, Task task, VelocityContext context, StringBuilder template) {

        context.put("date", new Date());
        context.put("table", table);
        context.put("task", task);
        context.put("packageName", task.getPackageName());
        String longClassKey = task.getName() + "GeneratedLongClassName";
        String shortClassKey = task.getName() + "GeneratedShotClassName";
        String firstLowerClassKey = task.getName() + "FirstLowerGeneratedClassName";
        String lineThroughClassKey = task.getName() + "LineThroughClassName";
        String generatedShotClassName = task.getGeneratedShotClassName(table.getName());
        context.put(longClassKey, task.getGeneratedReferenceClassName(table.getName()));
        context.put(shortClassKey, generatedShotClassName);
        context.put(firstLowerClassKey, NameUtils.getFirstLowerName(generatedShotClassName));
        context.put(lineThroughClassKey, NameUtils.getlineThroughName(generatedShotClassName));

        long serialVersionUID = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        context.put("serialVersionUID", serialVersionUID);

        Set<String> importSet = new HashSet<String>();

        String tmp = template.toString();
        if (StringUtils.indexOf(tmp, "List<") != -1
            && StringUtils.indexOf(tmp, "java.util.List") == -1) {
            importSet.add("java.util.List");
        }
        if (StringUtils.indexOf(tmp, "Map<") != -1
            && StringUtils.indexOf(tmp, "java.util.Map") == -1) {
            importSet.add("java.util.Map");
        }
        if (StringUtils.indexOf(tmp, "@Repository") != -1
            && StringUtils.indexOf(tmp, "org.springframework.stereotype.Repository") == -1) {
            importSet.add("org.springframework.stereotype.Repository");
        }
        if (StringUtils.indexOf(tmp, "@Autowired") != -1
            && StringUtils.indexOf(tmp, "org.springframework.beans.factory.annotation.Autowired") == -1) {
            importSet.add("org.springframework.beans.factory.annotation.Autowired");
        }
        if (StringUtils.indexOf(tmp, "@Component") != -1
            && StringUtils.indexOf(tmp, "org.springframework.stereotype.Component") == -1) {
            importSet.add("org.springframework.stereotype.Component");
        }
        if (StringUtils.indexOf(tmp, "${modelGeneratedShotClassName}") != -1
            && StringUtils.indexOf(tmp, "${modelGeneratedLongClassName}") == -1
            && !StringUtils.equals(task.getName(), "model")) {
            Object modelGeneratedLongClassName = context.get("modelGeneratedLongClassName");
            importSet.add(modelGeneratedLongClassName == null ? "" : modelGeneratedLongClassName
                .toString());
        }
        if (StringUtils.indexOf(tmp, "${modelVoGeneratedShotClassName}") != -1
            && StringUtils.indexOf(tmp, "${modelVoGeneratedLongClassName}") == -1
            && !StringUtils.equals(task.getName(), "modelVo")) {
            Object modelGeneratedLongClassName = context.get("modelVoGeneratedLongClassName");
            importSet.add(modelGeneratedLongClassName == null ? "" : modelGeneratedLongClassName
                .toString());
        }
        if (StringUtils.indexOf(tmp, "${javaMapperGeneratedShotClassName}") != -1
            && StringUtils.indexOf(tmp, "${javaMapperGeneratedLongClassName}") == -1
            && !StringUtils.equals(task.getName(), "javaMapper")) {
            Object mapperGeneratedLongClassName = context.get("javaMapperGeneratedLongClassName");
            importSet.add(mapperGeneratedLongClassName == null ? "" : mapperGeneratedLongClassName
                .toString());
        }
        if (StringUtils.indexOf(tmp, "${daoGeneratedShotClassName}") != -1
            && StringUtils.indexOf(tmp, "${daoGeneratedLongClassName}") == -1
            && !StringUtils.equals(task.getName(), "dao")) {
            Object daoGeneratedLongClassName = context.get("daoGeneratedLongClassName");
            importSet.add(daoGeneratedLongClassName == null ? "" : daoGeneratedLongClassName
                .toString());
        }
        if (StringUtils.indexOf(tmp, "${serviceGeneratedShotClassName}") != -1
            && StringUtils.indexOf(tmp, "${serviceGeneratedLongClassName}") == -1
            && !StringUtils.equals(task.getName(), "service")) {
            Object daoGeneratedLongClassName = context.get("serviceGeneratedLongClassName");
            importSet.add(daoGeneratedLongClassName == null ? "" : daoGeneratedLongClassName
                .toString());
        }

        if (StringUtils.equalsIgnoreCase("model", task.getName())
            || StringUtils.equalsIgnoreCase("modelVo", task.getName())) {
            importSet.addAll(super.getColumnsImportClass(table.getColumns()));
        }
        context.put("importList", importSet);
    }
}
