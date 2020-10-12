package com.foo.common.base.utils;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;

/**
 * <b>Quick develpment in maven style.</b>
 * <p>
 * <br>
 * 2013-10-25:Adding inner class copying
 *
 * @author Steve
 */
public class FooClassCompileHelper implements Cloneable {

    // private final String artifactId;

    // public static ClassHelper DEFAULT = new Builder().build();
    // private final String desBasePath = "d:\\zzNode\\tmp\\itmsPlus";
    private final String workingDirectory;
    // private final String sourceDirectory;
    // private final String myTargetPath =
    // "D:\\programTool/myGit/ims/itmsPlus_Core/target/classes";
    // Store classes here,some like build
    private final String desTargetBasePath;
    // Store source here,ie,java source files
    private String desSrcBasePath;
    private File orgTargetBathPath;

    private Properties properties;

    private boolean isCompiledFirst = false;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private FooClassCompileHelper(final String workingDirectory,
                                  final String sourceDirectory, File orgTargetBathPath,
                                  final String desTargetBasePath, final String desSrcBasePath,
                                  final String artifactId) {
        super();
        this.workingDirectory = workingDirectory;
        // this.sourceDirectory = sourceDirectory;
        this.desTargetBasePath = desTargetBasePath;
        this.desSrcBasePath = desSrcBasePath;
        this.orgTargetBathPath = orgTargetBathPath;
        // this.artifactId = artifactId;
    }

    public static FooClassCompileHelper.Builder custom() {
        return new Builder();
    }

    public static class Builder {

        private String workingDirectory;
        private String sourceDirectory;
        private File orgTargetBathPath;
        private String desTargetBasePath;
        private String desSrcBasePath;
        private String artifactId;

        public FooClassCompileHelper build() {

            orgTargetBathPath = new File(sourceDirectory);
            desTargetBasePath = FilenameUtils
                    .concat(workingDirectory, "target");
            desSrcBasePath = FilenameUtils.concat(workingDirectory, "src");

            return new FooClassCompileHelper(workingDirectory, sourceDirectory,
                    orgTargetBathPath, desTargetBasePath, desSrcBasePath,
                    artifactId);
        }

        public Builder setWorkingDirectory(final String workingDirectory) {
            this.workingDirectory = workingDirectory;
            return this;
        }

        public Builder setArtifactId(final String artifactId) {
            this.artifactId = artifactId;
            return this;
        }

        public Builder setSourceDirectory(final String sourceDirectory) {
            this.sourceDirectory = sourceDirectory;
            return this;
        }
    }

    /**
     * 编译tmp目录下的类，类需要提前放置
     *
     * @param projectName
     * @throws Exception
     */
    public int compileClass(String projectName) throws Exception {

        String tmpSourceDirectory = "";

        // setDesBasePath("D:\\tmp\\" + projectName + "\\");

        // p.setProperty("desBasePath", "D:\\tmp\\" + projectName + "\\");

        if (projectName.equals("itms")) {
            tmpSourceDirectory = "D:\\programTool\\myGit\\ims\\itmsPlus_Core\\target\\classes";
        } else if (projectName.equals("webClass")) {
            tmpSourceDirectory = "D:\\programTool\\myGit\\webClass\\target\\classes";
        } else {
            throw new Exception("error project name..");
        }

        FooClassCompileHelper classHelper = FooClassCompileHelper.custom()
                .setWorkingDirectory("D:\\tmp\\" + projectName + "\\")
                .setSourceDirectory(tmpSourceDirectory).build();

        FooUtils.info("Compile class only...");

        return classHelper.compileAndCopyClass();
        // this.quickUploadToServer();
    }

    @Test
    public void quickDevelopmentTest() throws Exception {
        FooClassCompileHelper classHelper = FooClassCompileHelper
                .custom()
                .setWorkingDirectory("D:\\tmp\\itms\\ui\\")
                .setSourceDirectory(
                        "D:\\programTool\\myGit\\ims\\itmsPlus_Core\\target\\classes")
                .build();

        classHelper.compileAndCopyClass();
        // this.quickUploadToServer();
    }

