package com.yonge.crud.core.generator.module;

import java.io.File;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonge.crud.core.FreemarkerTemplateEngine;

public final class JavaFileGenerator extends FreemarkerTemplateEngine {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(JavaFileGenerator.class);

	public JavaFileGenerator() {
		super();
	}

	public void generate(Map<String, Object> data, String srcBase,
			String packageName, String targetFile, String templateFile) {
		StringBuilder buf = new StringBuilder();
		buf.append(srcBase).append(File.separator);
		buf.append(packageName.replaceAll("\\.", File.separator
				+ File.separator));// 单个的File.separator会导致无法通过正则表达式解析
		buf.append(File.separator);
		File packageDir = new File(buf.toString());
		if (!packageDir.exists()) {
			boolean flag = packageDir.mkdirs();
			if (!flag) {
				LOGGER.warn("递归创建文件夹{}失败", packageDir);
				return;
			}
		}
		buf.append(targetFile);
		String outputFile = buf.toString();
		LOGGER.debug("根据输入内容[{},{},{}]得到文件名{}", new Object[] { templateFile,
				srcBase, packageName, outputFile });
		render(data, templateFile, outputFile);
	}

}
