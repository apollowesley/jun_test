package com.foo.common.base.utils.mac;

import com.foo.common.base.utils.FooUtils;
import com.google.common.io.ByteStreams;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * maven helper of mac version.
 *
 * @author Steve
 */
public class MavenMacHelper {

    public static final Logger logger = LoggerFactory
            .getLogger(MavenMacHelper.class);

    static boolean offlineMode = false;
    String gitRootPath = "";
    static String projectName = FooUtils.getGlobalArtifactId();
    //
    private static String globalProjectDirectory = FooUtils.getGlobalGitHome()
            + FooUtils.getFileSeparator() + projectName;


    @Test
    public void testCopyProjectDenpency() throws IOException, InterruptedException {
        logger.info("analysis pom file start at location of:{}",
                FooUtils.getGlobalPomFile().getAbsolutePath());
        copyProjectDenpency();
    }

    public static void main(String[] args) throws Exception {

        logger.info("analysis pom file start at location of:{}",
                FooUtils.getGlobalPomFile().getAbsolutePath());

//         instance.preCheckMavenEnvironment();
//        instance.copyProjectDenpency();
//        PomHelper.instance.analyzePomDependency();
//        PomHelper.instance.analyzePomRepository();

//        instance.checkProjectDenpencyRealTime();

        // installMavenDenpencyJar(
        // "d:\\tools\\maven\\YeePay_HTMLcommon_V3.0.jar", "com.yeepay",
        // "yeepay", "3.0");
        // installMavenDenpencyJar("d:\\tools\\maven\\ojdbc14-10.2.0.1.0.jar",
        // "com.oracle", "ojdbc14", "10.2.0.1.0");

    }

    public void preCheckMavenEnvironment() throws Exception {

        logger.info("preCheckMavenEnvironment");
        String myCommand = FooUtils.getGlobalMvnBash() + " -version && java -version ";
        Process p = FooUtils.executeCommand(myCommand);
        // + "&& mvn help:active-profiles && mvn dependency:copy-dependencies ";
        ByteStreams.copy(p.getInputStream(), System.out);
        // ByteStreams.copy(p.getErrorStream(), System.err);
        p.waitFor();

    }


    /**
     * Generate all maven dependency jars under folder: /target/dependency. With a clean folder first.
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void copyProjectDenpency() throws IOException, InterruptedException {

        logger.info("clean dependency directory.");

        File dependencyDir = new File(
                globalProjectDirectory + "/target/dependency");

        if (dependencyDir.isDirectory()) {
            FileUtils.cleanDirectory(dependencyDir);
        }

        logger.info("copying start");

        String myCommand = " cd " + globalProjectDirectory + " && " + FooUtils.getGlobalMvnBash()
                + " dependency:copy-dependencies -DexcludeScope=provided "
                + (offlineMode ? "-offline" : "") + "";

        logger.info("command execute is:{}", myCommand);

        Process p = FooUtils.executeCommand(myCommand);
        ByteStreams.copy(p.getInputStream(), System.out);
        ByteStreams.copy(p.getErrorStream(), System.err);
        p.waitFor();

        logger.info("copying end,exit the system.");

    }

    public void installMavenDenpencyJar(String jarFileFullPath, String groupId,
                                        String artifactId, String version) throws Exception {

        logger.info("install maven denpency");
        String myCommand = FooUtils.getGlobalMvnBash() + " install:install-file -Dfile="
                + jarFileFullPath + " -DgroupId=" + groupId + " -DartifactId="
                + artifactId + " -Dversion=" + version + " -Dpackaging=jar";
        Process p = FooUtils.executeCommand(myCommand);
        ByteStreams.copy(p.getInputStream(), System.out);
        ByteStreams.copy(p.getErrorStream(), System.err);
        p.waitFor();

    }

}