    public int compileAndCopyClass() throws IOException {

        File myDesDirectory = new File(workingDirectory);

        assertTrue("You should specify directory here",
                myDesDirectory.isDirectory());

        Collection<File> myFiles = FileUtils.listFiles(myDesDirectory,
                new String[]{"java"}, false);

        if (myFiles == null || myFiles.size() < 1) {
            logger.info("no class find under the directory:{}",
                    workingDirectory);
            return 0;
        }

        String orgSrcPath;
        File orgSrcFile;

        File desTargetFile;
        String desTargetPath;

        String relativePath;

        File desSrcFile;
        String desSrcPath;

        // Clean target folder
        logger.warn("clean foler:{}", desTargetBasePath);
        FileUtils.forceMkdir(new File(desTargetBasePath));
        FileUtils.cleanDirectory(new File(desTargetBasePath));

        logger.warn("clean foler:{}", desSrcBasePath);
        FileUtils.forceMkdir(new File(desSrcBasePath));
        FileUtils.cleanDirectory(new File(desSrcBasePath));

        IOFileFilter filter = null;
        Collection<File> orgSrcFileCollection = null;

        logger.info("classDevelopingStart,The Java class size is {} ",
                myFiles.size());

        for (File file : myFiles) {

            relativePath = getClassRelativePath(file);
            orgSrcPath = FilenameUtils.concat(FilenameUtils.concat(
                    orgTargetBathPath.getAbsolutePath(), relativePath), file
                    .getName().replace("java", "class"));
            orgSrcFile = new File(orgSrcPath);

            // Match inner class
            filter = new WildcardFileFilter(
                    FilenameUtils.getBaseName(orgSrcFile.getName())
                            + "$*.class");
            orgSrcFileCollection = FileUtils.listFiles(
                    new File(FilenameUtils.getFullPath(orgSrcFile
                            .getAbsolutePath())), filter, null);

            desTargetPath = FilenameUtils.concat(desTargetBasePath,
                    relativePath);
            desTargetFile = new File(desTargetPath);

            desSrcPath = FilenameUtils.concat(desSrcBasePath, relativePath);
            desSrcFile = new File(desSrcPath);

            logger.info("copy class file from {} to {}",
                    orgSrcFile.getAbsolutePath(),
                    desTargetFile.getAbsolutePath());
            FileUtils.copyFileToDirectory(orgSrcFile, desTargetFile);

            for (File tmpOrgSrcFile : orgSrcFileCollection) {
                logger.info("copy inner class file from {} to {}",
                        tmpOrgSrcFile.getAbsolutePath(),
                        desTargetFile.getAbsolutePath());
                FileUtils.copyFileToDirectory(tmpOrgSrcFile, desTargetFile);
            }

            logger.info("copy java file from {} to {}", file.getAbsolutePath(),
                    desSrcFile.getAbsolutePath());
            FileUtils.copyFileToDirectory(file, desSrcFile);

        }

        isCompiledFirst = true;

        return myFiles.size();
    }

    /**
     * Return path as:"<b> com/zznode/itms/business/model </b>"
     *
     * @param file
     * @return
     * @throws IOException
     */
    private String getClassRelativePath(File file) throws IOException {

        String myTargetDesStr = Files.readLines(file, Charsets.UTF_8,
                new LineProcessor<String>() {
                    private String myTargetLineStr = "";

                    @Override
                    public boolean processLine(String line) throws IOException {
                        if (line.contains("package")) {
                            myTargetLineStr = line.replace("package", "")
                                    .replace(";", "").trim().replace(".", "/");
                            return false;
                        }
                        return true;
                    }

                    @Override
                    public String getResult() {
                        return myTargetLineStr;
                    }
                });
        return myTargetDesStr;
    }

    public void quickUploadToServer() throws IOException {

        if (!isCompiledFirst) {
            logger.error("run quickDevelopment first please");
            return;
        }

        FileSystemManager fsManager = VFS.getManager();

        ClassPathResource myPath = new ClassPathResource(
                "target-oracle-112.properties");
        Properties p = new Properties();
        p.load(myPath.getInputStream());

        FTPClient ftp = new FTPClient();
        String hostname = p.getProperty("itms.ftp.hostname");
        String username = p.getProperty("itms.ftp.username");
        String password = p.getProperty("itms.ftp.password");
        String desServerPath = p.getProperty("itms.ftp.uiClassUploadPath");
        try {
            int reply;
            ftp.connect(hostname);
            ftp.login(username, password);
            logger.info("Connected to " + hostname + ".");

            // After connection attempt, you should check the reply code to
            // verify success.
            reply = ftp.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                logger.info("FTP server refused connection.");
                throw new ConnectException("FTP server refused connection.");
            }
            ftp.enterLocalPassiveMode();
            ftp.setFileType(FTP.BINARY_FILE_TYPE);

            Collection<File> classFiles = FileUtils.listFiles(new File(
                    desTargetBasePath), new String[]{"class"}, true);
            String tmpPath = "";
            String tmpPathForReplace = desTargetBasePath + "\\";
            String workingDirectory = "";
            for (File file : classFiles) {
                tmpPath = FilenameUtils.separatorsToUnix(file.getAbsolutePath()
                        .replace(tmpPathForReplace, ""));

                workingDirectory = FilenameUtils
                        .getFullPathNoEndSeparator(FilenameUtils
                                .separatorsToUnix(FilenameUtils.concat(
                                        desServerPath, tmpPath)));

                // Create folder if not exists,Start
                FileObject fileObject = null;
                if (!ftp.changeWorkingDirectory(workingDirectory)) {
                    fileObject = fsManager.resolveFile("ftp://" + username
                            + ":" + password + "@" + hostname + "/"
                            + workingDirectory + "");
                    fileObject.createFolder();
                    logger.info("Create folder at: {} ", workingDirectory);
                    fileObject.close();
                    fileObject = null;
                }
                // Create folder if not exists,End

                boolean saveFileSuccessful = ftp.storeFile(file.getName(),
                        new FileInputStream(file));
                logger.info(
                        "save file of {} at directory of: {} ,result is {} ",
                        file.getName(), workingDirectory, saveFileSuccessful);
            }

            ftp.logout();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    // do nothing
                }
            }
        }
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getWorkingDirectory() {
        return workingDirectory;
    }

}
