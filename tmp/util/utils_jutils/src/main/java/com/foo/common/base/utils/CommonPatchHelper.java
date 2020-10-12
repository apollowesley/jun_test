package com.foo.common.base.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.joda.time.DateTime;

import com.foo.common.base.pojo.CommonPatchRelease;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;

/**
 * @author Steve
 */
public enum CommonPatchHelper {

    INSTANCE;

    public static String artifactId;

    private static String commitAcc;
    ;
    private static String endAcc;
    ;// default value:HEAD

    public static String projectDirectory;

    private static String workingDirectory;

    private boolean isPomChanged = false;// for jar dependency update hint.
    private boolean isSqlNeedExecuted = false;
    private boolean isGlobalConfigChanged = false;
    private boolean isLogbackXmlChanged = false;
    private boolean isHibernateMysqlChanged = false;
    private boolean isHibernateMysqlPoolChanged = false;
    private static String isPatchLibOnPomChange;
    private static String isFetchLatestFromServer = "";

    private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory
            .getLogger(CommonPatchHelper.class);

    // 生成windows和mac都可以正确识别的文件目录，例如：2016-03-23-11.19.21
    private static final String timeToken = new DateTime()
            .toString("yyyy-MM-dd-HH.mm.ss");

    private static final String pathName = "patch-" + timeToken
            + SystemUtils.FILE_SEPARATOR;

    private static String latestCommitAcc;

    public static void main(String[] args) throws IOException {

        Properties p = FooUtils
                .getPropertiesFromResourceFile(("common-patch.properties"));
        artifactId = p.getProperty("artifactId");
        isFetchLatestFromServer = Strings
                .nullToEmpty(p.getProperty("isFetchLatestFromServer"));
        commitAcc = getCommitAcc(p.getProperty("commitAcc"));
        endAcc = p.getProperty("endAcc");
        if (SystemUtils.IS_OS_MAC) {
            projectDirectory = p.getProperty("projectDirectory_mac");
            workingDirectory = p.getProperty("workingDirectory_mac");
        } else {
            projectDirectory = p.getProperty("projectDirectory");
            workingDirectory = p.getProperty("workingDirectory");
        }

        isPatchLibOnPomChange = Strings
                .nullToEmpty(p.getProperty("patch-lib-on-pom-change"));

        // Compare commtAcc and latest commitAcc,if the same,quick exit.
        latestCommitAcc = GitHelper.getCommitHeadAcc(artifactId,
                projectDirectory, false);

        if (latestCommitAcc.equals(commitAcc)) {
            logger.error("打包版本与当前最新版本一致，没有需要更新的文件。打包commitAcc为：{}",
                    latestCommitAcc);
            return;
        }

        File tmpDir = new File(workingDirectory);
        if (!tmpDir.isDirectory()) {
            FileUtils.forceMkdir(tmpDir);
            logger.warn("create dir success now:{}", tmpDir.getPath());
            // Files.createParentDirs(tmpDir);
        }

        try {
            CommonPatchHelper.INSTANCE.patchFromGitDiff(workingDirectory,
                    pathName, commitAcc, endAcc);
        } catch (Exception e) {
            String directory = workingDirectory + pathName;
            logger.error("{}", e);
            logger.error("delete the folder on exception now:{}", directory);
            FileUtils.deleteQuietly(new File(directory));
        }
    }

