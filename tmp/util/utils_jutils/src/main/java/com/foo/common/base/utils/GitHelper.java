package com.foo.common.base.utils;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Git command tool utils.Such as <i>git diff</i>.You can use them to patch,to automatic operations.
 *
 * @author Steve
 */
public enum GitHelper {

    INSTANCE;

    public static final Logger logger = LoggerFactory
            .getLogger(GitHelper.class);
    public static final Logger simpleLogger = LoggerFactory.getLogger("empty");

    /**
     * 通过执行一个git diff例如：<br/>
     * <p>
     * <pre>
     * git diff --name-status HEAD^ 75b12c6d4eacc342016b6602481efd1333752ee2
     * </pre>
     * <p>
     * 来获得变更列表，一个变更列表如下：<br/>
     * M src/main/java/com/foo/example/action/ExampleAction.java<br>
     * D src/main/resources/logback-test.xml<br>
     * A src/main/resources/logback.xml
     * <p>
     * <br>
     * M和A的类型可以自动部署,D类型则需要用户自行从服务器上进行删除
     *
     * @param projectPath projectPath
     * @param artifactId  artifactId
     * @param commitAcc   commitAcc
     * @return Set<String> updatedFileNameSet:用于记录更新列表 <br>
     * Set<String> deletedFileNameSet:用于记录删除列表
     * @throws Exception Exception
     */
    public static Map<String, Set<String>> getGitUpdateLinesFromCmd_Diff(
            String projectPath, String artifactId, String commitAcc,
            String endAcc) throws Exception {

        Map<String, Set<String>> result = Maps.newHashMap();
        String gitQuotepathCommand = " git config --global core.quotepath false ";
        String gitDiffCommand = " git diff --name-status " + commitAcc + " "
                + endAcc;

        String myCommand;
        if (SystemUtils.IS_OS_MAC) {
            myCommand = " cd " + projectPath + artifactId + " && "
                    + gitQuotepathCommand + " && " + gitDiffCommand;
        } else {
            myCommand = "cmd /c "
                    + FooUtils.getWindowsDrivePartition(projectPath) + " && cd "
                    + projectPath + artifactId + " && " + gitQuotepathCommand
                    + " && " + gitDiffCommand;
        }

        logger.info("git diff start with command:【{}】", myCommand);

        Process p = FooUtils.executeCommand(myCommand);

        StringWriter successWriter = new StringWriter();
        StringWriter failureWriter = new StringWriter();
        IOUtils.copy(p.getInputStream(), successWriter, "utf-8");
        // 请注意，mac操作系统不会输出错误流，而是直接抛异常。错误流只适用于windows系统。
        IOUtils.copy(p.getErrorStream(), failureWriter, "utf-8");

        String successWriterStr = successWriter.toString();
        String failureWriterStr = failureWriter.toString();

        if (!failureWriterStr.equals("")) {
            logger.error("执行如下命令错误:【{}】,错误原因:{}", myCommand, failureWriterStr);
            throw new RuntimeException("Command execute error.");
        }

        Set<String> updatedFileNameSet = Sets.newHashSet();
        Set<String> deletedFileNameSet = Sets.newHashSet();

        List<String> tmp;

        logger.info("command output as following: is:");

        for (String line : Splitter.on("\n").split(successWriterStr)) {
            // for (String line : Splitter.on(SystemUtils.LINE_SEPARATOR).split(
            // sWriter.toString())) {

            simpleLogger.info("parsing line as:{}", line);

            tmp = Lists.newArrayList(Splitter.on("\t").split(line));
            if (tmp.size() != 2) {
                continue;
            }
            if (tmp.get(0).equals("M")) {
                updatedFileNameSet.add(tmp.get(1));
            } else if (tmp.get(0).equals("D")) {
                deletedFileNameSet.add(tmp.get(1));
            } else if (tmp.get(0).equals("A")) {
                updatedFileNameSet.add(tmp.get(1));
            } else {
                logger.error("error with line:{}", line);
                System.exit(0);
            }
        }

        logger.info("updatedFileNameSets is:{}", updatedFileNameSet);
        logger.warn("YOU SHOULD DEAL WITH THE FOLLOWING DELETE INFO:{}",
                deletedFileNameSet);

        result.put("updatedFileNameSet", updatedFileNameSet);
        result.put("deletedFileNameSet", deletedFileNameSet);

        return result;

    }


