package com.foo.common.base.utils;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang3.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * <b>Quick develpment in maven style.</b>
 * <p>
 * <br>
 * 2013-10-25:Adding inner class copying
 *
 * @author Steve
 */
public class CommonClassCompileHelper {

    private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory
            .getLogger(CommonClassCompileHelper.class);

    /**
     * 更具git diff命令所产生的文件列表，复制项目中对应的java类和class文件到target目录中。
     *
     * @param updatedFileNameSet updatedFileNameSet
     * @return 所有.class文件的数量总和，包括内部类。比如：MavenSearchResultJson$Response.class
     * @throws IOException IOException
     */
    public static Map<String, Integer> copyJavaSrcAndClass(
            Set<String> updatedFileNameSet, String workingDirectory,
            String projectDirectoryTmp, String artifactId) throws IOException {

        String sourceDirectory = projectDirectoryTmp + artifactId + ""
                + SystemUtils.FILE_SEPARATOR + "target"
                + SystemUtils.FILE_SEPARATOR + "classes";
        File orgTargetBathPath = new File(sourceDirectory);
        String desTargetBasePath = FilenameUtils.concat(workingDirectory,
                "target");
        String desSrcBasePath = FilenameUtils.concat(workingDirectory, "src");

        Map<String, Integer> resultMap = Maps.newHashMap();

        File myDesDirectory = new File(workingDirectory);

        Preconditions.checkArgument(myDesDirectory.isDirectory(),
                "You should specify directory here");

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

        File file = null;

        int innerClassSize = 0;

        for (String nameFromGitDiff : updatedFileNameSet) {

            // file = new File(IndustryPlatformPatchHelper.projectDirectory +
            // "\\"
            // + artifactId + "\\" + nameFromGitDiff);
            logger.trace("nameFromGitDiff here is:{}", nameFromGitDiff);

            file = new File(projectDirectoryTmp + artifactId
                    + SystemUtils.FILE_SEPARATOR + nameFromGitDiff);

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

            logger.trace("copy class file from {} to {}",
                    orgSrcFile.getAbsolutePath(),
                    desTargetFile.getAbsolutePath());
            FileUtils.copyFileToDirectory(orgSrcFile, desTargetFile);

            for (File tmpOrgSrcFile : orgSrcFileCollection) {
                logger.warn("copy inner class file from {} to {}",
                        tmpOrgSrcFile.getAbsolutePath(),
                        desTargetFile.getAbsolutePath());
                innerClassSize++;
                FileUtils.copyFileToDirectory(tmpOrgSrcFile, desTargetFile);
            }

            logger.trace("copy java file from {} to {}",
                    file.getAbsolutePath(), desSrcFile.getAbsolutePath());
            FileUtils.copyFileToDirectory(file, desSrcFile);

        }

        resultMap.put("normalClassSize", updatedFileNameSet.size());
        resultMap.put("innerClassSize", innerClassSize);

        return resultMap;

        // return updatedFileNameSet.size() + innerClassSize;
    }

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/Steve/tools/myGit/jUtils/src/main/java/com/foo/common/base/laboratory/Trader.java");
        logger.info("{}", getClassRelativePath(file));
        ;
    }

    /**
     * Return path as:"<b> com/foo/common/base </b>"
     *
     * @param file file to be analysed
     * @return ClassRelativePath ClassRelativePath Return path as:"<b> com/foo/common/base </b>"
     * @throws IOException IOException
     */
    private static String getClassRelativePath(File file) throws IOException {
        return java.nio.file.Files.lines(Paths.get(file.getPath()), Charsets.UTF_8)
                .filter((t) -> t.startsWith("package"))
                .findFirst()
                .map((t) -> t.replace("package", "").replace(";", "").trim().replace(".", "/"))
                .get();
    }

    /**
     * Return path as:"<b> com/zznode/itms/business/model </b>"
     *
     * @param file file
     * @return ClassRelativePath ClassRelativePath
     * @throws IOException IOException
     */
    private static String getClassRelativePathForJava7(File file) throws IOException {
        return Files.readLines(file, Charsets.UTF_8,
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
    }

}
