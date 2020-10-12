package com.foo.common.base.utils;

import java.io.File;
import java.io.IOException;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;

public enum FooClassHelper {
	instance;

	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(FooClassHelper.class);

	public static void main(String[] args) throws Exception {
		String relativePath = instance.getJavaFilePackageDeclartion(
				"D:\\tmp\\zznode\\acsserver\\FooPluginUtils2.java");
		logger.info("result is:{}", relativePath);
	}

	/**
	 * 解析java文件的package声明
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getJavaFilePackageDeclartion(String absoluteFilePath)
			throws IOException {
		String myTargetDesStr = Files.readLines(new File(absoluteFilePath),
				Charsets.UTF_8, new LineProcessor<String>() {
					private String myTargetLineStr = "";

					@Override
					public boolean processLine(String line) throws IOException {
						if (line.contains("package")) {
							myTargetLineStr = line;
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

	/**
	 * Return path as:"<b> com/zznode/itms/business/model </b>"
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public String getClassRelativePath(File file) throws IOException {

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
}