    /**
     * 获得本地最新的Git Commit Head Acc.
     *
     * @param artifactId      artifactId
     * @param artifactGitHome artifactGitHome
     * @return
     * @throws IOException
     */
    public static String getCommitHeadAcc(String artifactId,
                                          String artifactGitHome, boolean fetchOriginMaster)
            throws IOException {

        String gitLog_WindowsCommand = " && git log --pretty=format:\"%H$%cn$%cd$%s\" -1 --date=iso ";

        String gitFetch_WindowsCommand = " && git fetch origin master";

        String command = "";

        if (FooUtils.IsMacOs()) {
            command = "cd " + artifactGitHome + artifactId
                    + gitLog_WindowsCommand;

            if (fetchOriginMaster) {
                command = "cd " + artifactGitHome + artifactId
                        + gitFetch_WindowsCommand + gitLog_WindowsCommand;
            }
        }
        // deal with windows.
        else {
            command = "cmd /c "
                    + FooUtils.getWindowsDrivePartition(artifactGitHome)
                    + " && cd " + artifactGitHome + artifactId
                    + gitLog_WindowsCommand;

            if (fetchOriginMaster) {
                command = "cmd /c "
                        + FooUtils.getWindowsDrivePartition(artifactGitHome)
                        + " && cd " + artifactGitHome + artifactId
                        + gitFetch_WindowsCommand + gitLog_WindowsCommand;
            }
        }

        Process p = FooUtils.executeCommand(command);

        logger.info("getCommitHeadAcc fetch command is:【{}】", command);

        StringWriter sWriterSuccess = new StringWriter();
        StringWriter sWriterError = new StringWriter();
        IOUtils.copy(p.getInputStream(), sWriterSuccess, "utf-8");
        IOUtils.copy(p.getErrorStream(), sWriterError, "utf-8");

        String sWriterSuccessStr = sWriterSuccess.toString();
        String sWriterErrorStr = sWriterError.toString();

        if (sWriterSuccessStr.equals("")) {
            logger.error("execute command error,error is:{}", sWriterErrorStr);
        }

        // logger.info("success is:{}", sWriterSuccessStr);
        List<String> tmp;
        final int maxLogParamsLength = 4;// 参考log命令，以$符号进行分隔
        String tmpAbbreviate;
        for (String line : Splitter.on("\n").split(sWriterSuccessStr)) {
            tmp = Lists.newArrayList(Splitter.on("$").omitEmptyStrings()
                    .limit(maxLogParamsLength).split(line));
            if (tmp.size() < maxLogParamsLength) {
                continue;
            }
            tmpAbbreviate = StringUtils.abbreviate(tmp.get(3), 50);
            logger.info("one is:{},two is:{},three is:{},four is:{}",
                    tmp.get(0), tmp.get(1), tmp.get(2), tmpAbbreviate);
            return tmp.get(0);
        }
        return "";

    }

    public void gitLog() throws IOException, InterruptedException {

        String gitHome = "D:\\tools\\myGit\\";

        logger.info("git log start");

        String artifactId = "tenmalife-platform";
        // String gitFetch_WindowsCommand = " && git fetch origin master";

        String gitLog_WindowsCommand = " && git log --pretty=format:\"%H$%cn$%cd$%s\" -10 --date=iso ";

        String windowCommand = "cmd /c "
                + FooUtils.getWindowsDrivePartition(gitHome) + " && cd "
                + gitHome + artifactId
                // + gitFetch_WindowsCommand
                + gitLog_WindowsCommand;

        Process p = FooUtils.executeCommand(windowCommand);

        logger.info("command is:【{}】", windowCommand);

        StringWriter sWriterSuccess = new StringWriter();
        StringWriter sWriterError = new StringWriter();
        IOUtils.copy(p.getInputStream(), sWriterSuccess, "utf-8");
        IOUtils.copy(p.getErrorStream(), sWriterError, "utf-8");

        String sWriterSuccessStr = sWriterSuccess.toString();
        String sWriterErrorStr = sWriterError.toString();

        if (sWriterSuccessStr.equals("")) {
            logger.error("execute command error,error is:{}", sWriterErrorStr);
            return;
        }

        // logger.info("success is:{}", sWriterSuccessStr);
        List<String> tmp;
        final int maxLogParamsLength = 4;// 参考log命令，以$符号进行分隔
        String tmpAbbreviate;
        for (String line : Splitter.on("\n").split(sWriterSuccessStr)) {
            tmp = Lists.newArrayList(Splitter.on("$").omitEmptyStrings()
                    .limit(maxLogParamsLength).split(line));
            if (tmp.size() < maxLogParamsLength) {
                continue;
            }
            tmpAbbreviate = StringUtils.abbreviate(tmp.get(3), 50);
            logger.info("one is:{},two is:{},three is:{},four is:{}",
                    tmp.get(0), tmp.get(1), tmp.get(2), tmpAbbreviate);
        }

    }

