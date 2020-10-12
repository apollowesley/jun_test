package com.foo.common.base.utils;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Decorator a project as :nodejs project,java maven project and so on.
 */
public class ProjectDecoratorHelper {


    final private static Logger logger = LoggerFactory.getLogger(ProjectDecoratorHelper.class);
    final private String globalArtifactId = FooUtils.getGlobalArtifactId();
    final private String globalArtifactPath = FooUtils.getGlobalArtifactPath();
    private Map<String, Object> commonDataMap = null;
    private final File globalArtifactDir = new File(globalArtifactPath);

    private Map<String, Object> getCommonDataMap() {
        if (commonDataMap != null) {
            return commonDataMap;
        }
        commonDataMap = ImmutableMap.of("artifactId", globalArtifactId);
        return commonDataMap;
    }

    /**
     * 通用操作:修改.gitignore文件,加入
     */
    @Test
    public void decoratorAsCommonProject() {
        logger.info("decoratorAsCommonProject.");
        alwaysWriteAndOverrideFile(".gitignore", "projectHelper/common/.gitignore.tmpl");
        //if you do not need any java facet,just comment this line.
        alwaysWriteAndOverrideFile(globalArtifactId + ".iml", "projectHelper/common/.iml.tmpl");
    }

    /**
     * 加入maven支持功能
     */
    @Test
    public void decoratorAsMavenProject() {
        logger.info("decoratorAsMavenProject.");
        decoratorAsCommonProject();
        Stream.of("pom.xml").forEach((t) -> createFileIfNotExist(t, "projectHelper/maven/" + t + ".tmpl"));
    }

    /**
     * 加入eclipse工程支持功能
     */
    @Test
    public void decoratorAsEclipseProject() {
        logger.info("decoratorAsEclipseProject");
        decoratorAsCommonProject();
        Stream.of(".classpath", ".project").forEach((t) -> createFileIfNotExist(t, "projectHelper/eclipse/" + t + ".tmpl"));
    }

    /**
     * 加入nodeJs支持功能
     */
    @Test
    public void decoratorAsNodeJsProject() {
        logger.info("decoratorAsNodeJsProject");
        decoratorAsCommonProject();
        Stream.of(".babelrc", "gulpfile.js", "package.json").forEach((t) -> createFileIfNotExist(t, "projectHelper/nodeJs/" + t + ".tmpl"));
        logger.info("decoratorAsNodeJsProject success,execute 【npm update】at root dir.");
    }

    /**
     * 加入Java支持功能
     */
    @Test
    public void decoratorAsJavaProject() throws Exception {
        decoratorAsCommonProject();
        logger.info("decoratorAsJavaProject");

        logger.info("decoratorAsJavaProject,Create relatie dirs.");
        final String baseDir = globalArtifactPath + "" +
                FooUtils.getFileSeparator() + "src" +
                FooUtils.getFileSeparator() + "main";
        final String javaSourceDir = baseDir +
                FooUtils.getFileSeparator() + "java" +
                FooUtils.getFileSeparator() + "com" +
                FooUtils.getFileSeparator() + "foo" +
                FooUtils.getFileSeparator() + globalArtifactId + "";
        final String resourceDir = baseDir + FooUtils.getFileSeparator() + "resources";
        FooUtils.forceMkdir(javaSourceDir);
        FooUtils.forceMkdir(resourceDir);

        logger.info("decoratorAsJavaProject,Copy logback.xml file.");
        final String destFileData = FooUtils.generateDataWithMustache(getCommonDataMap(), "projectHelper/java/logback.xml.tmpl");
        FooUtils.writeStringToFile(new File(resourceDir + FooUtils.getFileSeparator() + "logback.xml"), destFileData);


    }

    private void alwaysWriteAndOverrideFile(String fileName, String tmplFileName) {
        logger.info("alwaysWriteAndOverrideFile:{}", fileName);
        File destFile = new File(FilenameUtils.concat(globalArtifactPath, fileName));
        final String destFileData;
        try {
            destFileData = FooUtils.generateDataWithMustache(getCommonDataMap(), tmplFileName);
            FooUtils.writeStringToFile(destFile, destFileData);
        } catch (Exception e) {
            logger.error("alwaysWriteAndOverrideFile:{} error:{}", fileName, e);
        }

    }

    private void createFileIfNotExist(String fileName, String tmplFileName) {
        logger.info("createFileIfNotExist:{}", fileName);
        String destFileData;
        try {
            boolean isFileExist = Files.list(globalArtifactDir.toPath()).anyMatch((t) -> t.getFileName().toString().equals(fileName));
            if (isFileExist) {
                logger.info("file named:{} already exist,quick return.", fileName);
                return;
            }
            File destFile = new File(FilenameUtils.concat(globalArtifactPath, fileName));
            destFileData = FooUtils.generateDataWithMustache(getCommonDataMap(), tmplFileName);
            FooUtils.writeStringToFile(destFile, destFileData);
        } catch (Exception e) {
            logger.error("createFileIfNotExista:{},error:{}", fileName, e);
        }
    }


}