    public void patchFromGitDiff(String workingDirectory, String pathName,
                                 String commitAcc, String endAcc) throws Exception {

        Set<String> myErrorProjectNameSet = Sets.newHashSet();// Error set

        logger.info("patch name will be {}", pathName);

        FileUtils.deleteDirectory(new File(workingDirectory + pathName));

        Set<String> saveOrUpdateClassSet = Sets.newHashSet();
        Set<String> configFileSet = Sets.newHashSet();
        Set<String> webappSourceSet = Sets.newHashSet();
        // The file exists in changed list but you should not deployment them
        Set<String> excludeSet = Sets.newHashSet();

        Map<String, Set<String>> bean = GitHelper.getGitUpdateLinesFromCmd_Diff(
                projectDirectory, artifactId, commitAcc, endAcc);

        Set<String> updatedFileNameSet = bean.get("updatedFileNameSet");
        Set<String> deletedFileNameSetTmp = bean.get("deletedFileNameSet");
        Set<String> deletedFileNameSetResult = Sets.newHashSet();

        for (String tmp : deletedFileNameSetTmp) {
            tmp = FilenameUtils.normalize(tmp);
            deletedFileNameSetResult.add(tmp);
        }

        for (String tmp : updatedFileNameSet) {

            tmp = FilenameUtils.normalize(tmp);

            logger.info("tmp is:{}", tmp);

            if (tmp.contains("pom.xml")) {
                isPomChanged = true;
//                copyDependencyOnPomChanged();
                logger.warn(
                        "maven pom file detected,skip this file.But you should add jar dependency");
                excludeSet.add(tmp);
                continue;
            }

            if (tmp.contains("deployment.sql")) {
                isSqlNeedExecuted = true;
                logger.warn(
                        "sql executed on server detected,skip this file.But you should execute sql on server.");
                excludeSet.add(tmp);
                continue;
            }

            if (tmp.contains("globalConfig.xml")) {
                isGlobalConfigChanged = true;
                logger.warn(
                        "globalConfig detected,skip this file.But you should config it on server.");
                excludeSet.add(tmp);
                continue;
            }

            if (tmp.contains("hibernate-localhost-mysql.properties")) {
                isHibernateMysqlChanged = true;
                logger.warn("hibernate-localhost-mysql.properties changed!");
                excludeSet.add(tmp);
                continue;
            }
            if (tmp.contains("logback.xml")) {
                isLogbackXmlChanged = true;
                logger.warn("logback.xml changed!");
                excludeSet.add(tmp);
                continue;
            }
            if (tmp.contains("pool-mysql.properties")) {
                isHibernateMysqlPoolChanged = true;
                logger.warn("pool-mysql.properties changed!");
                excludeSet.add(tmp);
                continue;
            }
            if (tmp.contains(".gitignore")) {
                logger.warn(".gitignore detected!");
                excludeSet.add(tmp);
                continue;
            }
            if (tmp.contains(".DS_Store")) {
                logger.warn(".DS_Store osx detected!");
                excludeSet.add(tmp);
                continue;
            }
            if (tmp.contains(".classpath")) {
                logger.warn(".classpath detected!");
                excludeSet.add(tmp);
                continue;
            }
            if (tmp.contains(".iml")) {
                logger.warn("intellij-idea config file detected!");
                excludeSet.add(tmp);
                continue;
            }
            if (tmp.contains(".babelrc")) {
                logger.warn("babel config file detected!");
                excludeSet.add(tmp);
                continue;
            }
            if (tmp.contains("package.json")) {
                logger.warn(
                        "package.json detected,now do not support npm function now!");
                excludeSet.add(tmp);
                continue;
            }
            if (tmp.contains("gulpfile.js")) {
                logger.warn(
                        "gulpfile.js detected,now do not support gulp function now!");
                excludeSet.add(tmp);
                continue;
            }
            if (tmp.contains(".project")) {
                logger.warn(".project detected!");
                excludeSet.add(tmp);
                continue;
            }
            if (tmp.contains("globalConfig.xml")) {
                logger.warn("globalConfig.xml detected!");
                excludeSet.add(tmp);
                continue;
            }

            if (tmp.startsWith("src" + SystemUtils.FILE_SEPARATOR + "main"
                    + SystemUtils.FILE_SEPARATOR + "java")) {
                saveOrUpdateClassSet
                        .add(tmp.replaceAll("/", SystemUtils.FILE_SEPARATOR));
                continue;
            } else if (tmp.startsWith("src" + SystemUtils.FILE_SEPARATOR
                    + "main" + SystemUtils.FILE_SEPARATOR + "resources")) {
                configFileSet.add(tmp);
                continue;
            } else if (tmp
                    .startsWith("src" + SystemUtils.FILE_SEPARATOR + "test")) {
                excludeSet.add(tmp);
                continue;
            } else {
                webappSourceSet.add(tmp);
            }

        }

        Map<String, Integer> classSizeResultMap = CommonClassCompileHelper
                .copyJavaSrcAndClass(saveOrUpdateClassSet, workingDirectory,
                        projectDirectory, artifactId);

        int normalClassSize = classSizeResultMap.get("normalClassSize");
        int innerClassSize = classSizeResultMap.get("innerClassSize");

        File srcDir = new File(
                workingDirectory + "target" + SystemUtils.FILE_SEPARATOR);
        File destDir = new File(workingDirectory + pathName + "WEB-INF"
                + SystemUtils.FILE_SEPARATOR + "classes");

        // 只要有target存在就可以判定是存在编译了的class类的
        Preconditions.checkArgument(srcDir.isDirectory(),
                "srcDir of:" + srcDir + " do not exist or is not a directory");

        FileUtils.copyDirectory(srcDir, destDir);

        logger.info("coping class over,ready to copy webApp resources.");

        String tmpOldPath = "";
        String tmpRelativePath = "";
        for (String filePath : webappSourceSet) {

            logger.trace("webappSourceSet to be copied is:{}", filePath);

            File oldFile = new File(projectDirectory + artifactId
                    + SystemUtils.FILE_SEPARATOR + filePath);

            tmpOldPath = FilenameUtils.getFullPath(oldFile.getPath());
            logger.trace("tmpOldPath path:{}", tmpOldPath);
            logger.trace("tmpRelativePath path:{}",
                    projectDirectory + artifactId + ""
                            + SystemUtils.FILE_SEPARATOR + "src"
                            + SystemUtils.FILE_SEPARATOR + "main"
                            + SystemUtils.FILE_SEPARATOR + "webapp"
                            + SystemUtils.FILE_SEPARATOR + "");

            tmpRelativePath = tmpOldPath.replace(projectDirectory + artifactId
                    + "" + SystemUtils.FILE_SEPARATOR + "src"
                    + SystemUtils.FILE_SEPARATOR + "main"
                    + SystemUtils.FILE_SEPARATOR + "webapp"
                    + SystemUtils.FILE_SEPARATOR + "", "");

            logger.trace("relativePath is:{}", tmpRelativePath);

            FileUtils.copyFileToDirectory(oldFile,
                    new File(workingDirectory + pathName + tmpRelativePath));
        }

        logger.info("coping webApp resources over,ready to copy config files.");

        String tmpDestDir = "";
        for (String filePath : configFileSet) {

            File oldFile = new File(projectDirectory + artifactId
                    + SystemUtils.FILE_SEPARATOR + filePath);
            String relativePath = "WEB-INF" + SystemUtils.FILE_SEPARATOR
                    + "classes" + SystemUtils.FILE_SEPARATOR + ""
                    + FilenameUtils.getFullPath(oldFile.getPath())
                    .replace(projectDirectory + artifactId + ""
                            + SystemUtils.FILE_SEPARATOR + "src"
                            + SystemUtils.FILE_SEPARATOR + "main"
                            + SystemUtils.FILE_SEPARATOR + "resources"
                            + SystemUtils.FILE_SEPARATOR + "", "");

            tmpDestDir = workingDirectory + pathName + relativePath;

            logger.trace(
                    "filePath is:{} and relativePath is:{} and tmpDestDir is:{}",
                    filePath, relativePath, tmpDestDir);

            FileUtils.copyFileToDirectory(oldFile, new File(tmpDestDir));
        }

		/*
         * check patch success or not,Start
		 */

        logger.info(
                "the last size of updatedFileNameSet is:{},saveOrUpdateClassSet is:{},configFileSet is:{},webappSourceSet is:{},excludeSet is:{}",
                updatedFileNameSet.size(), saveOrUpdateClassSet.size(),
                configFileSet.size(), webappSourceSet.size(),
                excludeSet.size());

        Preconditions
                .checkArgument(
                        (updatedFileNameSet.size() == saveOrUpdateClassSet
                                .size() + configFileSet.size()
                                + webappSourceSet.size() + excludeSet.size()),
                        "updatedFileNameSet do not match all sets,");

        // 请注意，实际打包的类因为内部类的存在，大多数情况会与git的统计不一致，我们只能确定：拷贝后的所有资源文件=当前文件夹下的所有文件数
        int allFileUpdatedSize = normalClassSize + innerClassSize
                + webappSourceSet.size() + configFileSet.size();
        logger.info("all files your update size exclude folder will be:{}",
                allFileUpdatedSize);
        int currentFileNum = FooUtils
                .getDirectoryFilesNumber(new File(workingDirectory + pathName));
        Preconditions.checkArgument(currentFileNum == allFileUpdatedSize,
                "Class number do not match.");
        /*
         * check patch success or not,End
		 */

        // Write to file start,2015-05-18 verison.
        logger.warn("excludeSet as:{}", excludeSet);

        StringBuilder tmpBuilder = new StringBuilder();

        if (isPomChanged) {
            tmpBuilder.append(
                    FooUtils.getSeparateLine() + SystemUtils.LINE_SEPARATOR);
            tmpBuilder.append("pom文件变化,需要手动添加或删除jar包:(如果不添加，会导致功能异常)"
                    + isPomChanged + SystemUtils.LINE_SEPARATOR);
            tmpBuilder.append(
                    FooUtils.getSeparateLine() + SystemUtils.LINE_SEPARATOR);
        }

        if (isSqlNeedExecuted) {
            tmpBuilder.append(
                    FooUtils.getSeparateLine() + SystemUtils.LINE_SEPARATOR);
            tmpBuilder
                    .append("sql文件变化，请执行sql文件【deployment.sql】里面的sql:(如果不执行，会导致功能异常或者服务器无法启动)"
                            + isSqlNeedExecuted + SystemUtils.LINE_SEPARATOR);
            tmpBuilder.append(
                    FooUtils.getSeparateLine() + SystemUtils.LINE_SEPARATOR);
        }

        if (isGlobalConfigChanged) {
            tmpBuilder.append(
                    FooUtils.getSeparateLine() + SystemUtils.LINE_SEPARATOR);
            tmpBuilder.append("globalConfig.xml文件变化，请手动修改服务器上的该文件!"
                    + isGlobalConfigChanged + SystemUtils.LINE_SEPARATOR);
            tmpBuilder.append(
                    FooUtils.getSeparateLine() + SystemUtils.LINE_SEPARATOR);
        }

        if (isHibernateMysqlChanged) {
            tmpBuilder.append(
                    FooUtils.getSeparateLine() + SystemUtils.LINE_SEPARATOR);
            tmpBuilder.append(
                    "监测到hibernate-localhost-mysql.properties配置文件变化,该文件在一般情况下不应该被修改，修改文件可能会导致服务器数据库连接错误！");
            tmpBuilder.append(
                    FooUtils.getSeparateLine() + SystemUtils.LINE_SEPARATOR);
        }

        if (isLogbackXmlChanged) {
            tmpBuilder.append(
                    FooUtils.getSeparateLine() + SystemUtils.LINE_SEPARATOR);
            tmpBuilder.append(
                    "监测到logback.xml配置文件变化,该文件在一般情况下不应该被修改，修改文件可能会导致服务器日志异常！");
            tmpBuilder.append(
                    FooUtils.getSeparateLine() + SystemUtils.LINE_SEPARATOR);
        }

        if (isHibernateMysqlPoolChanged) {
            tmpBuilder.append(
                    FooUtils.getSeparateLine() + SystemUtils.LINE_SEPARATOR);
            tmpBuilder.append(
                    "监测到pool-mysql.properties配置文件变化,该文件在一般情况下不应该被修改，修改文件会导致数据库连接池异常！");
            tmpBuilder.append(
                    FooUtils.getSeparateLine() + SystemUtils.LINE_SEPARATOR);
        }

        if (deletedFileNameSetResult.size() >= 1) {
            tmpBuilder.append(
                    FooUtils.getSeparateLine() + SystemUtils.LINE_SEPARATOR);
            tmpBuilder.append("需要手动从服务器上删除的文件如下：(如果不删除，会导致功能异常)"
                    + SystemUtils.LINE_SEPARATOR);
            tmpBuilder.append(
                    FooUtils.getSeparateLine() + SystemUtils.LINE_SEPARATOR);
            for (String tmp : deletedFileNameSetResult) {
                tmpBuilder.append(tmp + SystemUtils.LINE_SEPARATOR);
            }
        }

        if (excludeSet.size() >= 1) {
            tmpBuilder.append(
                    FooUtils.getSeparateLine() + SystemUtils.LINE_SEPARATOR);
            tmpBuilder.append("被排除的更新列表如下:" + SystemUtils.LINE_SEPARATOR);
            tmpBuilder.append(
                    FooUtils.getSeparateLine() + SystemUtils.LINE_SEPARATOR);
            for (String tmp : excludeSet) {
                tmpBuilder.append(tmp + SystemUtils.LINE_SEPARATOR);
            }
        }

        if (saveOrUpdateClassSet.size() >= 1) {
            tmpBuilder.append(
                    FooUtils.getSeparateLine() + SystemUtils.LINE_SEPARATOR);
            tmpBuilder.append("需要更新的java类列表如下:" + SystemUtils.LINE_SEPARATOR);
            tmpBuilder.append(
                    FooUtils.getSeparateLine() + SystemUtils.LINE_SEPARATOR);
            for (String tmp : saveOrUpdateClassSet) {
                tmpBuilder.append(tmp + SystemUtils.LINE_SEPARATOR);
            }
        }

        if (configFileSet.size() >= 1) {
            tmpBuilder.append(
                    FooUtils.getSeparateLine() + SystemUtils.LINE_SEPARATOR);
            tmpBuilder.append("需要更新的配置文件列表如下:" + SystemUtils.LINE_SEPARATOR);
            tmpBuilder.append(
                    FooUtils.getSeparateLine() + SystemUtils.LINE_SEPARATOR);
            for (String tmp : configFileSet) {
                tmpBuilder.append(tmp + SystemUtils.LINE_SEPARATOR);
            }
        }

        if (webappSourceSet.size() >= 1) {
            tmpBuilder.append(
                    FooUtils.getSeparateLine() + SystemUtils.LINE_SEPARATOR);
            tmpBuilder
                    .append("需要更新的webapp资源列表如下:" + SystemUtils.LINE_SEPARATOR);
            tmpBuilder.append(
                    FooUtils.getSeparateLine() + SystemUtils.LINE_SEPARATOR);
            for (String tmp : webappSourceSet) {
                tmpBuilder.append(tmp + SystemUtils.LINE_SEPARATOR);
            }
        }

        if (myErrorProjectNameSet.size() >= 1) {
            tmpBuilder.append(
                    FooUtils.getSeparateLine() + SystemUtils.LINE_SEPARATOR);
            tmpBuilder.append("错误的更新列表如下:(多个GIT在同一目录下会导致该错误，请考虑分git创建工程)"
                    + SystemUtils.LINE_SEPARATOR);
            tmpBuilder.append(
                    FooUtils.getSeparateLine() + SystemUtils.LINE_SEPARATOR);
            for (String tmp : myErrorProjectNameSet) {
                tmpBuilder.append(tmp + SystemUtils.LINE_SEPARATOR);
            }
        }

        String directory = workingDirectory + pathName;
        File updateFile = new File(directory + "update.txt");

        final String updateDesc = tmpBuilder.toString();
        FileUtils.write(updateFile, updateDesc, "utf-8");

        // writing release notes info,Start
        File releaseNotesFile = new File(
                directory + SystemUtils.FILE_SEPARATOR + "release-notes"
                        + SystemUtils.FILE_SEPARATOR + timeToken + ".txt");
        tmpBuilder = new StringBuilder();
        tmpBuilder.append(
                "当前到包截至ACC：" + latestCommitAcc + SystemUtils.LINE_SEPARATOR);
        tmpBuilder.append("本次打包时间：" + timeToken + SystemUtils.LINE_SEPARATOR);
        tmpBuilder.append("本次打包上传机器名：" + SystemUtils.USER_NAME
                + SystemUtils.LINE_SEPARATOR);
        tmpBuilder
                .append("本次打包更新内容为：" + updateDesc + SystemUtils.LINE_SEPARATOR);
        FileUtils.write(releaseNotesFile, tmpBuilder.toString(), "utf-8");
        logger.info("Create releaseNotesFile in file:{}",
                releaseNotesFile.getName());
        // writing release notes info,End

        // writing latest-release notes info,Start
        File releaseLatestNotesFile = new File(
                directory + SystemUtils.FILE_SEPARATOR + "release-notes"
                        + SystemUtils.FILE_SEPARATOR + "latest.json");
        CommonPatchRelease commonPatchRelease = new CommonPatchRelease();
        commonPatchRelease.setCommitAcc(latestCommitAcc);
        FileUtils.write(releaseLatestNotesFile,
                FooUtils.toJson(commonPatchRelease), "utf-8");
        logger.info("generate releaseLatestNotesFile:{}",
                releaseLatestNotesFile.getAbsolutePath());
        // writing latest-release notes info,End

        logger.info("更新文档生成目录:{}", directory);
        logger.info("更新文档生成日志:{}", updateFile.getAbsolutePath());

    }

