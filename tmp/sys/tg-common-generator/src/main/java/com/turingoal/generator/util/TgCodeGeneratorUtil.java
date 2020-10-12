package com.turingoal.generator.util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.system.ApplicationHome;
import com.turingoal.generator.commons.TgCodeGenerator;
import com.turingoal.generator.commons.TgFreeMarker;
import com.turingoal.generator.commons.TgSpringPropertiesSystem;
import com.turingoal.generator.constant.TgConstantGeneratorTags;
import com.turingoal.generator.constant.TgConstantSystemValues;
import com.turingoal.generator.core.domain.TgSqlTable;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import freemarker.template.TemplateException;

/**
 * TgCodeGeneratorUtil
 */
public final class TgCodeGeneratorUtil {
    private static Logger logger = LoggerFactory.getLogger(TgCodeGeneratorUtil.class);

    private TgCodeGeneratorUtil() {
        throw new Error("工具类不能实例化！");
    }

    /**
     * generate
     */
    public static void generate(final TgSpringPropertiesSystem tgSpringPropertiesSystem, final List<TgSqlTable> tables, final boolean showDetailLogs) throws Exception {
        String applicationHomeDir = null; // springboot jar包所在目录
        ApplicationHome applicationHome = new ApplicationHome(TgCodeGenerator.class.getClass());
        if (applicationHome != null) {
            applicationHomeDir = FileUtil.normalize(applicationHome.getDir().getAbsolutePath() + "/");
        }
        String tempBaseDir = tgSpringPropertiesSystem.getTemplateBaseDir(); // 模板base路径
        if (StrUtil.isBlank(tempBaseDir)) {
            tempBaseDir = applicationHomeDir + "templates/";
        }
        String templateName = tgSpringPropertiesSystem.getTemplateName();
        if (StrUtil.isBlank(templateName)) {
            logger.error("!!! 模板名称不能为空，请检查配置属性: templateName !!!");
            exitSystem(); // 退出系统
        }
        String templateDir = FileUtil.normalize(tempBaseDir + templateName + "/"); // 选中的模板目录
        if (!FileUtil.exist(templateDir)) {
            logger.error("!!! 所选模板不存在：" + templateDir + "，请检查配置属性: templateName !!!");
            exitSystem(); // 退出系统
        }
        TgFreeMarker tgFreeMarker = new TgFreeMarker(TgConstantSystemValues.FREE_MARKER_VERSION, templateDir);
        String projectName = tgSpringPropertiesSystem.getProjectName(); // 项目名称
        if (StrUtil.isBlank(projectName)) {
            logger.error("!!! 项目名称不能为空，请检查配置属性: projectName !!!");
            exitSystem(); // 退出系统
        }
        String outBaseDir = tgSpringPropertiesSystem.getOutputBaseDir(); // 输出base路径
        if (StrUtil.isBlank(outBaseDir)) {
            outBaseDir = applicationHomeDir + "output/";
        }
        String outpuDir = FileUtil.normalize(outBaseDir + projectName + "/"); // 输出文件路径
        if (FileUtil.exist(outpuDir)) { // 如果文件存在，先删除
            FileUtil.del(outpuDir);
        }
        String basePackage = tgSpringPropertiesSystem.getBasePackage(); // 包名
        if (StrUtil.isBlank(basePackage)) {
            logger.error("!!! 包名不能为空，请检查配置属性: basePackage !!!");
            exitSystem(); // 退出系统
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(TgConstantGeneratorTags.TAG_GLOBAL_PROJECT_NAME, projectName); // 项目名
        map.put(TgConstantGeneratorTags.TAG_GLOBAL_PROJECT_TITLE, tgSpringPropertiesSystem.getProjectTitle()); // 项目标题
        map.put(TgConstantGeneratorTags.TAG_GLOBAL_PROJECT_TITLE_SHORT, tgSpringPropertiesSystem.getProjectTitleShort()); // 项目标题（短）
        map.put(TgConstantGeneratorTags.TAG_GLOBAL_PROJECT_DESC, tgSpringPropertiesSystem.getProjectDesc()); // 项目描述
        map.put(TgConstantGeneratorTags.TAG_GLOBAL_BASE_PACKAGE, basePackage); // 包名
        map.put(TgConstantGeneratorTags.TAG_GLOBAL_DB_URL, tgSpringPropertiesSystem.getDatasourceUrl()); // 连接池url
        map.put(TgConstantGeneratorTags.TAG_GLOBAL_DB_USERNAME, tgSpringPropertiesSystem.getDatasourceUsername()); // 连接池用户名
        map.put(TgConstantGeneratorTags.TAG_GLOBAL_DB_PASSWORD, tgSpringPropertiesSystem.getDatasourcePassword()); // 连接池密码
        map.put(TgConstantGeneratorTags.TAG_GLOBAL_DB_SCHEMA, tgSpringPropertiesSystem.getSchema()); // 数据库schema
        map.put(TgConstantGeneratorTags.TAG_DIR_BASE_PACKAGE, StrUtil.replace(basePackage, ".", "/"));
        List<String> prefixsNeedRemove = tgSpringPropertiesSystem.getPrefixsListNeedRemove(); // 需要移除的前缀，英文逗号分隔
        List<String> ignoreTables = tgSpringPropertiesSystem.getIgnoreTables(); // 需要忽略的表，英文逗号分隔
        generateFiles(tgFreeMarker, templateDir, outpuDir, map, tables, ignoreTables, prefixsNeedRemove, showDetailLogs); // 递归模板文件生成代码
    }

    /**
     * 递归模板文件夹
     */
    private static void generateFiles(final TgFreeMarker tgFreeMarker, final String templateDir, final String outputDir, final Map<String, Object> map, final List<TgSqlTable> tables, final List<String> ignoreTables, final List<String> prefixsNeedRemove, final boolean showDetailLogs) {
        logger.info("============= 开始生成代码  =============");
        logger.info("*** 模板目录：" + templateDir + " ***");
        logger.info("*** 输出目录：" + outputDir + " ***");
        FileUtil.walkFiles(Paths.get(templateDir), 10, new SimpleFileVisitor<Path>() {
            /**
             * 访问目录
             */
            @Override
            public FileVisitResult preVisitDirectory(final Path path, final BasicFileAttributes attrs) throws IOException {
                String dirPath = FileUtil.normalize(path.toString()); // 目录路径
                String dirName = path.getFileName().toString(); // 目录名称
                String parentDirPath = FileUtil.getParent(dirPath, 1);
                String parentDirName = null;
                if (StrUtil.isNotBlank(parentDirPath)) {
                    parentDirName = FileUtil.getName(parentDirPath);
                }
                if (!isValidPath(dirPath)) {
                    logger.error("!!! 路劲不合法：" + dirPath + " !!!");
                } else {
                    if (isDirNeedsLoop(dirName) || isDirNeedsLoop(parentDirName)) { // 目录是否需要循环 或 父级目录需要循环（父级目录循环，子目录也循环）
                        for (TgSqlTable table : tables) { // 循环遍历数据表
                            String tableName = table.getTableName();
                            if (isTableNeedsProcess(ignoreTables, tableName)) { // 表是否需要处理
                                table.setPrefixsNeedRemove(prefixsNeedRemove);
                                map.put(TgConstantGeneratorTags.TAG_DIR_TABLE_NAME, table.getTableName()); // tableName
                                map.put(TgConstantGeneratorTags.TAG_DIR_CLASS_NAME, table.getClassName()); // className
                                map.put(TgConstantGeneratorTags.TAG_DIR_CLASS_NAME_FIRST_LOWER, table.getClassNameFirstLower()); // classNameFirstLower
                                try {
                                    String dir = outputDir + StrUtil.removePrefix(tgFreeMarker.processStringTemplate(dirPath, map), templateDir);
                                    FileUtil.mkdir(dir); // 创建目录
                                    if (showDetailLogs) {
                                        logger.info("创建目录:" + dir);
                                    }
                                } catch (IOException | TemplateException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } else if (dirName.contains(TgConstantGeneratorTags.TAG_DIR_BASE_PACKAGE)) {
                        try {
                            String dir = outputDir + StrUtil.removePrefix(tgFreeMarker.processStringTemplate(dirPath, map), templateDir);
                            FileUtil.mkdir(dir); // 创建目录
                            if (showDetailLogs) {
                                logger.info("创建目录:" + dir);
                            }
                        } catch (IOException | TemplateException e) {
                            e.printStackTrace();
                        }
                    } else if (!isDirNeedsProcess(dirPath)) { // 目录不需要处理
                        String dir = outputDir + StrUtil.removePrefix(dirPath, templateDir);
                        FileUtil.mkdir(dir); // 创建目录
                        if (showDetailLogs) {
                            logger.info("创建目录:" + dir);
                        }
                    }
                }
                return FileVisitResult.CONTINUE;
            }

            /**
             * 访问文件
             */
            @Override
            public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
                String filePath = FileUtil.normalize(file.toString()); // 文件路径
                String fileName = file.getFileName().toString(); // 文件名称
                String parentDirPath = FileUtil.getParent(filePath, 1);
                String parentDirName = null;
                if (StrUtil.isNotBlank(parentDirPath)) {
                    parentDirName = FileUtil.getName(parentDirPath);
                }
                if (!isValidPath(filePath)) {
                    logger.error("!!! 路劲不合法：" + filePath + " !!!");
                } else {
                    if (isFileNeedsLoop(fileName) || isDirNeedsLoop(parentDirName)) { // 文件是否需要循环 或 父级目录需要循环（父级目录循环，子文件也循环）
                        for (TgSqlTable table : tables) { // 循环遍历数据表，依次生成文件
                            String tableName = table.getTableName();
                            if (isTableNeedsProcess(ignoreTables, tableName)) { // 表是否需要处理
                                table.setPrefixsNeedRemove(prefixsNeedRemove);
                                map.put(TgConstantGeneratorTags.TAG_FILE_TABLE_NAME, table.getTableName()); // tableName
                                map.put(TgConstantGeneratorTags.TAG_FILE_CLASS_NAME, table.getClassName()); // className
                                map.put(TgConstantGeneratorTags.TAG_FILE_CLASS_NAME_FIRST_LOWER, table.getClassNameFirstLower()); // classNameFirstLower
                                map.put(TgConstantGeneratorTags.TAG_DIR_TABLE_NAME, table.getTableName()); // tableName
                                map.put(TgConstantGeneratorTags.TAG_DIR_CLASS_NAME, table.getClassName()); // className
                                map.put(TgConstantGeneratorTags.TAG_DIR_CLASS_NAME_FIRST_LOWER, table.getClassNameFirstLower()); // classNameFirstLower
                                map.put("table", table);
                                try {
                                    String outputAbsolutePath = outputDir + StrUtil.removePrefix(tgFreeMarker.processStringTemplate(filePath, map), templateDir);
                                    FileUtil.mkParentDirs(outputAbsolutePath); // 创建目录
                                    tgFreeMarker.processTemplate(filePath, templateDir, outputAbsolutePath, map);
                                    if (showDetailLogs) {
                                        logger.info("生成文件:" + outputAbsolutePath);
                                    }
                                } catch (IOException | TemplateException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } else {
                        try {
                            String outputAbsolutePath = outputDir + StrUtil.removePrefix(tgFreeMarker.processStringTemplate(filePath, map), templateDir);
                            FileUtil.mkParentDirs(outputAbsolutePath); // 创建目录
                            tgFreeMarker.processTemplate(filePath, templateDir, outputAbsolutePath, map);
                            if (showDetailLogs) {
                                logger.info("生成文件:" + outputAbsolutePath);
                            }
                        } catch (IOException | TemplateException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });
        logger.info("============= 代码生成完成  =============");
        exitSystem(); // 退出系统
    }

    /**
     * 退出系统
     */
    private static void exitSystem() {
        logger.info("<<<<<<<<<<<<  系统关闭    >>>>>>>>>>>>>>");
        System.exit(0); // 系统退出
    }

    /**
     * 检查路径是否合法<br>
     * 一个路径不能同时有两个循环类的标签 tableName，className，classNameFirstLower
     */
    private static boolean isValidPath(final String path) {
        boolean flag = true;
        String str = StrUtil.getContainsStr(path,
                // 文件夹
                TgConstantGeneratorTags.TAG_DIR_TABLE_NAME_WARP, TgConstantGeneratorTags.TAG_DIR_CLASS_NAME_WARP, TgConstantGeneratorTags.TAG_DIR_CLASS_NAME_FIRST_LOWER_WARP,
                // 文件
                TgConstantGeneratorTags.TAG_FILE_TABLE_NAME_WARP, TgConstantGeneratorTags.TAG_FILE_CLASS_NAME_WARP, TgConstantGeneratorTags.TAG_FILE_CLASS_NAME_FIRST_LOWER_WARP);
        if (str != null) {
            String s1 = StrUtil.replace(path, str, "");
            if (StrUtil.containsAny(s1,
                    // 文件夹
                    TgConstantGeneratorTags.TAG_DIR_TABLE_NAME_WARP, TgConstantGeneratorTags.TAG_DIR_CLASS_NAME_WARP, TgConstantGeneratorTags.TAG_DIR_CLASS_NAME_FIRST_LOWER_WARP,
                    // 文件
                    TgConstantGeneratorTags.TAG_FILE_TABLE_NAME_WARP, TgConstantGeneratorTags.TAG_FILE_CLASS_NAME_WARP, TgConstantGeneratorTags.TAG_FILE_CLASS_NAME_FIRST_LOWER_WARP)) {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * table是否需要处理
     */
    private static boolean isTableNeedsProcess(final List<String> ignoreTables, final String tableName) {
        return !(ignoreTables != null && ignoreTables.size() > 0 && ignoreTables.contains(tableName));
    }

    /**
     * 文件是否需要循环
     */
    private static boolean isFileNeedsLoop(final String fileName) {
        boolean flag = false;
        if (StrUtil.isNotBlank(fileName)) {
            flag = StrUtil.containsAny(fileName, TgConstantGeneratorTags.TAG_FILE_TABLE_NAME_WARP, TgConstantGeneratorTags.TAG_FILE_CLASS_NAME_WARP, TgConstantGeneratorTags.TAG_FILE_CLASS_NAME_FIRST_LOWER_WARP);
        }
        return flag;
    }

    /**
     * 目录是否需要循环
     */
    private static boolean isDirNeedsLoop(final String dirName) {
        boolean flag = false;
        if (StrUtil.isNotBlank(dirName)) {
            flag = StrUtil.containsAny(dirName, TgConstantGeneratorTags.TAG_DIR_TABLE_NAME_WARP, TgConstantGeneratorTags.TAG_DIR_CLASS_NAME_WARP, TgConstantGeneratorTags.TAG_DIR_CLASS_NAME_FIRST_LOWER_WARP);
        }
        return flag;
    }

    /**
     * 目录是否需要处理
     */
    private static boolean isDirNeedsProcess(final String dirPath) {
        boolean flag = false;
        if (StrUtil.isNotBlank(dirPath)) {
            flag = StrUtil.containsAny(dirPath, TgConstantGeneratorTags.TAG_DIR_TABLE_NAME_WARP, TgConstantGeneratorTags.TAG_DIR_CLASS_NAME_WARP, TgConstantGeneratorTags.TAG_DIR_CLASS_NAME_FIRST_LOWER_WARP, TgConstantGeneratorTags.TAG_DIR_BASE_PACKAGE_WARP);
        }
        return flag;
    }
}