    public static void gitDiff(String[] args)
            throws IOException, InterruptedException {

        System.out.println(Lists.newArrayList(Splitter.on("\t").split(
                "M	industry-platform/src/main/java/com/feiynn/groupbuying/action/FinanceAction.java")));

        String projectPath = "D:\\tools\\myGit\\feintek-platform\\";
        // String projectPath = "/Users/Steve/tools/myGit/";

        logger.info("git diff start");

        String artifactId = "industry-platform";
        // String artifactId = "tenmalife-platform";
        String commitAcc = "8ce39091ef6358686cfc9ccdfb4cd683b31d6090";
        String endAcc = "19f68c247b1b272233cd13a012a0fefee5d2aca6";

        String myCommand;

        if (SystemUtils.IS_OS_MAC) {
            myCommand = " cd " + projectPath + artifactId
                    + " && git diff --name-status " + commitAcc + " " + endAcc
                    + "";
        } else {

            myCommand = "cmd /c "
                    + FooUtils.getWindowsDrivePartition(projectPath) + " && cd "
                    + projectPath + artifactId + " && git diff --name-status "
                    + commitAcc + " " + endAcc + "";
        }

        Process p = FooUtils.executeCommand(myCommand);

        logger.info("command is:{}", myCommand);

        StringWriter sWriter = new StringWriter();
        IOUtils.copy(p.getInputStream(), sWriter, "utf-8");

        Set<String> modifiedFileNameSets = Sets.newHashSet();
        Set<String> deletedFileNameSets = Sets.newHashSet();
        Set<String> addedFileNameSets = Sets.newHashSet();

        List<String> tmp;
        for (String line : Splitter.on("\n").split(sWriter.toString())) {
            tmp = Lists.newArrayList(Splitter.on("\t").split(line));
            if (tmp.size() != 2) {
                continue;
            }
            if (tmp.get(0).equals("M")) {
                modifiedFileNameSets.add(tmp.get(1));
            } else if (tmp.get(0).equals("D")) {
                deletedFileNameSets.add(tmp.get(1));
            } else if (tmp.get(0).equals("A")) {
                addedFileNameSets.add(tmp.get(1));
            } else {
                logger.error("error with line:{}", line);
                System.exit(0);
            }
        }

        logger.info("modifiedFileNameSets:{}", modifiedFileNameSets);
        logger.info("deletedFileNameSets:{}", deletedFileNameSets);
        logger.info("addedFileNameSets:{}", addedFileNameSets);

        // FileUtils.copyInputStreamToFile(p.getInputStream(),
        // FooUtils.getTestLogFile());

        // FileUtils.copyInputStreamToFile(p.getErrorStream(),
        // FooUtils.getTestLogFile());

        // ByteStreams.copy(p.getInputStream(), System.out);
        // ByteStreams.copy(p.getErrorStream(), System.err);
        // p.waitFor();

        // logger.info("Start:mvn versions:display-dependency-updates");
        // myCommand = "cmd /c D: && cd D:\\programTool\\myGit\\" + projectName
        // + " && mvn versions:display-dependency-updates";
        // p = Runtime.getRuntime().exec(myCommand);
        // ByteStreams.copy(p.getInputStream(), System.out);
        // ByteStreams.copy(p.getErrorStream(), System.err);
        // p.waitFor();

    }
}
