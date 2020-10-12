package com.cnbbx;

import com.cnbbx.model.Table;
import com.cnbbx.util.FileHelper;
import com.cnbbx.util.IOHelper;
import com.cnbbx.util.StringHelper;
import com.cnbbx.util.StringTemplate;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jinge
 * @email admin(a)cnbbx.com
 */
public class Generator {
    private static final String WEBAPP_GENERATOR_INSERT_LOCATION = "webapp-generator-insert-location";
//	private static final String WEBAPP_GENERATOR_INSERT_BEFORE_LOCATION = WEBAPP_GENERATOR_INSERT_LOCATION+":before";
//	private static final String WEBAPP_GENERATOR_INSERT_AFTER_LOCATION = WEBAPP_GENERATOR_INSERT_LOCATION+":after";

    public Generator() {
    }

    public void generateAllTable() throws Exception {
        List tables = DbModelProvider.getInstance().getAllTables();
        for (int j = 0; j < tables.size(); j++) {
            generateTable((Table) tables.get(j));
        }
    }

    public void generateTable(String tableName) throws Exception {
        Table table = DbModelProvider.getInstance().getTable(tableName);
        generateTable(table);
    }

    private void generateTable(Table table) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, TemplateException {
        System.out.println("***************************************************************");
        System.out.println("* BEGIN generate table:" + table.getSqlName());
        System.out.println("***************************************************************");
        Configuration config = new Configuration();
        File templateRootDir = new File("template").getAbsoluteFile();
        config.setDirectoryForTemplateLoading(templateRootDir);
        config.setNumberFormat("###############");
        config.setBooleanFormat("true,false");

        List files = new ArrayList();
        FileHelper.listFiles(templateRootDir, files);

        for (int i = 0; i < files.size(); i++) {
            File file = (File) files.get(i);
            String templateRelativePath = FileHelper.getRelativePath(templateRootDir, file);
            String outputFilePath = templateRelativePath;
            if (file.isDirectory() || file.isHidden())
                continue;
            if (templateRelativePath.trim().equals(""))
                continue;
            if (file.getName().toLowerCase().endsWith(".include")) {
                System.out.println("[skip]\t\t endsWith '.include' template:" + templateRelativePath);
                continue;
            }
            int testExpressionIndex = -1;
            if ((testExpressionIndex = templateRelativePath.indexOf('@')) != -1) {
                outputFilePath = templateRelativePath.substring(0, testExpressionIndex);
                String testExpressionKey = templateRelativePath.substring(testExpressionIndex + 1);
                Map map = getFilepathDataModel(table);
                Object expressionValue = map.get(testExpressionKey);
                if (!"true".equals(expressionValue.toString())) {
                    System.out.println("[not-generate]\t test expression '@" + testExpressionKey + "' is false,template:" + templateRelativePath);
                    continue;
                }
            }
            try {
                generateFile(table, config, templateRelativePath, outputFilePath);
            } catch (Exception e) {
                throw new RuntimeException("generate table '" + table.getSqlName() + "' oucur error,template is:" + templateRelativePath, e);
            }
        }
    }

    @SuppressWarnings("all")
    private void generateFile(Table table, Configuration config, String templateRelativePath, String outputFilePath) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, TemplateException {
        Template template = config.getTemplate(templateRelativePath);

        //将sys_user 前缀去掉，修改className首字母大写
        String sqlName = table.getSqlName();
        String tableRemovePrefixes = (String) PropertiesProvider.getProperties().get("tableRemovePrefixes");
        String[] prefixs = tableRemovePrefixes.split(",");
        for (String prefix : prefixs) {
            if (sqlName.contains(prefix)) {
                sqlName = sqlName.replace(prefix, "").trim();
                table.setClassName(StringHelper.makeAllWordFirstLetterUpperCase(sqlName));
                table.setClassNameLower(StringHelper.uncapitalize(StringHelper.makeAllWordFirstLetterUpperCase(sqlName)));
            }
        }
        //-------------------

        String targetFilename = getTargetFilename(table, outputFilePath);

        Map templateDataModel = getTemplateDataModel(table);
        File absoluteOutputFilePath = getAbsoluteOutputFilePath(targetFilename);
        if (absoluteOutputFilePath.exists()) {
            StringWriter newFileContentCollector = new StringWriter();
            if (isFoundInsertLocation(template, templateDataModel, absoluteOutputFilePath, newFileContentCollector)) {
                System.out.println("[insert]\t generate content into:" + targetFilename);
                IOHelper.saveFile(absoluteOutputFilePath, newFileContentCollector.toString());
                return;
            }
        }

        System.out.println("[generate]\t template:" + templateRelativePath + " to " + targetFilename);
        saveNewOutputFileContent(template, templateDataModel, absoluteOutputFilePath);
    }

    @SuppressWarnings("all")
    private String getTargetFilename(Table table, String templateFilepath) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Map fileModel = getFilepathDataModel(table);
        String targetFilename = resolveFile(templateFilepath, fileModel);
        return targetFilename;
    }

    /**
     * 得到生成"文件目录/文件路径"的Model
     **/
    private Map getFilepathDataModel(Table table) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Map fileModel = BeanUtils.describe(table);
        fileModel.putAll(PropertiesProvider.getProperties());
        return fileModel;
    }

    /**
     * 得到FreeMarker的Model
     **/
    @SuppressWarnings("all")
    private Map getTemplateDataModel(Table table) {
        Map model = new HashMap();
        model.putAll(PropertiesProvider.getProperties());
        model.put("table", table);
        return model;
    }

    private File getAbsoluteOutputFilePath(String targetFilename) {
        String outRoot = getOutRootDir();
        File outputFile = new File(outRoot, targetFilename);
        outputFile.getParentFile().mkdirs();
        return outputFile;
    }

    private boolean isFoundInsertLocation(Template template, Map model, File outputFile, StringWriter newFileContent) throws IOException, TemplateException {
        LineNumberReader reader = new LineNumberReader(new FileReader(outputFile));
        String line = null;
        boolean isFoundInsertLocation = false;

        PrintWriter writer = new PrintWriter(newFileContent);
        while ((line = reader.readLine()) != null) {
            writer.println(line);
            // only insert once
            if (!isFoundInsertLocation && line.indexOf(WEBAPP_GENERATOR_INSERT_LOCATION) >= 0) {
                template.process(model, writer);
                writer.println();
                isFoundInsertLocation = true;
            }
        }

        writer.close();
        reader.close();
        return isFoundInsertLocation;
    }

    private void saveNewOutputFileContent(Template template, Map model, File outputFile) throws IOException, TemplateException {
        FileWriter out = new FileWriter(outputFile);
        template.process(model, out);
        out.close();
    }

    @SuppressWarnings("all")
    private String resolveFile(String templateFilepath, Map fileModel) {
        return new StringTemplate(templateFilepath, fileModel).toString();
    }

    public void clean() throws IOException {
        String outRoot = getOutRootDir();
        FileUtils.deleteDirectory(new File(outRoot));
        System.out.println("[Delete Dir]	" + outRoot);
    }

    private String getOutRootDir() {
        return PropertiesProvider.getProperties().getProperty("outRoot");
    }

}