    private static String getUrlFromArtifact() {
        String result = "";
        if (artifactId.equals("tenmalife-platform")) {
            result = "http://120.24.95.188/release-notes/latest.json";
        } else if (artifactId.equals("industry-platform")) {
            result = "http://120.26.119.150/release-notes/latest.json";
        } else if (artifactId.equals("enjoy-buying")) {
            result = "http://120.25.103.135:8088/release-notes/latest.json";
        } else {
            result = "";
        }
        return result;
    }

    public static String getCommitAcc(String defaultValue) {

        String url = getUrlFromArtifact();
        if (url.equals("")) {
            return defaultValue;
        }

        if (isFetchLatestFromServer.equals("true")) {
            File tmpFile = new File(
                    FileUtils.getTempDirectory() + FooUtils.getFileSeparator()
                            + FooUtils.generateUUID() + ".txt");

            logger.info("请求临时文件:{}", tmpFile.getAbsolutePath());
            try {
                FileUtils.copyURLToFile(new URL(url), tmpFile, 3000, 3000);

                String source = FileUtils.readFileToString(tmpFile);

                // List<Area> areas = gson.fromJson(source,
                // new TypeToken<List<Area>>() {
                // }.getType());
                CommonPatchRelease commonPatchRelease = FooUtils.getGson()
                        .fromJson(source, CommonPatchRelease.class);
                commitAcc = commonPatchRelease.getCommitAcc();
                logger.info("成功读取服务器上的commitAcc:{}，读取路径为：{}", commitAcc, url);
            } catch (IOException e) {
                logger.error("{}", e);
                commitAcc = defaultValue;
            } finally {
                logger.info("开始清理临时文件:{}", tmpFile.getAbsolutePath());
                FileUtils.deleteQuietly(tmpFile);
            }
        } else {
            commitAcc = defaultValue;
        }
        return commitAcc;
    }

