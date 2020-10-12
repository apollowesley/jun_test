package com.foo.common.base.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * SmartAdmin1.5 版本的html模版到到jsp版本的转换
 * 
 * @author Steve
 *
 */
public class LinuxShFileHelper {

	private final static ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory
			.getLogger("com.foo");

	private static List<String> getResult(File file) throws IOException {

		List<String> lines = FileUtils.readLines(file, "utf-8");

		int indexOfLogLib = 0;
		int indexOfClasspath = 0;
		int indexOfJavaMainStart = 0;

		String line = "";
		String classpathLine = "";
		String javaMainStartLine = "";
		boolean hasModifyClassPath = false;

		for (int i = 0; i < lines.size(); i++) {
			line = Strings.nullToEmpty(lines.get(i));

			if (line.contains("LOG_LIB=")) {
				logger.warn(
						"file of:{} already dealed,just return original lines.",
						file.getName());
				hasModifyClassPath = true;
				indexOfLogLib = i;
				continue;
			}

			if (line.contains("CLASSPATH=")) {
				indexOfClasspath = i;
				classpathLine = line;
				continue;
			}

			if (line.contains("-D$PROCESSOR_NAME")) {
				indexOfJavaMainStart = i;
				javaMainStartLine = line;
				break;
			}

		}

		Preconditions.checkArgument(indexOfClasspath > 10,
				"can not find indexOfClasspath ");
		Preconditions.checkArgument(indexOfJavaMainStart > indexOfClasspath,
				"can not find indexOfJavaMainStart ");

		lines.set(indexOfJavaMainStart, javaMainStartLine.replace(
				"-D$PROCESSOR_NAME",
				"-D$PROCESSOR_NAME -DPROCESSOR_NAME=$PROCESSOR_NAME"));

		if (!hasModifyClassPath) {
			lines.set(indexOfClasspath, classpathLine.replace(":$LOG_LIB", "")
					+ ":$LOG_LIB");
			lines.add(
					indexOfClasspath,
					"LOG_LIB=$ITMS_HOME/lib/logback-classic-1.1.2.jar:$ITMS_HOME/lib/logback-core-1.1.2.jar:$ITMS_HOME/lib/slf4j-api-1.7.7.jar:$CAP_HOME/cfg/log/"
							+ "\n");

		} else {
			lines.set(
					indexOfLogLib,
					"LOG_LIB=$ITMS_HOME/lib/logback-classic-1.1.2.jar:$ITMS_HOME/lib/logback-core-1.1.2.jar:$ITMS_HOME/lib/slf4j-api-1.7.7.jar:$CAP_HOME/cfg/log/"
							+ "\n");
		}
		return lines;

	}

	public static void main(String[] args) {

		logger.setLevel(Level.DEBUG);

		String workingDir = "D:\\tmp\\itms\\log\\shFiles";
//		String srcDir = workingDir + "\\src-cap";
		 String srcDir = workingDir + "\\src";
		String targetDir = workingDir + "\\target";

		List<String> errorFileNames = Lists.newArrayList();

		IOFileFilter filter = new WildcardFileFilter("*.sh");

		logger.info("delete directory:{}");
		FileUtils.deleteQuietly(new File(targetDir));

		String newFileName = "";
		for (File file : FileUtils.listFiles(new File(srcDir), filter, null)) {
			newFileName = FilenameUtils.concat(targetDir,
					FilenameUtils.getBaseName(file.getName()) + ".sh");
			logger.info("deal with file:{}", newFileName);
			try {

				List<String> myResult = getResult(file);
				Preconditions.checkNotNull(myResult);
				// 注意，linux需要\n换行符，不然脚本无法识别
				FileUtils.writeLines(new File(newFileName), "utf-8", myResult,
						"\n", false);

			} catch (Exception e) {
				errorFileNames.add(newFileName);
				logger.error("{}", e);
				continue;
			}
		}
		logger.warn("error files are following ones.");
		for (String string : errorFileNames) {
			logger.warn(string);
		}

	}
}
