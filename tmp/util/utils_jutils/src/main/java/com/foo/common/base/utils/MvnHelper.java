package com.foo.common.base.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.ByteStreams;

public class MvnHelper {
    public static final Logger logger = LoggerFactory
            .getLogger(MvnHelper.class);

    static boolean offlineMode = false;
    static String projectName = "industry-platform";
    static String mvnLocalRepository = "d:\\tools\\maven\\repository";
    static String globalProjectDirectory = "D:\\tools\\myGit\\" + projectName;
    static File globalPomFile = FileUtils
            .getFile(globalProjectDirectory + "\\pom.xml");

    public static void main(String[] args) throws Exception {

        logger.info("analysis pom file start at location of:{}",
                globalPomFile.getAbsolutePath());

        logger.info("local repository here is:{}", mvnLocalRepository);

        // preCheckMavenEnvironment();
        copyProjectDenpency();

        // checkProjectDenpencyRealTime();

        // deleteAllLastUpdatedFiles();

        // inistallMavenDenpencyJar(
        // "d:\\tools\\maven\\YeePay_HTMLcommon_V3.0.jar", "com.yeepay",
        // "yeepay", "3.0");
        // inistallMavenDenpencyJar("d:\\tools\\maven\\ojdbc14-10.2.0.1.0.jar",
        // "com.oracle", "ojdbc14", "10.2.0.1.0");

    }

    /**
     * 删除lastUpdated文件，防止maven读取本地缓存，当maven build失败的时候，调用此方法
     */
    public static void deleteAllLastUpdatedFiles() {
        File myFile = new File("D:\\tools\\maven\\repository");
        List<File> files = (List<File>) FileUtils.listFiles(myFile,
                new String[]{"lastUpdated"}, true);
        for (File file : files) {
            System.out.println(file.getAbsolutePath());
        }
    }

    public static void preCheckMavenEnvironment() throws Exception {

        logger.info("preCheckMavenEnvironment");
        String myCommand = "cmd /c D: && mvn -version && java -version ";
        // + "&& mvn help:active-profiles && mvn dependency:copy-dependencies ";
        Process p = Runtime.getRuntime().exec(myCommand);
        ByteStreams.copy(p.getInputStream(), System.out);
        ByteStreams.copy(p.getErrorStream(), System.err);
        p.waitFor();

    }

    public static void inistallMavenDenpencyJar(String jarFileFullPath,
                                                String groupId, String artifactId, String version)
            throws Exception {

        logger.info("inistallMavenDenpency");
        String myCommand = "cmd /c D: && mvn install:install-file -Dfile="
                + jarFileFullPath + " -DgroupId=" + groupId + " -DartifactId="
                + artifactId + " -Dversion=" + version + " -Dpackaging=jar";
        Process p = Runtime.getRuntime().exec(myCommand);
        ByteStreams.copy(p.getInputStream(), System.out);
        ByteStreams.copy(p.getErrorStream(), System.err);
        p.waitFor();

    }

    /**
     * 清理项目同时拷贝依赖。在此之后，你必须执行一次eclipse的clean来生成新的类
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public static void copyProjectDenpency()
            throws IOException, InterruptedException {

        String mvnProjectDirectory = "D:\\tools\\myGit\\" + projectName + "";
        File file = FileUtils
                .getFile("D:\\tools\\myGit\\" + projectName + "\\pom.xml");

        if (file == null) {
            logger.info("pom file do not exist");
            return;
        }

        logger.info("clean dependency directory.");

        File dependencyDir = new File(
                mvnProjectDirectory + "\\target\\dependency");

        if (dependencyDir.isDirectory()) {
            FileUtils.cleanDirectory(dependencyDir);
        }

        logger.info("copying start");
        String prefix = FilenameUtils.getPrefix(mvnProjectDirectory)
                .substring(0, 2);
        logger.info("drive relative here is: {}", prefix);

        String myCommand = "cmd /c " + prefix + " && cd " + mvnProjectDirectory
                + " && mvn dependency:copy-dependencies -DexcludeScope=provided "
                + (offlineMode ? "-offline" : "") + "";

        logger.info("command execute is:{}", myCommand);

        Process p = Runtime.getRuntime().exec(myCommand);
        ByteStreams.copy(p.getInputStream(), System.out);
        ByteStreams.copy(p.getErrorStream(), System.err);
        p.waitFor();

        logger.info("copying end,exit the system.");

    }

    public static void checkProjectDenpency() throws Exception {

        logger.info("Start:versions:display-plugin-updates");
        String myCommand = "cmd /c D: && cd D:\\tools\\myGit\\" + projectName
                + " && mvn versions:display-plugin-updates";
        Process p = Runtime.getRuntime().exec(myCommand);

        // FileUtils.copyInputStreamToFile(p.getInputStream(),
        // FooUtils.getTestLogFile());

        // FileUtils.copyInputStreamToFile(p.getErrorStream(),
        // FooUtils.getTestLogFile());

        ByteStreams.copy(p.getInputStream(), System.out);
        ByteStreams.copy(p.getErrorStream(), System.err);
        p.waitFor();

        logger.info("Start:mvn versions:display-dependency-updates");
        myCommand = "cmd /c D: && cd D:\\tools\\myGit\\" + projectName
                + " && mvn versions:display-dependency-updates";
        p = Runtime.getRuntime().exec(myCommand);
        ByteStreams.copy(p.getInputStream(), System.out);
        ByteStreams.copy(p.getErrorStream(), System.err);
        p.waitFor();

    }
}