    /**
     * 当pom变化的时候，并且配置文件开启了拷贝pom依赖的配置后，该功能可用。
     *
     * @throws Exception
     */
    private static void copyDependencyOnPomChanged() throws Exception {

        logger.info(
                "pom change detected,now execute copyDependencyOnPomChanged");

        if (!isPatchLibOnPomChange.equals("true")) {
            logger.info(
                    "you config patch-lib-on-pom-change as false,just return.");
        }

        Set<String> serverLibNames = Sets.newHashSet();
        // Set<String> localLibNames = Sets.newHashSet();

        // Add serverLibNames,Start
        Properties p = FooUtils
                .getPropertiesFromResourceFile(("common-patch.properties"));

        String artifactId = Strings.nullToEmpty(p.getProperty("artifactId"));
        String server = Strings
                .nullToEmpty(p.getProperty("server." + artifactId + ".server"));

        Preconditions.checkArgument(artifactId.equals("tenmalife-platform"),
                "function test,support tenmalife-platform only.");

        Preconditions.checkArgument(ValidationHelper.isInetAddressValid(server),
                "server address error.");

        logger.info("artifactId is:{} and server is:{}", artifactId, server);

        FTPClient ftp = new FTPClient();
        String path = "/tenmalife-platform/WEB-INF/lib";
        int reply;
        ftp.connect(server);
        ftp.login("adminnimda", "1234567");
        logger.info("Connected to server of:{} successful. ", server);

		/*
         * After connection attempt, you should check the reply code to verify success.
		 */
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            logger.info("FTP server refused connection.");
            System.exit(1);
        }
        // ftp.enterLocalPassiveMode();

        for (FTPFile ftpFile : ftp.listFiles(path)) {
            logger.debug("name is:{} and size is:{}", ftpFile.getName(),
                    ftpFile.getSize());
            serverLibNames.add(ftpFile.getName());
        }

        logger.info("Connected to server of:{} successful. ", server);
        ftp.logout();
        // Add serverLibNames,End

        // Add localLibNames,Start
        // Add localLibNames,End

    }

}
