package com.foo.common.base.utils;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Map;

public enum TomcatHelper {
    instance;
    private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TomcatHelper.class);

    public static String artifact = FooUtils.getGlobalArtifactId();
    private static String workingDirectory = "/Users/Steve/tmp/";
    private static String srcTomcatDir = workingDirectory + "apache-tomcat-8.0.36" + FooUtils.getFileSeparator();
    private static String targetTomcatDirName = workingDirectory + "tomcatUpgrade/apache-tomcat-8.0.36"
            + FooUtils.getFileSeparator();

    public static void main(String[] args) {
        try {
            instance.copyOriginalTomcat();
            instance.deleteUnnecessaryFiles();
            instance.renameUnnecessaryFiles();
            instance.copyNecessaryFiles();
            instance.copyRootXmlOnTemplate();
            instance.copyServerXmlOnTemplate();

        } catch (Exception e) {
            logger.error("Upgrade tomcat failure,error is:{}", e);
            FileUtils.deleteQuietly(new File(targetTomcatDirName));
        }

    }

    private void copyServerXmlOnTemplate() throws IOException {
        logger.info("copyServerXmlOnTemplate of server.xml");

        Map<String, Object> bean = Maps.newHashMap();
        bean.put("server-port", 8005);
        bean.put("http-port", 8080);
        bean.put("http-redirectPort", 8443);
        bean.put("ajp-port", 8009);
        bean.put("ajp-redirectPort", 8443);
        bean.put("default-host-name", "127.0.0.1");
        bean.put("host-name", "127.0.0.1");

        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile("tomcatUpgrade/server.xml");
        Writer writer = new StringWriter();
        mustache.execute(writer, bean);
        writer.flush();
        String result = writer.toString();
        File file = new File(targetTomcatDirName + "conf" + FooUtils.getFileSeparator() + "server.xml");
        IOUtils.write(result, new FileOutputStream(file), "utf-8");
    }

    private void copyRootXmlOnTemplate() throws IOException {
        logger.info("copyRootXmlOnTemplate of ROOT.xml");
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile("tomcatUpgrade/ROOT.xml");
        Writer writer = new StringWriter();
        mustache.execute(writer,
                ImmutableMap.<String, Object>of("artifact", artifact, "isWebsite", false));
        writer.flush();
        String result = writer.toString();
        File rootFilePath = new File(targetTomcatDirName + "conf" + FooUtils.getFileSeparator() + "Catalina"
                + FooUtils.getFileSeparator() + "127.0.0.1" + FooUtils.getFileSeparator() + "");
        FileUtils.forceMkdir(rootFilePath);
        IOUtils.write(result,
                new FileOutputStream(new File(rootFilePath + "" + FooUtils.getFileSeparator() + "ROOT.xml")), "utf-8");
    }

    private void copyNecessaryFiles() throws IOException {
        logger.info("copyNecessaryFiles of jars");
        File libDir = new File(targetTomcatDirName + "lib");

        for (String tmp : ImmutableList.of("tomcatUpgrade/logback-access-1.1.1.jar",
                "tomcatUpgrade/logback-core-1.1.1.jar", "tomcatUpgrade/mysql-connector-java-5.1.29.jar")) {
            FileUtils.copyFileToDirectory(FooUtils.getClassPathResourceFile(tmp), libDir);
        }

        logger.info("copyNecessaryFiles of conf");
        File confDir = new File(targetTomcatDirName + "conf");

        for (String tmp : ImmutableList.of("tomcatUpgrade/tomcat-users.xml",
                "tomcatUpgrade/logging.properties", "tomcatUpgrade/logback-access.xml")) {
            FileUtils.copyFileToDirectory(FooUtils.getClassPathResourceFile(tmp), confDir);
        }

        logger.info("copyNecessaryFiles of .gitignore file for root folder.");
        File gitignoreDir = new File(targetTomcatDirName);
        for (String tmp : ImmutableList.of("tomcatUpgrade/outer.gitignore")) {
            FileUtils.copyFileToDirectory(FooUtils.getClassPathResourceFile(tmp), gitignoreDir);
        }
        Files.move(new File(targetTomcatDirName + "outer.gitignore"), new File(targetTomcatDirName + ".gitignore"));

        logger.info("copyNecessaryFiles of .gitignore file for ROOT.xml ");
        String tmpDir = targetTomcatDirName + "conf" + FooUtils.getFileSeparator() + "Catalina"
                + FooUtils.getFileSeparator() + "127.0.0.1" + FooUtils.getFileSeparator() + "";
        gitignoreDir = new File(tmpDir);
        for (String tmp : ImmutableList.of("tomcatUpgrade/conf.gitignore")) {
            FileUtils.copyFileToDirectory(FooUtils.getClassPathResourceFile(tmp), gitignoreDir);
        }
        Files.move(new File(tmpDir + "conf.gitignore"), new File(tmpDir + ".gitignore"));

    }

    private void copyOriginalTomcat() throws Exception {
        logger.info("copy whole project for artifact {} to directory:{}", artifact, targetTomcatDirName);
        File tmpFile = new File(targetTomcatDirName);
        if (tmpFile.isDirectory()) {
            logger.warn("directory already exist,remove it now.");
            FileUtils.deleteDirectory(tmpFile);
        }
        FooUtils.copyDirectory(srcTomcatDir, targetTomcatDirName);
    }

    private void deleteUnnecessaryFiles() throws IOException {
        logger.info("deleteUnnecessaryFiles now.");
        FileUtils.deleteDirectory(new File(targetTomcatDirName + "temp"));
        FileUtils.deleteDirectory(new File(targetTomcatDirName + "webapps" + FooUtils.getFileSeparator() + "docs"));
        FileUtils.deleteDirectory(new File(targetTomcatDirName + "webapps" + FooUtils.getFileSeparator() + "examples"));
        FileUtils.cleanDirectory(new File(targetTomcatDirName + "logs"));
        FileUtils.cleanDirectory(new File(targetTomcatDirName + "work"));
        FileUtils.deleteDirectory(new File(targetTomcatDirName +
                FooUtils.getFileSeparator() + "conf" +
                FooUtils.getFileSeparator() + "Catalina"));
    }

    private void renameUnnecessaryFiles() throws IOException {
        logger.info("renameUnnecessaryFiles now.");
        File hostManager = new File(targetTomcatDirName + "webapps" + FooUtils.getFileSeparator() + "host-manager");
        if (hostManager.isDirectory()) {
            Files.move(new File(targetTomcatDirName + "webapps" + FooUtils.getFileSeparator() + "host-manager"),
                    new File(targetTomcatDirName + "webapps" + FooUtils.getFileSeparator()
                            + "feiYnn-console-host-manager"));
        }

        File manager = new File(targetTomcatDirName + "webapps" + FooUtils.getFileSeparator() + "manager");
        if (manager.isDirectory()) {
            Files.move(new File(targetTomcatDirName + "webapps" + FooUtils.getFileSeparator() + "manager"),
                    new File(targetTomcatDirName + "webapps" + FooUtils.getFileSeparator() + "feiYnn-console-manager"));
        }

        File root = new File(targetTomcatDirName + "webapps" + FooUtils.getFileSeparator() + "ROOT");
        if (root.isDirectory()) {
            Files.move(new File(targetTomcatDirName + "webapps" + FooUtils.getFileSeparator() + "ROOT"),
                    new File(targetTomcatDirName + "webapps" + FooUtils.getFileSeparator() + "feiYnn-console-config"));
        }

    }

}
